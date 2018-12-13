/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import kp.udl.autowired.AutowiredCache.PropertyCache;
import kp.udl.autowired.types.AutowiredType;
import kp.udl.data.UDLValue;

/**
 *
 * @author Asus
 */
public final class AutowiredCache
{
    private final Class<?> clazz;
    private final List<PropertyCache> props;
    private final Method builder;
    private final Method beforeBuild;
    private final Method afterBuild;
    
    private AutowiredCache(Class<?> clazz)
    {
        this.clazz = clazz;
        props = Collections.unmodifiableList(loadProperties(clazz, new LinkedList<>()));
        if(clazz.isAnnotationPresent(InjectOptions.class))
        {
            InjectOptions ops = clazz.getAnnotation(InjectOptions.class);
            builder = findMethod(clazz, ops.builder(), true, clazz);
            beforeBuild = findMethod(clazz, ops.beforeBuild(), false, Void.TYPE);
            afterBuild = findMethod(clazz, ops.afterBuild(), false, Void.TYPE);
        }
        else
        {
            builder = null;
            beforeBuild = null;
            afterBuild = null;
        }
    }
    
    private static LinkedList<PropertyCache> loadProperties(Class<?> clazz, LinkedList<PropertyCache> props)
    {
        if(clazz == Object.class)
            return props;
        Class<?> superClazz = clazz.getSuperclass();
        if(superClazz != null)
        {
            if(CACHE.containsKey(superClazz))
            {
                AutowiredCache ac = CACHE.get(superClazz);
                props.addAll(ac.props);
            }
            else props = loadProperties(superClazz, props);
        }
        for(Field field : clazz.getDeclaredFields())
        {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod) || !field.isAnnotationPresent(Property.class))
                continue;
            Property prop = field.getAnnotation(Property.class);
            PropertyCache cache = new PropertyCache(field, prop);
            props.add(cache);
        }
        return props;
    }
    
    public final Class<?> getAutowiredClass() { return clazz; }
    
    public final int getPropertyCount() { return props.size(); }
    
    final List<PropertyCache> getProperties() { return props; }
    
    final Method getBuilder() { return builder; }
    final Method getBeforeBuild() { return beforeBuild; }
    final Method getAfterBuild() { return afterBuild; }
    
    
    
    
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
        final Method set;
        final Method get;
        final Method injector;
        
        private PropertyCache(Field field, Property property)
        {
            this.field = field;
            this.name = property.name().isEmpty() ? field.getName() : property.name();
            this.type = AutowiredType.decode(field, property);
            this.set = findGSMethod(field, property.set(), true);
            this.get = findGSMethod(field, property.get(), false);
            this.injector = findInjectorMethod(field, property.customInjector());
            
            if(!field.isAccessible())
                field.setAccessible(true);
        }
        
        public final Field getField() { return field; }
        public final String getName() { return name; }
        
        public final AutowiredType getType() { return type; }
        
        public final Method getGetMethod() { return get; }
        public final Method getSetMethod() { return set; }
    }
    
    private static Method findGSMethod(Field field, String name, boolean isSet)
    {
        Class<?> ret = isSet ? Void.TYPE : field.getType();
        Class<?>[] args = isSet ? new Class[] { field.getType() } : new Class[] {};
        return findMethod(field.getDeclaringClass(), name, false, ret, args);
    }
    
    private static Method findInjectorMethod(Field field, String name)
    {
        Class<?> ret = field.getType();
        Class<?>[] args = new Class[] { UDLValue.class };
        return findMethod(field.getDeclaringClass(), name, false, ret, args);
    }
    
    private static Method findMethod(Class<?> jclass, String name, boolean isStatic, Class<?> returnType, Class<?>... argTypes)
    {
        if(name == null || name.isEmpty())
            return null;
        try
        {
            Method m = jclass.getDeclaredMethod(name, argTypes);
            if(m == null)
                return null;
            if(m.getReturnType() != returnType)
                return null;
            if(Modifier.isStatic(m.getModifiers()) != isStatic)
                return null;
            if(!m.isAccessible())
                m.setAccessible(true);
            return m;
        }
        catch(Throwable ex)
        {
            return null;
        }
    }
    
    
}
