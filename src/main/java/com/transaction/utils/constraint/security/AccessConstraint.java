package com.transaction.utils.constraint.security;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessConstraint {
    Class<? extends AccessStatusSource<?>> value();
}
