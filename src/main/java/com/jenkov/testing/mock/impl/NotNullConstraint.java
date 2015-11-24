package com.jenkov.testing.mock.impl;

import com.jenkov.testing.mock.itf.IParameterConstraint;

/**
 * This contraint checks if a parameter is not null.
 * 
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class NotNullConstraint implements IParameterConstraint{


    public boolean isWithin(Object parameter) {
        return parameter != null;
    }
}
