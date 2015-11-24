package com.jenkov.testing.mock.impl;

import com.jenkov.testing.mock.itf.IMock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class is to be used with a <code>java.lang.reflect.MockFactory</code> instance which
 * can be used to implement an interface at runtime. <code>Proxy</code>
 * receives information about the methods called on such a mock, and then logs that
 * information internally as a list of <code>MethodInvocation</code> instances. This
 * can be useful in unit tests to check if the tested component uses another component
 * correctly. For instance to test if a DAO class closes the database connections
 * correctly when done reading or writing.
 *
 * <br/><br/>
 * A <code>Proxy</code> instance can forward the method calls received
 * from the mock to a target object which implements the same interface as the mock.
 *
 * <br/><br/>
 * Example: <br/>
 * <br/><code>
 *       java.sql.Connection connection = getConnection();<br/>
 * <br/>
 *       Proxyr   = new Proxytion); <br/><br/>
 *       java.sql.Connection mock = (java.sql.Connection) MockFactory.newProxyInstance(               <br/>
 *       &nbsp;&nbsp;&nbsp; java.sql.Connection.class.getClassLoader(), new Class[] { java.sql.Connection.class }, handlerGeneric); </br>
 *<br/>
 *       handlerGeneric.assertInvoked(new MethodInvocation("close")); <br/>
 * </code>
 *
 * @author Jakob Jenkov
 *         Copyright 2004 Jenkov Development
 * @see com.jenkov.testing.mock.impl.MethodInvocation
 */
public class Mock implements InvocationHandler, IMock {

    protected Object invocationTarget = null;
    protected List   invocations      = new ArrayList();
    protected boolean debug           = false;

    protected List   returnValues     = new ArrayList();

    /**
     * Creates an instance with no target object and not in debug mode. The
     * method calls received by the mock will only be logged internally,
     * and not forwarded anywhere.
     */
    public Mock(){}



    /**
     * Creates an instance with no target object and possibly in debug mode. The
     * method calls received by the mock will only be logged internally,
     * and not forwarded anywhere. When in debug mode the instance will
     * write the method calls to System.out as well as log them internally.
     *
     * @param debug True if the instance is to write method calls to <code>System.out</code>. False if not.
     */
    public Mock(boolean debug){
        this.debug = debug;
    }


    /**
     * Creates an instance which forwards all method calls to the target object. The instance
     * is not in debug mode. The method calls are also logged internally.
     *
     * @param target The target object to forward the method calls on the mock to. The
     * target object should implement the same interface as the mock instance this
     * <code>Proxy</code> instance is registered with.
     */
    public Mock(Object target){
        this.invocationTarget = target;
    }


    /**
     * Creates an instance which forwards all method calls to the target object. The instance
     * can be set to debug mode. When in debug mide the instance will write all
     * method calls to <code>System.out</code> in addition to logging them internally.
     *
     * @param target The target object to forward the method calls on the mock to. The
     * target object should implement the same interface as the mock instance this
     * <code>Proxy</code> instance is registered with.
     *
     * @param debug True if the instance is to write method calls to <code>System.out</code>. False if not.
     */
    public Mock(Object target, boolean debug){
        this.invocationTarget = target;
        this.debug = debug;
    }


    /**
     * This method is called by the mock instance that this <code>Proxy</code>
     * instance is registered with.
     * @param proxy      The mock the original method was called on.
     * @param method     The method called on the mock.
     * @param parameters The parameters the method was called with.
     * @return           The return value of the coresponding method on this instance's target object.
     *                   If this instance has no target object then either null is returned, or if the
     *                   original method has a primitive type as return type, then a wrapper object
     *                   (Long, Integer etc.) with the value 0.
     *
     *
     * @throws Throwable If anything goes wrong during the method forwarding.
     */
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
        MethodInvocation methodInvocation =
                new MethodInvocation(method.getName(), method.getParameterTypes(), parameters);

        if(this.debug) {
            printInvocation(methodInvocation, "invoked: ");
        }

        /* If method called is from IMock interface, method call is executed on this Mock */
        if(IMock.class.equals(method.getDeclaringClass())){
            return methodInvocation.invoke(this);
        }

        invocations.add(methodInvocation);

