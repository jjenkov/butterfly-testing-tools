package com.jenkov.testing.mock.itf;

import com.jenkov.testing.mock.impl.MethodInvocation;

import java.util.List;

/**
 * This interface represents a mock object. You can set return values on it,
 * and you can make assertions about what methods were called on the mock.
 *
 * 
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public interface IMock {

    /**
     * Adds a return value to the mock. The mock will then function as a mock as well as a mock.
     * You cannot call this method if the mock has a target object that method calls are forwarded to.
     *
     * <br/><br/>
     * If you add multiple return values, the values will be returned by the mock in the sequence
     * you add them. The mock doesn't care what method is called when returning the value. This
     * may result in class cast exceptions, mismatched methods and return values etc. The return
     * values have to match the methods called on the mock to avoid these problems.
     *
     * <br/><br/>
     * If the mock runs out of return values, it will switch to default mock return values.
     *
     * @param returnValue The value to be returned by the mock for a method call.
     *
     */
    void addReturnValue(Object returnValue);

    /**
     * Adds one or more return values to the mock. The mock will then function as a mock as well as a mock.
     * Return values take precedence over forwarding calls to a target object (if the mock has one). In other
     * words, if you add a return value to a mock which has a target object, then the next method called
     * on the mock will return the added value, and the call will not be forwarded to the target object.
     *
     * <br/><br/>
     * If you add multiple return values, the values will be returned by the mock in the sequence
     * you add them. The mock doesn't care what method is called when returning the value. This
     * may result in class cast exceptions, mismatched methods and return values etc. The return
     * values have to match the methods called on the mock to avoid these problems.
     *
     * <br/><br/>
     * If the mock runs out of return values, it will switch to default mock return values.
     * 
     * @param returnValues
     */
    void addReturnValues(Object[] returnValues);

    /**
     * Returns the list of <code>MethodInvocation</code> instances logged in this instance.
     * @return The list of <code>MethodInvocation</code> instances logged in this instance.
     */
    List getInvocations();

    /**
     * Throws an <code>java.langAssertionError</code> if the given method has not been invoked. Does nothing if
     * the method has been invoked.
     * @param methodInvocation The method to assert was invoked.
     * @throws IllegalStateException If the mock has an invocation target object.
     */
    void assertInvoked(MethodInvocation methodInvocation);

    /**
     * Reverse of <code>assertInvoked</code>.
     * Throws an <code>java.langAssertionError</code> if the given method has been invoked. Does nothing if
     * the method has not been invoked.
     * @param methodInvocation The method to assert was invoked.
     * @throws IllegalStateException If the mock has an invocation target object.
     */
    public void assertNotInvoked(MethodInvocation methodInvocation);

    /**
     * Throws an <code>java.langAssertionError</code> if the given method was not invoked
     * as the index'th method invoked on the mock this instance is registered with. Index starts from 0.
     * For instance, if index is 3 then you are asserting that the <code>MethodInvocation</code>
     * instance passed as parameter must be the 4th method called on the mock (index 3 in the list
     * of method invocations, index starting from 0).
     *
     * @param methodInvocation The method invocation to assert occurred at the given index.
     * @param index            The index of the method invocation to assert about.
     * @throws IllegalStateException If the mock has an invocation target object.
     */
    void assertInvoked(MethodInvocation methodInvocation, int index);

    /**
     * Throws an <code>java.langAssertionError</code> if the given method is not the last method invoked
     * on the mock this instance is registered with. If it is the last method invoked this method
     * does nothing.
     * @param methodInvocation The method invocation to assert occured last.
     */
    void assertInvokedLast(MethodInvocation methodInvocation);

    /**
     * Throws an <code>java.langAssertionError</code> if the first method was not invoked before the second
     * method.
     * @param firstInvocation The method invocation that must have occurred before first.
     * @param lastInvocation  The method invocation that must have occurred after the first.
     */
    void assertInvokedBefore(MethodInvocation firstInvocation, MethodInvocation lastInvocation);

    /**
     * Returns true if the given method was invoked on the mock this instance is registered with.
     * False if not.
     * @param methodInvocation The method invocation to check if has occurred.
     * @return True if the method was invoked on the mock. False if not.
     */
    boolean invoked(MethodInvocation methodInvocation);

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
    boolean invoked(MethodInvocation methodInvocation, int index);

    /**
     * Returns true if the given method invocation occurred as the last method invocation on
     * the mock this instance is registered with. False if not.
     * @param methodInvocation   The method invocation to check if occurred last.
     * @return                   True if the method invocation was the last. False if not.
     */
    boolean invokedLast(MethodInvocation methodInvocation);

    /**
     * Returns true if the first method invocation occurred before the second. False if not. Also returns
     * false if one or both of the method invocations never occurred.
     * @param firstInvocation  The method invocation that should have occurred first.
     * @param lastInvocation   The method invocation that should have occurred later than the first.
     * @return True if the first method was invoked before the second. False if not.
     */
    boolean invokedBefore(MethodInvocation firstInvocation, MethodInvocation lastInvocation);

    /**
     * Removes all method invocations logged inside this instance.
     * The invocation target, if any, is not removed.
     */
    void clear();
}
