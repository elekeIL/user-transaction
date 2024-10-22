package com.transaction.utils.constraint.security;

import java.lang.annotation.Annotation;

public interface AccessStatusSource <A extends Annotation>{
    AccessStatus getStatus(A accessConstraint);

}
