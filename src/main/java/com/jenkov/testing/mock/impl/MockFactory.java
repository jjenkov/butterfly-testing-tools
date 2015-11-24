package com.jenkov.testing.mock.impl;

import com.jenkov.testing.mock.itf.IMock;

import java.lang.reflect.InvocationHandler;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

/**
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class MockFactory {

    /**
     * Creates a mock that implements all interfaces implemented by the target object.
     *
     * @param collaborator The target object to create a mock for.
     * @return A mock for the target object.
     */
    public static Object createProxy(Object collaborator){
        Class[] interfaces = getInterfacesForObject(collaborator);

        return java.lang.reflect.Proxy.newProxyInstance(
                IMock.class.getClassLoader(),
                interfaces,
                new Mock(collaborator));
    }

    /**
     * Creates a mock that implements all interfaces implemented by the target object + the given
     * extra interface.
     *
     * @param collaborator The target object to create a mock for.
     * @return A mock for the target object.
     */
    public static Object createProxy(Object collaborator, Class[] proxyInterfaces){
        Class[] interfaces = appendInterfaces(getInterfacesForObject(collaborator), proxyInterfaces);

        return java.lang.reflect.Proxy.newProxyInstance(
                IMock.class.getClassLoader(),
                interfaces,
                new Mock(collaborator));
    }


    /**
     * Creates a mock that implements all interfaces implemented by the target object + the given
     * extra interface.
     *
     * @param collaborator The target object to create a mock for.
     * @return A mock for the target object.
     */
    public static Object createProxy(Object collaborator, Class proxyInterface){
        Class[] interfaces = appendInterfaces(getInterfacesForObject(collaborator), proxyInterface);

        return java.lang.reflect.Proxy.newProxyInstance(
                IMock.class.getClassLoader(),
                interfaces,
                new Mock(collaborator));
    }


    /**
     * Creates a mock that implements the target interface.
     * @param proxyInterface The interface the mock is to implement.
     * @return A mock that implements the target interface.
     */
    public static Object createProxy(Class proxyInterface){
        Class[] interfaces = new Class[]{proxyInterface, IMock.class};
        return java.lang.reflect.Proxy.newProxyInstance(
                IMock.class.getClassLoader(),
                interfaces,
                new Mock());
    }

    /**
     * Creates a mock that implements the target interfaces.
     * @param proxyInterfaces The interfaces the mock is to implement.
     * @return A mock that implements the target interface.
     */
    public static Object createProxy(Class[] proxyInterfaces){
        return java.lang.reflect.Proxy.newProxyInstance(
                IMock.class.getClassLoader(),
                proxyInterfaces,
                new Mock());
    }


    public static Object createProxy(Class theInterface, InvocationHandler invocationHandler){
        return java.lang.reflect.Proxy.newProxyInstance(
                theInterface.getClassLoader(),
                new Class[]{theInterface},
                invocationHandler);
    }

    /**
     * Returns the invocation handler registered with the java.lang.reflect.Proxy given as parameter, and returns
     * it cast to an IMock instance. Use this method to get to the mock handler object behind the mock object
     * created by the <code>createProxy()</code> methods.
     *
     * @param proxyObject The mock object created by one of this factory's <code>createProxy()</code> methods.
     * @return An IMock instance that can be used to check what methods were called on the mock, plus add mock
     *         return values etc.
     * @see IMock
     */
    public static IMock getMock(Object proxyObject){
        return (IMock) java.lang.reflect.Proxy.getInvocationHandler(proxyObject);
    }

    private static Class[] appendInterfaces(Class[] interfaces, Class aClass) {
        return appendInterfaces(interfaces, new Class[]{aClass});
    }

    public static Class[] appendInterfaces(Class[] firstInterfaces, Class[] secondInterfaces) {
        Set interfaces = new HashSet();

        for(int i=0; i<firstInterfaces.length; i++) interfaces.add(firstInterfaces[i]);
        for(int i=0; i<secondInterfaces.length; i++) interfaces.add(secondInterfaces[i]);

        Class[] allInterfaces = new Class[interfaces.size()];

        Iterator iterator = interfaces.iterator();
        int i = 0;
        while(iterator.hasNext()){
            allInterfaces[i++] = (Class) iterator.next();
        }

        return allInterfaces;
    }

    /**
     * Returns all interfaces implemented by this objects class. In contrast
     * to the Class.getInterfaces() method this method also includes the
     * interfaces implemented by any of the object class's superclasses.
     * @param object The object to return the interfaces of.
     * @return An array of the interfaces this objects class implements.
     */
    public static Class[] getInterfacesForObject(Object object){
        Set interfaceSet = new HashSet();

        Class objectClass = object.getClass();
        while(!(Object.class.equals(objectClass))){
            Class[] classInterfaces = objectClass.getInterfaces();
            for (int i = 0; i < classInterfaces.length; i++) {
                interfaceSet.add(classInterfaces[i]);
            }
            objectClass = objectClass.getSuperclass();
        }

        Class[] interfaceArray = new Class[interfaceSet.size()];
        Iterator iterator = interfaceSet.iterator();
        int i = 0;
        while(iterator.hasNext()) interfaceArray[i++] = (Class) iterator.next();
        return interfaceArray;
    }


}
