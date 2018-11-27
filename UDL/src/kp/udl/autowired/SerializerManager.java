/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired;

import java.util.HashMap;
import kp.udl.data.UDLValue;

/**
 *
 * @author Asus
 */
public final class SerializerManager
{
    private final HashMap<Class<?>, AutowiredSerializer<?>> serializers = new HashMap<>();
    
    public final void registerSerializer(AutowiredSerializer<?> serializer)
    {
        if(serializer == null)
            throw new NullPointerException();
        serializers.put(serializer.getJavaClass(), serializer);
    }
    
    public final boolean hasSerializer(Class<?> jclass) { return serializers.containsKey(jclass); }
    
    public final void removeSerializer(Class<?> jclass) { serializers.remove(jclass); }
    
    
    public final UDLValue extract(Object obj)
    {
        AutowiredSerializer serializer = serializers.getOrDefault(obj.getClass(), null);
        if(serializer != null)
            return serializer.serialize(obj);
        return Autowired.extract(obj, this);
    }
    
    public final <T> T inject(Class<T> jclass, UDLValue value)
    {
        AutowiredSerializer<?> serializer = serializers.getOrDefault(jclass, null);
        if(serializer != null)
            return (T) serializer.unserialize(value);
        return Autowired.inject(jclass, value, this);
    }
}
