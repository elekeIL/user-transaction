package com.transaction.repository;
;
import com.transaction.entity.Users;
import com.transaction.enums.GenericStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByIdAndStatus(Long userId, GenericStatus genericStatus);

    Optional<Users> findByUsernameIgnoreCaseAndStatus(String username, GenericStatus genericStatus);
    @Query("select u from Users as u where lower(u.email) = lower(?1) ")
    Optional<Users> findByEmail(String email);
}

