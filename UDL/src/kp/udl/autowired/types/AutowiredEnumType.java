/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import java.lang.reflect.Array;
import kp.udl.autowired.SerializerManager;
import kp.udl.data.UDLValue;
import static kp.udl.data.UDLValue.valueOf;

/**
 *
 * @author Marc
 */
public final class AutowiredEnumType extends AutowiredType
{
    private final Class<? extends Enum> jclass;
    
    public AutowiredEnumType(Class<?> jclass)
    {
        if(!jclass.isEnum())
            throw new IllegalStateException();
        this.jclass = (Class<? extends Enum>) jclass;
    }

    @Override
    final Object arrayInstance(int len)
    {
        return Array.newInstance(jclass, len);
    }

    @Override
    public final Object inject(UDLValue base, SerializerManager smanager)
    {
        try { return Enum.valueOf(jclass, base.getString()); }
        catch(Throwable ex) { return null; }
    }

    @Override
    public final UDLValue extract(Object base, SerializerManager smanager)
    {
        Enum e = (Enum) base;
        return valueOf(e.name());
    }
}
