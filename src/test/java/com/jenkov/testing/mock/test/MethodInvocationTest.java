package com.jenkov.testing.mock.test;

import com.jenkov.testing.mock.impl.MethodInvocation;
import junit.framework.TestCase;

/**
 * @author Jakob Jenkov
 *         Copyright 2004 Jenkov Development
 */
public class MethodInvocationTest extends TestCase{

    public void testMatches() throws Exception{
        MethodInvocation invocation1 = null;
        MethodInvocation invocation2 = null;

        invocation1 = new MethodInvocation("test");
        invocation2 = new MethodInvocation("test2");
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test");
        invocation2 = new MethodInvocation("test");
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", (Class) null);
        invocation2 = new MethodInvocation("test");
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", (Class) null);
        invocation2 = new MethodInvocation("test", (Class)null);
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{});
        invocation2 = new MethodInvocation("test", (Class)null);
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{});
        invocation2 = new MethodInvocation("test");
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{});
        invocation2 = new MethodInvocation("test", new Class[]{});
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class});
        invocation2 = new MethodInvocation("test", new Class[]{});
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class});
        invocation2 = new MethodInvocation("test", (Class)null);
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class});
        invocation2 = new MethodInvocation("test");
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class});
        invocation2 = new MethodInvocation("test", new Class[]{int.class});
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{Integer.class});
        invocation2 = new MethodInvocation("test", new Class[]{int.class});
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class, int.class});
        invocation2 = new MethodInvocation("test", new Class[]{int.class});
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));


        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{});
        invocation2 = new MethodInvocation("test", new Class[]{int.class});
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{});
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{});
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{});
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, null);
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, null);
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, null);
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2)});
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, null);
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2)});
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{});
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2)});
        invocation2 = new MethodInvocation("test", new Class[]{int.class});
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2)});
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2)});
        assertTrue(invocation1.matches(invocation2));
        assertTrue(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2)});
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(3)});
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));

        invocation1 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2), new Integer(2)});
        invocation2 = new MethodInvocation("test", new Class[]{int.class}, new Object[]{new Integer(2)});
        assertFalse(invocation1.matches(invocation2));
        assertFalse(invocation2.matches(invocation1));

    }
}
