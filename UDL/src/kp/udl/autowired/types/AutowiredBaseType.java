/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import kp.udl.autowired.SerializerManager;
import kp.udl.data.UDLValue;
import static kp.udl.data.UDLValue.valueOf;

/**
 *
 * @author Asus
 */
final class AutowiredBaseType extends AutowiredType
{
    private final PType ptype;
    private final Class<?> jclass;
    private final boolean primitive;
    
    AutowiredBaseType(PType ptype, Class<?> jclass)
    {
        this.ptype = ptype;
        this.jclass = jclass;
        this.primitive = jclass.isPrimitive();
        baseCache(this, jclass);
    }
    
    @Override
    public final Class<?> getBaseClass() { return jclass; }
    
    @Override
    public final boolean isBase() { return true; }
    
    @Override
    final Object arrayInstance(int len)
    {
        switch(ptype)
        {
            case BYTE: return primitive ? new byte[len] : new Byte[len];
            case SHORT: return primitive ? new short[len] : new Short[len];
            case INT: return primitive ? new int[len] : new Integer[len];
            case LONG: return primitive ? new long[len] : new Long[len];
            case FLOAT: return primitive ? new float[len] : new Float[len];
            case DOUBLE: return primitive ? new double[len] : new Double[len];
            case BOOLEAN: return primitive ? new boolean[len] : new Boolean[len];
            case CHAR: return primitive ? new char[len] : new Character[len];
            case STRING: return new String[len];
            default: throw new IllegalStateException();
        }
    }

    @Override
    public Object inject(UDLValue base, SerializerManager smanager)
    {
        switch(ptype)
        {
            case BYTE: return (byte) base.getInt();
            case SHORT: return (short) base.getInt();
            case INT: return base.getInt();
            case LONG: return base.getLong();
            case FLOAT: return base.getFloat();
            case DOUBLE: return base.getDouble();
            case BOOLEAN: return base.getBoolean();
            case CHAR: return base.getString().charAt(0);
            case STRING: return base.getString();
            default: throw new IllegalStateException();
        }
    }

    @Override
    public final UDLValue extract(Object base, SerializerManager smanager)
    {
        switch(ptype)
        {
            case BYTE: return valueOf((Byte) base);
            case SHORT: return valueOf((Short) base);
            case INT: return valueOf((Integer) base);
            case LONG: return valueOf((Long) base);
            case FLOAT: return valueOf((Float) base);
            case DOUBLE: return valueOf((Double) base);
            case BOOLEAN: return valueOf((Boolean) base);
            case CHAR: return valueOf((Character) base);
            case STRING: return valueOf((String) base);
            default: throw new IllegalStateException();
        }
    }
    
    enum PType { BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOLEAN, CHAR, STRING; }
}