        /* If method not from IMock interface, let invocation target have it */
        if(this.invocationTarget != null){
            Object returnValue = forwardMethodInvocation(proxy, method, parameters, methodInvocation);
            if(returnValue == invocationTarget){
                return proxy; //return 
            }
            return returnValue;
        } else {
            return returnMockValue(method);
        }

    }

    private Object returnMockValue(Method method) {
        if(this.returnValues.size() > 0 && (!method.getReturnType().equals(void.class)) && !(method.getReturnType() == null)){
            return this.returnValues.remove(0);
        }

        if(boolean.class.equals(method.getReturnType())){ return new Boolean  (true); }
        if(byte.class   .equals(method.getReturnType())){ return new Byte     ((byte)  0); }
        if(short.class  .equals(method.getReturnType())){ return new Short    ((short) 0); }
        if(char.class   .equals(method.getReturnType())){ return new Character((char)  0); }
        if(int.class    .equals(method.getReturnType())){ return new Integer  (0); }
        if(long.class   .equals(method.getReturnType())){ return new Long     (0); }
        if(float.class  .equals(method.getReturnType())){ return new Float    (0); }
        if(double.class .equals(method.getReturnType())){ return new Double   (0); }

        return null;

    }

    private Object forwardMethodInvocation(Object proxy, Method method, Object[] parameters, MethodInvocation methodInvocation) throws Throwable {
        if(this.invocationTarget instanceof InvocationHandler){
            return ((InvocationHandler) this.invocationTarget).invoke(proxy, method, parameters);
        }
        return methodInvocation.invoke(this.invocationTarget);
    }


    protected void printInvocation(MethodInvocation invocation, String text) {
        System.out.print(text + invocation.getMethodName());
        System.out.print("(");
        if(invocation.getParameterTypes() != null && invocation.getParameters() != null){
            for(int i=0; i<invocation.getParameterTypes().length; i++){
                System.out.print(invocation.getParameterTypes()[i].getName());
                System.out.print(" <");
                System.out.print(invocation.getParameters()[i]);
                System.out.print(">");
                if(i < invocation.getParameterTypes().length-1){
                    System.out.println(", ");
                }
            }
        }
        System.out.println(")");
    }


    public void addReturnValue(Object returnValue) {
//        if(this.invocationTarget != null){
//            throw new IllegalStateException("You cannot add return values to a mock that has an invocation target." +
//                    "The mock cannot both return a mock value and forward the method call");
//        }
        this.returnValues.add(returnValue);
    }

    public void addReturnValues(Object[] returnValues) {
        for (int i = 0; i < returnValues.length; i++) {
            addReturnValue(returnValues[i]);
        }
    }

    /**
     * Returns the list of <code>MethodInvocation</code> instances logged in this instance.
     * @return The list of <code>MethodInvocation</code> instances logged in this instance. 
     */
    public List getInvocations(){
        return this.invocations;
    }


    /**
     * Throws an <code>junit.framework.AssertionFailedError</code> if the given method has not been invoked. Does nothing if
     * the method has been invoked.
     * @param methodInvocation The method to assert was invoked.
     */
    public void assertInvoked(MethodInvocation methodInvocation) {
        if(!invoked(methodInvocation)){
            throw new java.lang.AssertionError("Method not invoked: " + methodInvocation.toString());
        }
    }

    /**
     * Reverse of <code>assertInvoked</code>. Throws an <code>junit.framework.AssertionFailedError</code> if the given method has been invoked. Does nothing if
     * the method has not been invoked.
     * @param methodInvocation The method to assert was invoked.
     */
    public void assertNotInvoked(MethodInvocation methodInvocation) {
        if(invoked(methodInvocation)){
            throw new java.lang.AssertionError("Method was invoked: " + methodInvocation.toString());
        }
    }


    /**
     * Throws an <code>junit.framework.AssertionFailedError</code> if the given method was not invoked
     * as the index'th method invoked on the mock this instance is registered with. Index starts from 0.
     * For instance, if index is 3 then you are asserting that the <code>MethodInvocation</code>
     * instance passed as parameter must be the 4th method called on the mock (index 3 in the list
     * of method invocations, index starting from 0).
     *
     * @param methodInvocation The method invocation to assert occurred at the given index.
     * @param index            The index of the method invocation to assert about.
     */
    public void assertInvoked(MethodInvocation methodInvocation, int index) {
        if(index >= this.invocations.size()){
            throw new java.lang.AssertionError("Only " + this.invocations.size() + " methods invoked. " +
                    "Index was " + index);
        }
        if(! invoked(methodInvocation, index)){
            throw new java.lang.AssertionError("Method invoked at index " + index + " was not: " + methodInvocation.toString());
        }
    }


    /**
     * Throws an <code>junit.framework.AssertionFailedError</code> if the given method is not the last method invoked
     * on the mock this instance is registered with. If it is the last method invoked this method
     * does nothing.
     * @param methodInvocation The method invocation to assert occured last.
     */
    public void assertInvokedLast(MethodInvocation methodInvocation) {
        if(this.invocations.size() == 0){
            throw new java.lang.AssertionError("No methods invoked");
        }
        if(!invokedLast(methodInvocation)){
            throw new java.lang.AssertionError("Last method invoked was not: " + methodInvocation.toString()
                    + "\nLast method invoked was: " + this.invocations.get(this.invocations.size()-1).toString());
        }
    }


    /**
     * Throws an <code>junit.framework.AssertionFailedError</code> if the first method was not invoked before the second
     * method.
     * @param firstInvocation The method invocation that must have occurred before first.
     * @param lastInvocation  The method invocation that must have occurred after the first.
     */
    public void assertInvokedBefore(MethodInvocation firstInvocation, MethodInvocation lastInvocation){
        if(!invokedBefore(firstInvocation, lastInvocation)){
            throw new java.lang.AssertionError("The first method passed was not invoked before the second");
        }

    }


    /**
     * Returns true if the given method was invoked on the mock this instance is registered with.
     * False if not.
     * @param methodInvocation The method invocation to check if has occurred.
     * @return True if the given method was invoked. False if not.
     */
    public boolean invoked(MethodInvocation methodInvocation){
        Iterator iterator = this.invocations.iterator();
        while(iterator.hasNext()){
            if(methodInvocation.matches((MethodInvocation) iterator.next())){
                return true;
            }
        }
        return false;
    }


    /**
     * Returns true if the given method invocation occurred as the index'th method invocation
     * on the mock this instance is registered with. False if not, also if index is equal to
     * or larger than the total number of methods invoked.
     * Index starts from 0.
     * For instance, if index is 3 then you are checking if the <code>MethodInvocation</code>
     * instance passed as parameter must be the 4th method called on the mock (index 3 in the list
     * of method invocations, index starting from 0).
     * @param methodInvocation  The method invocation to check if occurred at the given index.
     * @param index             The index of the method invocation to compare to.
     * @return                  True if the method invoked at the given index matches the given
     *                          method invocation. False if not.
     */
    public boolean invoked(MethodInvocation methodInvocation, int index){
        if(index >= this.invocations.size()){
            return false;
        }
        return methodInvocation.matches((MethodInvocation) this.invocations.get(index));
    }


    /**
     * Returns true if the given method invocation occurred as the last method invocation on
     * the mock this instance is registered with. False if not.
     * @param methodInvocation   The method invocation to check if occurred last.
     * @return                   True if the method invocation was the last. False if not.
     */
    public boolean invokedLast(MethodInvocation methodInvocation){
        if(this.invocations.size() == 0) {
            return false;
        }
        return methodInvocation.matches((MethodInvocation) this.invocations.get(this.invocations.size()-1));
    }


    /**
     * Returns true if the first method invocation occurred before the second. False if not. Also returns
     * false if one or both of the method invocations never occurred.
     * @param firstInvocation  The method invocation that should have occurred first.
     * @param lastInvocation   The method invocation that should have occurred later than the first.
     * @return True if the first method was invoked before the second. False if not.
     */
    public boolean invokedBefore(MethodInvocation firstInvocation, MethodInvocation lastInvocation){
        int indexFirst  = -1;
        int indexLast   = -1;
        int index       =  0;

        Iterator iterator = this.invocations.iterator();
        while(iterator.hasNext()){
            MethodInvocation currentInvocation = (MethodInvocation) iterator.next();
            if(firstInvocation.matches(currentInvocation)){
                indexFirst = index;
            }
            if(lastInvocation.matches(currentInvocation)){
                indexLast = index;
            }
            index++;
        }
        if(indexFirst < indexLast && (indexFirst > -1 && indexLast > -1)){
            return true;
        }
        return false;
    }

    /**
     * Removes all method invocations logged inside this instance, and all not returned stubbed return values.
     * The invocation target, if any, is not removed.
     */
    public void clear(){
        this.invocations.clear();
        this.returnValues.clear();
    }

}
