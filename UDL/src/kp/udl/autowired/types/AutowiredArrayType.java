/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import kp.udl.autowired.SerializerManager;
import kp.udl.data.UDLValue;
import static kp.udl.data.UDLValue.valueOf;

/**
 *
 * @author Asus
 */
public final class AutowiredArrayType extends AutowiredType
{
    private final AutowiredType componentType;
    private final int depth;
    
    public AutowiredArrayType(AutowiredType componentType)
    {
        if(componentType.isArray())
        {
            this.componentType = componentType.getArrayComponentType();
            this.depth = componentType.getArrayDepth() + 1;
        }
        else
        {
            this.componentType = componentType;
            this.depth = 1;
        }
    }
    
    @Override
    public final AutowiredType getArrayComponentType() { return componentType; }
    
    @Override
    public final int getArrayDepth() { return depth; }
    
    @Override
    public final boolean isArray() { return false; }
    
    @Override
    final Object arrayInstance(int len)
    {
        Class<?> aclass = componentType.arrayInstance(1).getClass();
        return Array.newInstance(aclass, len);
    }

    @Override
    public final Object inject(UDLValue base, SerializerManager smanager)
    {
        List<UDLValue> list = base.getList();
        Object array = componentType.arrayInstance(list.size());
        int count = 0;
        for(UDLValue value : list)
            Array.set(array, count++, componentType.inject(value, smanager));
        return array;
    }
    
    @Override
    public final UDLValue extract(Object base, SerializerManager smanager)
    {
        int len = Array.getLength(base);
        List<UDLValue> list = new LinkedList<>();
        for(int i=0;i<len;i++)
            list.add(componentType.extract(Array.get(base, i), smanager));
        return valueOf(list);
    }
}
