package com.jenkov.testing.mock.impl;

/**
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class ClassUtil {
    public static boolean areEqual(Object parameter, Object otherParameter) {
        if( parameter == null && otherParameter == null) return true;
        if( parameter != null && otherParameter == null) return false;
        if( parameter == null && otherParameter != null) return false;
        return parameter.equals(otherParameter);
    }
}
