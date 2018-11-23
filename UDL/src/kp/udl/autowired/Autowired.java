/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired;

import java.util.LinkedHashMap;
import kp.udl.autowired.AutowiredCache.PropertyCache;
import kp.udl.data.UDLValue;
import static kp.udl.data.UDLValue.NULL;
import static kp.udl.data.UDLValue.valueOf;
import kp.udl.exception.UDLException;

/**
 *
 * @author Asus
 */
public final class Autowired
{
    private Autowired() {}
    
    public static final <T> T inject(Class<T> jclass, UDLValue source)
    {
        try
        {
            AutowiredCache cache = AutowiredCache.getCache(jclass);
            T obj = jclass.newInstance();
            
            for(PropertyCache prop : cache.getProperties())
            {
                UDLValue value = source.get(valueOf(prop.name));
                if(value == null || value == UDLValue.NULL)
                    continue;
                Object injectedObject = prop.type.inject(value);
                prop.field.set(obj, injectedObject);
            }
            
            return obj;
        }
        catch(Throwable ex)
        {
            throw new UDLException("Autowired exception has been ocurred:", ex);
        }
    }
    
    public static final UDLValue extract(Object source)
    {
        try
        {
            AutowiredCache cache = AutowiredCache.getCache(source.getClass());
            LinkedHashMap<UDLValue, UDLValue> obj = new LinkedHashMap<>();
            
            for(PropertyCache prop : cache.getProperties())
            {
                Object fieldValue = prop.field.get(source);
                UDLValue key = valueOf(prop.name);
                UDLValue value = fieldValue == null ? NULL : prop.type.extract(fieldValue);
                obj.put(key, value);
            }
            
            return valueOf(obj);
        }
        catch(Throwable ex)
        {
            throw new UDLException("Autowired exception has been ocurred:", ex);
        }
    }
}
