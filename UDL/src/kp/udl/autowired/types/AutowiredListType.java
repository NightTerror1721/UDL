/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import kp.udl.data.UDLValue;
import static kp.udl.data.UDLValue.valueOf;

/**
 *
 * @author Asus
 */
public final class AutowiredListType extends AutowiredType
{
    private final boolean linked;
    private final AutowiredType valuesType;
    
    public AutowiredListType(boolean linked, AutowiredType valuesType)
    {
        this.linked = linked;
        this.valuesType = valuesType;
    }
    
    @Override
    public final AutowiredType getValueType() { return valuesType; }
    
    @Override
    public final boolean isList() { return true; }
    
    @Override
    final Object arrayInstance(int len)
    {
        return linked ? new LinkedList[len] : new ArrayList[len];
    }

    @Override
    public Object inject(UDLValue base)
    {
        List<UDLValue> baseList = base.getList();
        List list = linked ? new LinkedList<>() : new ArrayList<>(baseList.size());
        for(UDLValue value : baseList)
            list.add(valuesType.inject(value));
        return list;
    }

    @Override
    public final UDLValue extract(Object base)
    {
        List baseList = (List) base;
        List<UDLValue> list = new LinkedList<>();
        for(Object value : baseList)
            list.add(valuesType.extract(value));
        return valueOf(list);
    }
}
