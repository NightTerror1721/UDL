/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.types;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kp.udl.autowired.Property;
import kp.udl.autowired.SerializerManager;
import kp.udl.autowired.types.AutowiredBaseType.PType;
import kp.udl.data.UDLValue;

/**
 *
 * @author Asus
 */
public abstract class AutowiredType
{
    private static final HashMap<Class<?>, AutowiredType> SCALAR_CACHE = new HashMap<>();
    
    AutowiredType() {}
    
    public Class<?> getBaseClass() { throw new IllegalStateException(); }
    public AutowiredType getArrayComponentType() { throw new IllegalStateException(); }
    public int getArrayDepth() { throw new IllegalStateException(); }
    public AutowiredType getValueType() { throw new IllegalStateException(); }
    public AutowiredType getKeyType() { throw new IllegalStateException(); }
    
    public boolean isBase() { return false; }
    public boolean isEnum() { return false; }
    public boolean isArray() { return false; }
    public boolean isList() { return false; }
    public boolean isSet() { return false; }
    public boolean isMap() { return false; }
    public boolean isPojo() { return false; }
    
    abstract Object arrayInstance(int len);
    
    public abstract Object inject(UDLValue base, SerializerManager smanager);
    
    public abstract UDLValue extract(Object base, SerializerManager smanager);
    
    
    
    private static AutowiredType decode(Type jtype, Property prop)
    {
        if(jtype instanceof Class<?>)
        {
            Class<?> jclass = (Class<?>) jtype;
            if(jclass.isEnum())
            {
                return new AutowiredEnumType(jclass, getInvalidEnumValue(jclass, prop), prop.enumById());
            }
            else if(jclass.isArray())
            {
                Class<?> base = jclass.getComponentType();
                return new AutowiredArrayType(decode(base, prop));
            }
            else if(SCALAR_CACHE.containsKey(jclass))
                return SCALAR_CACHE.get(jclass);
            return new AutowiredPojoType(jclass);
        }
        else if(jtype instanceof ParameterizedType)
        {
            ParameterizedType ptype = (ParameterizedType) jtype;
            Type[] pars = ((ParameterizedType) jtype).getActualTypeArguments();
            Type raw = ptype.getRawType();
            if(raw == ArrayList.class || raw == List.class)
                return new AutowiredListType(false, decode(pars[0], prop));
            if(raw == LinkedList.class)
                return new AutowiredListType(true, decode(pars[0], prop));
            if(raw == HashSet.class || raw == Set.class)
                return new AutowiredSetType(false, decode(pars[0], prop));
            if(raw == LinkedHashSet.class)
                return new AutowiredSetType(true, decode(pars[0], prop));
            if(raw == HashMap.class || raw == Map.class)
                return new AutowiredMapType(false, decode(pars[0], prop), decode(pars[1], prop));
            if(raw == LinkedHashMap.class)
                return new AutowiredMapType(true, decode(pars[0], prop), decode(pars[1], prop));
            return new AutowiredPojoType((Class<?>) ptype.getRawType());
        }
        throw new IllegalArgumentException("Invalid type to decode: " + jtype);
    }
    
    public static final AutowiredType decode(Field field, Property prop)
    {
        if(field.getType().getTypeParameters().length > 0)
            return decode(field.getGenericType(), prop);
        return decode(field.getType(), prop);
    }   
    
    private static Enum getInvalidEnumValue(Class<?> jclass, Property prop)
    {
        try { return Enum.valueOf((Class<? extends Enum>) jclass, prop.invalidEnumValue()); }
        catch(Throwable ex) { return null; }
    }
    
    
    /* INSTANCES */
    
    public static final AutowiredType P_BYTE = new AutowiredBaseType(PType.BYTE, Byte.TYPE);
    public static final AutowiredType P_SHORT = new AutowiredBaseType(PType.SHORT, Short.TYPE);
    public static final AutowiredType P_INT = new AutowiredBaseType(PType.INT, Integer.TYPE);
    public static final AutowiredType P_LONG = new AutowiredBaseType(PType.LONG, Long.TYPE);
    public static final AutowiredType P_FLOAT = new AutowiredBaseType(PType.FLOAT, Float.TYPE);
    public static final AutowiredType P_DOUBLE = new AutowiredBaseType(PType.DOUBLE, Double.TYPE);
    public static final AutowiredType P_BOOLEAN = new AutowiredBaseType(PType.BOOLEAN, Boolean.TYPE);
    public static final AutowiredType P_CHAR = new AutowiredBaseType(PType.CHAR, Character.TYPE);
    
    public static final AutowiredType BYTE = new AutowiredBaseType(PType.BYTE, Byte.class);
    public static final AutowiredType SHORT = new AutowiredBaseType(PType.SHORT, Short.class);
    public static final AutowiredType INTEGER = new AutowiredBaseType(PType.INT, Integer.class);
    public static final AutowiredType LONG = new AutowiredBaseType(PType.LONG, Long.class);
    public static final AutowiredType FLOAT = new AutowiredBaseType(PType.FLOAT, Float.class);
    public static final AutowiredType DOUBLE = new AutowiredBaseType(PType.DOUBLE, Double.class);
    public static final AutowiredType BOOLEAN = new AutowiredBaseType(PType.BOOLEAN, Boolean.class);
    public static final AutowiredType CHARACTER = new AutowiredBaseType(PType.CHAR, Character.class);
    public static final AutowiredType STRING = new AutowiredBaseType(PType.STRING, String.class);
    
    
    static final AutowiredType baseCache(AutowiredType type, Class<?> base)
    {
        SCALAR_CACHE.put(base, type);
        return type;
    }
}
