/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import java.lang.reflect.Array;
import kp.udl.autowired.Autowired;
import kp.udl.data.UDLValue;

/**
 *
 * @author Asus
 */
public final class AutowiredPojoType extends AutowiredType
{
    private final Class<?> jclass;
    
    public AutowiredPojoType(Class<?> jclass)
    {
        this.jclass = jclass;
    }
    
    @Override
    public final boolean isPojo() { return true; }

    @Override
    public Class<?> getBaseClass() { return jclass; }

    @Override
    final Object arrayInstance(int len) { return Array.newInstance(jclass, len); }

    @Override
    public final Object inject(UDLValue base)
    {
        return Autowired.inject(jclass, base);
    }

    @Override
    public final UDLValue extract(Object base)
    {
        return Autowired.extract(base);
    }
    
    
}
