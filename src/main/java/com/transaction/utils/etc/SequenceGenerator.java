package com.transaction.utils.etc;

public interface SequenceGenerator {
    Long getNextLong();
    String getNext();
    String getNext(String format);
}
