package com.jenkov.testing.mock.itf;

/**
 * Still experimental
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public interface IParameterConstraint {

    /**
     * Checks if the given parameter is within this parameter constraint.
     * @param parameter The parameter to check against this constraint.
     */
    public boolean isWithin(Object parameter);
}
