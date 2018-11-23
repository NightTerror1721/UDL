/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import kp.udl.autowired.AutowiredCache.PropertyCache;
import kp.udl.autowired.types.AutowiredType;

/**
 *
 * @author Asus
 */
public final class AutowiredCache
{
    private final Class<?> clazz;
    private final List<PropertyCache> props;
    
    private AutowiredCache(Class<?> clazz)
    {
        this.clazz = clazz;
        props = Collections.unmodifiableList(loadProperties(clazz, new LinkedList<>()));
    }
    
    private static LinkedList<PropertyCache> loadProperties(Class<?> clazz, LinkedList<PropertyCache> props)
    {
        if(clazz == Object.class)
            return props;
        Class<?> superClazz = clazz.getSuperclass();
        if(superClazz != null)
            props = loadProperties(superClazz, props);
        if(CACHE.containsKey(clazz))
        {
            AutowiredCache ac = CACHE.get(clazz);
            props.addAll(ac.props);
        }
        else
        {
            for(Field field : clazz.getDeclaredFields())
            {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod) || !field.isAnnotationPresent(Property.class))
                    continue;
                Property prop = field.getAnnotation(Property.class);
                PropertyCache cache = new PropertyCache(field, prop);
                props.add(cache);
            }
        }
        return props;
    }
    
    public final Class<?> getAutowiredClass() { return clazz; }
    
    public final int getPropertyCount() { return props.size(); }
    
    final List<PropertyCache> getProperties() { return props; }
    
    
    
    
    private static final HashMap<Class<?>, AutowiredCache> CACHE = new HashMap<>();
    
    public static final AutowiredCache getCache(Class<?> clazz)
    {
        if(clazz == null)
            throw new NullPointerException();
        AutowiredCache cache = CACHE.getOrDefault(clazz, null);
        if(cache == null)
        {
            cache = new AutowiredCache(clazz);
            CACHE.put(clazz, cache);
        }
        return cache;
    }
    
    
    static final class PropertyCache
    {
        final Field field;
        final String name;
        final AutowiredType type;
        
        private PropertyCache(Field field, Property property)
        {
            this.field = field;
            this.name = property.name().isEmpty() ? field.getName() : property.name();
            this.type = AutowiredType.decode(field);
            
            if(!field.isAccessible())
                field.setAccessible(true);
        }
        
        public final Field getField() { return field; }
        public final String getName() { return name; }
        
        public final AutowiredType getType() { return type; }
    }
    
    
}
