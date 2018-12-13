/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired;

import java.lang.reflect.Method;
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
    
    public static final <T> T inject(Class<T> jclass, UDLValue source, SerializerManager smanager)
    {
        if(smanager != null && smanager.hasSerializer(jclass))
            return smanager.inject(jclass, source);
        try
        {
            AutowiredCache cache = AutowiredCache.getCache(jclass);
            T obj = buildInjectedObject(cache, jclass);
            
            for(PropertyCache prop : cache.getProperties())
            {
                UDLValue value = source.get(valueOf(prop.name));
                if(value == null || value == UDLValue.NULL)
                    continue;
                Object injectedObject = injectProperty(prop, smanager, obj, value);
                if(prop.set != null)
                    prop.set.invoke(obj, injectedObject);
                else prop.field.set(obj, injectedObject);
            }
            
            Method after = cache.getAfterBuild();
            if(after != null)
                after.invoke(obj);
            
            return obj;
        }
        catch(Throwable ex)
        {
            throw new UDLException("Autowired exception has been ocurred:", ex);
        }
    }
    
    public static final UDLValue extract(Object source, SerializerManager smanager)
    {
        if(smanager != null && smanager.hasSerializer(source.getClass()))
            return smanager.extract(source);
        try
        {
            AutowiredCache cache = AutowiredCache.getCache(source.getClass());
            LinkedHashMap<UDLValue, UDLValue> obj = new LinkedHashMap<>();
            
            for(PropertyCache prop : cache.getProperties())
            {
                Object fieldValue = prop.get != null ? prop.get.invoke(source) : prop.field.get(source);
                UDLValue key = valueOf(prop.name);
                UDLValue value = fieldValue == null ? NULL : prop.type.extract(fieldValue, smanager);
                obj.put(key, value);
            }
            
            return valueOf(obj);
        }
        catch(Throwable ex)
        {
            throw new UDLException("Autowired exception has been ocurred:", ex);
        }
    }
    
    
    private static <T> T buildInjectedObject(AutowiredCache cache, Class<T> jclass) throws Throwable
    {
        T obj;
        
        Method builder = cache.getBuilder();
        if(builder != null)
            obj = (T) builder.invoke(null);
        else obj = jclass.newInstance();
        
        Method before = cache.getBeforeBuild();
        if(before != null)
            before.invoke(obj);
        
        return obj;
    }
    
    private static Object injectProperty(PropertyCache prop, SerializerManager smanager, Object obj, UDLValue value) throws Throwable
    {
        Object injectedObject = null;
        if(prop.injector != null)
            injectedObject = prop.injector.invoke(obj, value);
        
        if(injectedObject == null)
            injectedObject = prop.type.inject(value, smanager);
        return injectedObject;
    }
}
