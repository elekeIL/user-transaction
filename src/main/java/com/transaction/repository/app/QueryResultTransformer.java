package com.transaction.repository.app;

public interface QueryResultTransformer<E, T> {

    T transaform(E e);
}
