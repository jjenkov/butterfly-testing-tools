package com.jenkov.testing.mock.impl;

import com.jenkov.testing.mock.itf.IParameterConstraint;

/**
 * This constraint checks if a parameter is exactly equal to a specific value.
 * Nulls are allowed. If parameter is null and specific value is null it is also
 * considered a match.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class EqualConstraint implements IParameterConstraint{

    protected Object constrainingParameter = null;

    public EqualConstraint(Object constrainingParameter) {
        this.constrainingParameter = constrainingParameter;
    }

    public boolean isWithin(Object parameter) {
        return ClassUtil.areEqual(this.constrainingParameter, parameter);
    }
}
