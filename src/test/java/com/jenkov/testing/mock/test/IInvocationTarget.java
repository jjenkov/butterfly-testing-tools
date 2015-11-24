package com.jenkov.testing.mock.test;

/**
 * @author Jakob Jenkov
 *         Copyright 2004 Jenkov Development
 */
public interface IInvocationTarget {

    public void invoke();
    public void invoke(String aString);
    public Long invoke(Long aLong);
    public int  invoke(int anInt);

    public void invokeThrowsException() throws Exception;

    public boolean invokeCalled();
    public boolean invokeCalled(String aString);
    public boolean invokeCalled(Long aLong);
    public boolean invokeCalled(int anInt);

    public boolean invokeBoolean();
    public byte    invokeByte();
    public short   invokeShort();
    public char    invokeChar();
    public int     invokeInt();
    public long    invokeLong();
    public float   invokeFloat();
    public double  invokeDouble();

}
