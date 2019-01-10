/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kp.udl.autowired.SerializerManager;
import kp.udl.data.UDLValue;
import static kp.udl.data.UDLValue.valueOf;

/**
 *
 * @author Asus
 */
public final class AutowiredMapType extends AutowiredType
{
    private final boolean linked;
    private final AutowiredType keyType;
    private final AutowiredType valuesType;
    
    public AutowiredMapType(boolean linked, AutowiredType keyType, AutowiredType valuesType)
    {
        this.linked = linked;
        this.keyType = keyType;
        this.valuesType = valuesType;
    }
    
    @Override
    public final AutowiredType getValueType() { return valuesType; }
    
    @Override
    public final AutowiredType getKeyType() { return keyType; }
    
    @Override
    public final boolean isMap() { return true; }
    
    @Override
    final Object arrayInstance(int len)
    {
        return new HashMap[len];
    }

    @Override
    public final Object inject(UDLValue base, SerializerManager smanager)
    {
        Map<UDLValue, UDLValue> baseMap = base.getMap();
        HashMap map = linked ? new LinkedHashMap<>(base.size()) : new HashMap<>(base.size());
        for(Map.Entry<UDLValue, UDLValue> e : baseMap.entrySet())
            map.put(keyType.inject(e.getKey(), smanager), valuesType.inject(e.getValue(), smanager));
        return map;
    }

    @Override
    public final UDLValue extract(Object base, SerializerManager smanager)
    {
        Map<?, ?> baseMap = (Map) base;
        Map<UDLValue, UDLValue> map = new HashMap<>();
        for(Map.Entry e : baseMap.entrySet())
            map.put(keyType.extract(e.getKey(), smanager), valuesType.extract(e.getValue(), smanager));
        return valueOf(map);
    }
}
