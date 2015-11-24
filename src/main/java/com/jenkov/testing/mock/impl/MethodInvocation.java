package com.jenkov.testing.mock.impl;

import java.lang.reflect.Method;


/**
 * This class represents a method invocation. It is used by the <code>GenericInvocationHandler</code> to log
 * methods called on the <code>java.lang.reflect.MockFactory</code> the invocation handlerGeneric is registered
 * with. <code>MethodInvocation</code> is also used by clients of <code>GenericInvocationHandler</code>
 * to specify what methods to check if were called. The <code>MethodInvocation</code> can also forward
 * it's method call to a target object.
 *
 * @see com.jenkov.testing.mock.itf.IMock
 * @see java.lang.reflect.Proxy
 * @author Jakob Jenkov
 *         Copyright 2004 Jenkov Development
 */
public class MethodInvocation {

    protected String   methodName       = null;
    protected Object[] parameters       = null;
    protected Class[]  parameterTypes   = null;

    /**
     * Creates an instance representing a method call to a methodName that doesn't take any parameters.
     * @param methodName The name of the method that was invoked.
     */
    public MethodInvocation(String methodName){
        validate(methodName);
        this.methodName           = methodName;
    }

    /**
     * Creates an instance representing any method call with the given name and parameter types, regardless
     * of the parameter values.
     * @param methodName      The name of the method invoked.
     * @param parameterTypes  The parameter types of the method invoked.
     */
    public MethodInvocation(String methodName, Class[] parameterTypes){
        validate(methodName);
        this.methodName       = methodName;
        this.parameterTypes   = parameterTypes;
    }

    /**
     * Creates an instance representing any method call with the given name and a single parameter of
     * the given type. A shortcut constructor for representing invocations of setters etc.
     * @param methodName    The name of the method invoked.
     * @param parameterType The parameter type of the method invoked.
     */
    public MethodInvocation(String methodName, Class parameterType) {
        this.methodName = methodName;
        if(parameterType != null){
            this.parameterTypes = new Class[]{parameterType};
        }
    }

    /**
     * Creates an instance representing a method call with the given method name, parameter types, and
     * parameter values.
     * @param methodName      The name of the method invoked.
     * @param parameterTypes  The parameter types of the method invoked.
     * @param parameters      The parameter values of the method invoked.
     */
    public MethodInvocation(String methodName, Class[] parameterTypes, Object[] parameters){
        validate(methodName);
        this.methodName       = methodName;
        this.parameterTypes   = parameterTypes;
        this.parameters       = parameters;
    }

