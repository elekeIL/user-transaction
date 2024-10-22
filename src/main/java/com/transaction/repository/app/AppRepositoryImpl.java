package com.transaction.repository.app;

import com.querydsl.core.QueryModifiers;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.transaction.enums.GenericStatus;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Repository
class AppRepositoryImpl implements AppRepository {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    @Qualifier("defaultEntityManager")
    private EntityManager entityManager;

    @Transactional(transactionManager = "defaultTransactionManager")
    @Override
    public <E> Optional<E> fetchOne(JPAQuery<E> query) {
        return Optional.ofNullable(query.fetchOne());
    }

    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    @Override
    public <E> long count(Class<E> type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(type);
        criteriaQuery.select(cb.count(root));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    @Override
    public <E> Optional<E> findById(Class<E> type, Object id) {
        return Optional.ofNullable(entityManager.find(type, id));
    }

    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    @Override
    public <E> List<E> findByIds(Class<E> type, Collection<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> clientCriteriaQuery = criteriaBuilder.createQuery(type);
        Root<E> clientRoot = clientCriteriaQuery.from(type);
        clientCriteriaQuery.where(clientRoot.get("id").in(ids));
        return entityManager.createQuery(clientCriteriaQuery).getResultList();
    }

    @Override
    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    public <T> T unproxy(Class<T> tClass, T entity) {
        if (!Hibernate.isInitialized(entity)) {
            return findById(tClass, ((HibernateProxy) entity).getHibernateLazyInitializer().getIdentifier()).orElse(null);
        }
        return

                entity;
    }

    @Override
    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    public <T> List<T> unproxyAll(Class<T> tClass, List<T> entities) {

        List<T> entityList = new ArrayList<>();

        for (T entity : entities) {
            entityList.add(unproxy(tClass, entity));
        }

        return entityList;
    }

    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    @Override
    public <T> List<T> getByProperty(Class<T> tClass, String propertyName, Object propertyValue) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> clientCriteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> clientRoot = clientCriteriaQuery.from(tClass);
        Predicate predicate = criteriaBuilder.equal(clientRoot.get(propertyName), propertyValue);
        clientCriteriaQuery.where(predicate);
        return entityManager.createQuery(clientCriteriaQuery).getResultList();
    }

    @Override
    @Transactional(transactionManager = "defaultTransactionManager")
    public <E> E persist(E e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    @Transactional(transactionManager = "defaultTransactionManager")
    public <E> E merge(E e) {
        return entityManager.merge(e);
    }

    @Override
    public void remove(Object e) {
        entityManager.remove(e);
    }

    @Override
    public <E> JPAQuery<E> startJPAQuery(EntityPath<E> entityPath) {
        return new JPAQuery<E>(entityManager).from(entityPath);
    }

    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    @Override
    public <E> QueryResults<E> fetchResults(JPAQuery<E> query) {
        return query.fetchResults();
    }

    @Transactional(transactionManager = "defaultTransactionManager")
    @Override
    public <E> List<E> fetchResultList(JPAQuery<E> query) {
        return query.fetch();
    }

    @Override
    public <E, T> QueryResults<T> fetchResults(JPAQuery<E> query, QueryResultTransformer<E, T> transformer) {
        QueryResults<E> results = query.fetchResults();
        return new QueryResults<T>(results.getResults().stream().map(transformer::transaform)
                .collect(Collectors.toList()), new QueryModifiers(results.getLimit(), results.getOffset()), results.getTotal());
    }

    @Override
    public <E> OrderSpecifier<?> getSortedColumn(Order order, String columnName, EntityPath<E> entityPath) {
        Path<Object> fieldPath = Expressions.path(Object.class, entityPath, columnName);
        return new OrderSpecifier(order, fieldPath);
    }

    @Transactional(readOnly = true, transactionManager = "defaultTransactionManager")
    @Override
    public <E> Optional<E> findFirstByField(Class<E> type, String columnName, Object value) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteria = builder.createQuery(type);
        Root<E> from = criteria.from(type);
        criteria.select(from);
        criteria.where(builder.equal(from.get(columnName), value));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).setMaxResults(1).getSingleResult());
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public <E> Long countByProperty(Class<E> type, String propertyName, Object propertyValue) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(type);
        criteriaQuery.select(cb.count(root));

        List<Predicate> restrictions = new ArrayList<>();
        restrictions.add(
                cb.and(
                        cb.equal(root.get(propertyName), propertyValue),
                        cb.equal(root.get("status"), GenericStatus.ACTIVE)
                )
        );
        criteriaQuery.where(restrictions.toArray(new Predicate[restrictions.size()]));

        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            logger.error("Error counting entity by property.", e);
            return null;
        }
    }

    @Override
    public void setLockTimeout(long timeOutInMilliSeconds) {
        entityManager.createNativeQuery(String.format("set lock_timeout = %d", timeOutInMilliSeconds)).executeUpdate();
    }

    @Override
    public String getLockTimeout() {
        return (String) entityManager.createNativeQuery("show lock_timeout").getSingleResult();
    }
}
