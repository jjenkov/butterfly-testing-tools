package com.jenkov.testing.mock.test;

/**
 * @author Jakob Jenkov
 *         Copyright 2004 Jenkov Development
 */
public class InvocationTarget implements IInvocationTarget{

    protected String aString = null;
    protected Long   aLong   = null;
    protected int    anInt   = -1;

    protected boolean invoked1 = false;
    protected boolean invoked2 = false;
    protected boolean invoked3 = false;
    protected boolean invokedAnInt = false;


    public void invoke() {
        this.invoked1 = true;
    }

    public void invoke(String aString) {
        this.invoked2 = true;
        this.aString  = aString;
    }

    public Long invoke(Long aLong) {
        this.invoked3 = true;
        return aLong;
    }

    public int invoke(int anInt) {
        this.invokedAnInt = true;
        this.anInt = anInt;
        return this.anInt;
    }

    public void invokeThrowsException() throws Exception {
        throw new IllegalStateException("This is an error!");
    }

    public boolean invokeCalled() {
        return invoked1;
    }

    public boolean invokeCalled(String aString) {
        if(invoked2 && areEqual(this.aString, aString)){
            return true;
        }
        return false;
    }

    public boolean invokeCalled(Long aLong) {
        if(invoked3 && areEqual(this.aLong, aLong)){
            return true;
        }
        return false;
    }

    public boolean invokeCalled(int value){
        if(invokedAnInt && this.anInt == value){
            return true;
        }
        return false;
    }

    public boolean invokeBoolean() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte invokeByte() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public short invokeShort() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public char invokeChar() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int invokeInt() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long invokeLong() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public float invokeFloat() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double invokeDouble() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private boolean areEqual(Object obj1, Object obj2) {
        if(obj1 == null && obj2 == null) return true;
        if(obj1 != null && obj2 == null) return false;
        if(obj1 == null && obj2 != null) return false;
        return obj1.equals(obj2);

    }


}