    /**
     * Creates an instance representing a method call with the given method name and
     * parameter values. The parameter types will be retrieved from the
     * <code>getClass()</code> method of the parameter objects. The parameter objects cannot be null
     * when using this constructor. The constructor cannot determine the type of the parameter if
     * the parameter is null.
     *
     * @param methodName      The name of the method invoked.
     * @param parameters      The parameter values of the method invoked.
     */
    public MethodInvocation(String methodName, Object[] parameters){
        validate(methodName);
        this.methodName       = methodName;
        this.parameters       = parameters;

        if(parameters != null){
            this.parameterTypes   = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                this.parameterTypes[i] = parameters[i].getClass();
            }
        }
    }

    /**
     * Creates an instance representing a method call with the given method name, parameter type, and
     * parameter value. A shortcut constructor for representing invocations of setters etc.
     * @param methodName      The name of the method invoked.
     * @param parameterType  The parameter type of the method invoked.
     * @param parameter      The parameter value of the method invoked.
     */
    public MethodInvocation(String methodName, Class parameterType, Object parameter){
        validate(methodName);
        this.methodName       = methodName;
        if(parameterType != null){
            this.parameterTypes   = new Class[]{parameterType};
        }
        if(parameter != null){
            this.parameters       = new Object[]{parameter};
        }
    }

    /**
     * Creates an instance representing a method call with the given method name and
     * parameter value. As parameter type is used the Class object returned by the
     * <code>parameter.getClass()</code> method call.
     *
     * A shortcut constructor for representing invocations of setters etc.
     *
     * @param methodName      The name of the method invoked.
     * @param parameter      The parameter value of the method invoked.
     */
    public MethodInvocation(String methodName, Object parameter){
        validate(methodName);
        this.methodName       = methodName;
        this.parameterTypes   = new Class[]{parameter.getClass()};
        this.parameters       = new Object[]{parameter};
    }

    private void validate(String methodName) {
        if(methodName == null){
            throw new IllegalArgumentException("Parameter methodName cannot be null");
        }
    }

    /**
     * Returns the method name of the method invoked.
     * @return The method name of the method invoked.
     */
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * Returns the parameter types of the method invoked.
     * @return The parameter types of the method invoked.
     */
    public Class[] getParameterTypes(){
        return this.parameterTypes;
    }

    /**
     * Returns the parameter values of the method invoked.
     * @return The parameter values of the method invoked.
     */
    public Object[] getParameters(){
        return this.parameters;
    }

    /**
     * Forwards the method invocation represented by this instance to the target object. The
     * target object must of course have a matching method.
     * @param invocationTarget The object to invoke the matching method on.
     * @return                 The value returned by the target object, if any.
     * @throws Throwable       If anything goes wrong during the method invocation, for instance if the
     *                         target object throws an exception, of if no matching method was found
     *                         in the target object.
     */
    public Object invoke(Object invocationTarget) throws Throwable{
        Method method = null;

        method = invocationTarget.getClass().getMethod(getMethodName(), getParameterTypes());
        return method.invoke(invocationTarget, getParameters());
    }


    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.methodName);
        buffer.append("(");
        if(this.parameters != null){
            for(int i=0; i < this.parameters.length; i++){
                if(this.parameterTypes[i] != null){
                    buffer.append(this.parameterTypes[i].getName());
                    buffer.append("<");
                    buffer.append(this.parameters[i]);
                    buffer.append(">");
                    if(i< this.parameters.length - 1){
                        buffer.append(", ");
                    }
                }
            }
        }
        buffer.append(")");

        return buffer.toString();
    }

    /**
     * Returns true if this instance matches the other instance. Two instances match each other if
     * they have the same method name, and the same or no parameter types. If one of the instances
     * also have parameter values the two instance will still match, regardless of these parameter
     * values. If both instances have parameter values they will only match of all parameters are
     * equal.
     * @param other The <code>MethodInvocation</code> instance to match against this instance.
     * @return True if the other instance matches this one. False if not.
     */
    public boolean matches(MethodInvocation other){
        if(other == null)                      return false;

        if( !other.getMethodName().equals(this.methodName)) return false;

        Class[]  otherParameterTypes = other.getParameterTypes();
        if(!areEqual(otherParameterTypes)) return false;

        if( this.getParameters() != null && this.getParameters().length != 0){
            if(other.getParameters() != null && other.getParameters().length !=0){
                if(!areEqual(other.getParameters())) return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the two instances are equal to each other. By equal means if
     * their method names, parameter types and parameter values are all equal. Unlike
     * <code>match()</code> an instance
     * with parameter values is not equal to an instance without parameter values, even
     * if the rest is equal.
     * @param o The <code>MethodInvocation</code> instance to test if is equal to this one.
     * @return True if the other instance is equal to this one. False if not.
     */
    public boolean equals(Object o){
        if(o == null)                      return false;
        if(!(o instanceof MethodInvocation)) return false;

        MethodInvocation other = (MethodInvocation) o;
        if( !other.getMethodName().equals(this.methodName)) return false;

        Object[] otherParameters     = other.getParameters();
        Class[]  otherParameterTypes = other.getParameterTypes();

        if(!areEqual(otherParameterTypes)) return false;
        if(!areEqual(otherParameters)) return false;

        return true;
    }


    private boolean areEqual(Class[] otherParameterTypes){
        if(this.parameterTypes == null && otherParameterTypes == null){ return true;  }
        if(this.parameterTypes == null && otherParameterTypes != null){ return otherParameterTypes.length == 0; }
        if(this.parameterTypes != null && otherParameterTypes == null){ return parameterTypes.length == 0; }
        if(this.parameterTypes.length != otherParameterTypes.length) return false;

        for(int i=0; i < parameterTypes.length; i++){
            if(! ClassUtil.areEqual(parameterTypes[i], otherParameterTypes[i])) return false;
        }
        return true;
    }

    private boolean areEqual(Object[] otherParameters){
        if(this.parameters == null && otherParameters == null){ return true;  }
        if(this.parameters == null && otherParameters != null){ return otherParameters.length == 0; }
        if(this.parameters != null && otherParameters == null){ return parameters.length == 0; }
        if(this.parameters.length != otherParameters.length) return false;
        for(int i=0; i < parameters.length; i++){
            if(! ClassUtil.areEqual(parameters[i], otherParameters[i])) return false;
        }
        return true;
    }

}
