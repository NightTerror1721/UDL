/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import kp.udl.autowired.SerializerManager;
import kp.udl.data.UDLValue;
import static kp.udl.data.UDLValue.valueOf;

/**
 *
 * @author Asus
 */
public class AutowiredSetType extends AutowiredType
{
    private final boolean linked;
    private final AutowiredType valuesType;
    
    public AutowiredSetType(boolean linked, AutowiredType valuesType)
    {
        this.linked = linked;
        this.valuesType = valuesType;
    }
    
    @Override
    public final AutowiredType getValueType() { return valuesType; }
    
    @Override
    public final boolean isSet() { return true; }
    
    @Override
    final Object arrayInstance(int len)
    {
        return linked ? new LinkedHashSet[len] : new HashSet[len];
    }

    @Override
    public Object inject(UDLValue base, SerializerManager smanager)
    {
        List<UDLValue> baseList = base.getList();
        Set set = linked ? new LinkedHashSet<>() : new HashSet<>(baseList.size());
        for(UDLValue value : baseList)
            set.add(valuesType.inject(value, smanager));
        return set;
    }

    @Override
    public final UDLValue extract(Object base, SerializerManager smanager)
    {
        Set baseSet = (Set) base;
        List<UDLValue> list = new LinkedList<>();
        for(Object value : baseSet)
            list.add(valuesType.extract(value, smanager));
        return valueOf(list);
    }
}
