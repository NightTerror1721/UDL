/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public abstract class UDLValue
{
    UDLValue() {}
    
    public abstract UDLDataType getDataType();
    public abstract UDLElementType getElementType();
    
    /* Direct java getters */
    public abstract int getInt();
    public abstract long getLong();
    public abstract float getFloat();
    public abstract double getDouble();
    public abstract boolean getBoolean();
    public abstract String getString();
    public abstract List<UDLValue> getList();
    public abstract Map<UDLValue, UDLValue> getMap();
    
    
    /* Array getters/setters/adders */
    public <V extends UDLValue> V get(int index) { return (V) getList().get(index); }
    public int getInt(int index) { return getList().get(index).getInt(); }
    public long getLong(int index) { return getList().get(index).getLong(); }
    public float getFloat(int index) { return getList().get(index).getFloat(); }
    public double getDouble(int index) { return getList().get(index).getDouble(); }
    public boolean getBoolean(int index) { return getList().get(index).getBoolean(); }
    public String getString(int index) { return getList().get(index).getString(); }
    public List<UDLValue> getList(int index) { return getList().get(index).getList(); }
    public Map<UDLValue, UDLValue> getMap(int index) { return getList().get(index).getMap(); }
    
    public UDLValue set(int index, UDLValue value) { getList().set(index, value); return this; }
    public UDLValue setNull(int index) { getList().set(index, NULL); return this; }
    public UDLValue setInt(int index, int value) { getList().set(index, new UDLInteger(value)); return this; }
    public UDLValue setLong(int index, long value) { getList().set(index, new UDLInteger(value)); return this; }
    public UDLValue setFloat(int index, float value) { getList().set(index, new UDLFloat(value)); return this; }
    public UDLValue setDouble(int index, double value) { getList().set(index, new UDLFloat(value)); return this; }
    public UDLValue setBoolean(int index, boolean value) { getList().set(index, value ? TRUE : FALSE); return this; }
    public UDLValue setString(int index, String value) { getList().set(index, new UDLString(value)); return this; }
    public UDLValue setList(int index, List<UDLValue> value) { getList().set(index, new UDLArray(value)); return this; }
    public UDLValue setMap(int index, Map<UDLValue, UDLValue> value) { getList().set(index, new UDLObject(value)); return this; }
    public UDLValue setIdentifier(int index, String identifier) { getList().set(index, new UDLIdentifier(identifier)); return this; }
    public UDLValue setVariableValue(int index, String varvalue) { getList().set(index, new UDLVariableValue(varvalue)); return this; }
    
    public UDLValue add(UDLValue value) { getList().add(value); return this; }
    public UDLValue addNull() { getList().add(NULL); return this; }
    public UDLValue addInt(int value) { getList().add(new UDLInteger(value)); return this; }
    public UDLValue addLong(long value) { getList().add(new UDLInteger(value)); return this; }
    public UDLValue addFloat(float value) { getList().add(new UDLFloat(value)); return this; }
    public UDLValue addDouble(double value) { getList().add(new UDLFloat(value)); return this; }
    public UDLValue addBoolean(boolean value) { getList().add(value ? TRUE : FALSE); return this; }
    public UDLValue addString(String value) { getList().add(new UDLString(value)); return this; }
    public UDLValue addList(List<UDLValue> value) { getList().add(new UDLArray(value)); return this; }
    public UDLValue addMap(Map<UDLValue, UDLValue> value) { getList().add(new UDLObject(value)); return this; }
    public UDLValue addIdentifier(String identifier) { getList().add(new UDLIdentifier(identifier)); return this; }
    public UDLValue addVariableValue(String varvalue) { getList().add(new UDLVariableValue(varvalue)); return this; }
    
    public UDLValue add(int index, UDLValue value) { getList().add(index, value); return this; }
    public UDLValue addNull(int index) { getList().add(index, NULL); return this; }
    public UDLValue addInt(int index, int value) { getList().add(index, new UDLInteger(value)); return this; }
    public UDLValue addLong(int index, long value) { getList().add(index, new UDLInteger(value)); return this; }
    public UDLValue addFloat(int index, float value) { getList().add(index, new UDLFloat(value)); return this; }
    public UDLValue addDouble(int index, double value) { getList().add(index, new UDLFloat(value)); return this; }
    public UDLValue addBoolean(int index, boolean value) { getList().add(index, value ? TRUE : FALSE); return this; }
    public UDLValue addString(int index, String value) { getList().add(index, new UDLString(value)); return this; }
    public UDLValue addList(int index, List<UDLValue> value) { getList().add(index, new UDLArray(value)); return this; }
    public UDLValue addMap(int index, Map<UDLValue, UDLValue> value) { getList().add(index, new UDLObject(value)); return this; }
    public UDLValue addIdentifier(int index, String identifier) { getList().add(index, new UDLIdentifier(identifier)); return this; }
    public UDLValue addVariableValue(int index, String varvalue) { getList().add(index, new UDLVariableValue(varvalue)); return this; }
    
    public UDLValue remove(int index) { return getList().remove(index); }
    
    public boolean contains(UDLValue value) { return getList().contains(value); }
    public boolean contains(int value) { return getList().contains(new UDLInteger(value)); }
    public boolean contains(long value) { return getList().contains(new UDLInteger(value)); }
    public boolean contains(float value) { return getList().contains(new UDLFloat(value)); }
    public boolean contains(double value) { return getList().contains(new UDLFloat(value)); }
    public boolean contains(boolean value) { return getList().contains(value ? TRUE : FALSE); }
    public boolean contains(String value) { return getList().contains(new UDLString(value)); }
    public boolean contains(List<UDLValue> value) { return getList().contains(new UDLArray(value)); }
    public boolean contains(Map<UDLValue, UDLValue> value) { return getList().contains(new UDLObject(value)); }
    
    
    /* Object getters/setters */
    public <V extends UDLValue> V get(UDLValue key) { return (V) getMap().get(key); }
    public int getInt(UDLValue key) { return getMap().get(key).getInt(); }
    public long getLong(UDLValue key) { return getMap().get(key).getLong(); }
    public float getFloat(UDLValue key) { return getMap().get(key).getFloat(); }
    public double getDouble(UDLValue key) { return getMap().get(key).getDouble(); }
    public boolean getBoolean(UDLValue key) { return getMap().get(key).getBoolean(); }
    public String getString(UDLValue key) { return getMap().get(key).getString(); }
    public List<UDLValue> getList(UDLValue key) { return getMap().get(key).getList(); }
    public Map<UDLValue, UDLValue> getMap(UDLValue key) { return getMap().get(key).getMap(); }
    
    public <V extends UDLValue> V get(String key) { return (V) getMap().get(new UDLString(key)); }
    public int getInt(String key) { return getMap().get(new UDLString(key)).getInt(); }
    public long getLong(String key) { return getMap().get(new UDLString(key)).getLong(); }
    public float getFloat(String key) { return getMap().get(new UDLString(key)).getFloat(); }
    public double getDouble(String key) { return getMap().get(new UDLString(key)).getDouble(); }
    public boolean getBoolean(String key) { return getMap().get(new UDLString(key)).getBoolean(); }
    public String getString(String key) { return getMap().get(new UDLString(key)).getString(); }
    public List<UDLValue> getList(String key) { return getMap().get(new UDLString(key)).getList(); }
    public Map<UDLValue, UDLValue> getMap(String key) { return getMap().get(new UDLString(key)).getMap(); }
    
    public UDLValue set(UDLValue key, UDLValue value) { getMap().put(key, value); return this; }
    public UDLValue setInt(UDLValue key, int value) { getMap().put(key, new UDLInteger(value)); return this; }
    public UDLValue setLong(UDLValue key, long value) { getMap().put(key, new UDLInteger(value)); return this; }
    public UDLValue setFloat(UDLValue key, float value) { getMap().put(key, new UDLFloat(value)); return this; }
    public UDLValue setDouble(UDLValue key, double value) { getMap().put(key, new UDLFloat(value)); return this; }
    public UDLValue setBoolean(UDLValue key, boolean value) { getMap().put(key, value ? TRUE : FALSE); return this; }
    public UDLValue setString(UDLValue key, String value) { getMap().put(key, new UDLString(value)); return this; }
    public UDLValue setList(UDLValue key, List<UDLValue> value) { getMap().put(key, new UDLArray(value)); return this; }
    public UDLValue setMap(UDLValue key, Map<UDLValue, UDLValue> value) { getMap().put(key, new UDLObject(value)); return this; }
    public UDLValue setIdentifier(UDLValue key, String identifier) { getMap().put(key, new UDLIdentifier(identifier)); return this; }
    public UDLValue setVariableValue(UDLValue key, String varvalue) { getMap().put(key, new UDLVariableValue(varvalue)); return this; }
    
    public UDLValue set(String key, UDLValue value) { getMap().put(new UDLString(key), value); return this; }
    public UDLValue setInt(String key, int value) { getMap().put(new UDLString(key), new UDLInteger(value)); return this; }
    public UDLValue setLong(String key, long value) { getMap().put(new UDLString(key), new UDLInteger(value)); return this; }
    public UDLValue setFloat(String key, float value) { getMap().put(new UDLString(key), new UDLFloat(value)); return this; }
    public UDLValue setDouble(String key, double value) { getMap().put(new UDLString(key), new UDLFloat(value)); return this; }
    public UDLValue setBoolean(String key, boolean value) { getMap().put(new UDLString(key), value ? TRUE : FALSE); return this; }
    public UDLValue setString(String key, String value) { getMap().put(new UDLString(key), new UDLString(value)); return this; }
    public UDLValue setList(String key, List<UDLValue> value) { getMap().put(new UDLString(key), new UDLArray(value)); return this; }
    public UDLValue setMap(String key, Map<UDLValue, UDLValue> value) { getMap().put(new UDLString(key), new UDLObject(value)); return this; }
    public UDLValue setIdentifier(String key, String identifier) { getMap().put(new UDLString(key), new UDLIdentifier(identifier)); return this; }
    public UDLValue setVariableValue(String key, String varvalue) { getMap().put(new UDLString(key), new UDLVariableValue(varvalue)); return this; }
    
    public UDLValue remove(UDLValue key) { return getMap().remove(key); }
    public UDLValue remove(String key) { return getMap().remove(new UDLString(key)); }
    
    public boolean containsKey(UDLValue key) { return getMap().containsKey(key); }
    public boolean containsKey(String key) { return getMap().containsKey(new UDLString(key)); }
    public boolean containsKey(int key) { return getMap().containsKey(new UDLInteger(key)); }
    
    /* Collections size */
    public int size() { return 1; }
    public int length() { return 1; }
    public boolean isEmpty() { return false; }
    
    /* Commands */
    public UDLValue setCommand(String commandName, UDLValue... args)
    {
        UDLCommand cmd = new UDLCommand(commandName, args);
        getMap().put(cmd, NULL);
        return this;
    }
    
    
    /* Basic operations */
    @Override
    public final boolean equals(Object o)
    {
        return o instanceof UDLValue && equals((UDLValue) o);
    }
    public abstract boolean equals(UDLValue o);
    
    @Override
    public abstract int hashCode();
    
    @Override
    public final String toString() { return getString(); }
    
    
    /* CONSTANTS */
    public static final UDLValue NULL = UDLNull.INSTANCE;
    public static final UDLValue MINUSONE = new UDLInteger(-1);
    public static final UDLValue ZERO = new UDLInteger(0);
    public static final UDLValue ONE = new UDLInteger(1);
    public static final UDLValue TRUE = UDLBoolean.TRUE_INSTANCE;
    public static final UDLValue FALSE = UDLBoolean.FALSE_INSTANCE;
    
    
    /* Value Ofs */
    
    /*public static final UDLValue valueOf(Object value)
    {
        if(value == null)
            return NULL;
        
        if(value instanceof UDLValue)
            return (UDLValue) value;
        
        if(value instanceof Number)
        {
            if(value instanceof Float || value instanceof Double)
                return new UDLFloat(((Number) value).doubleValue());
            return new UDLInteger(((Number) value).longValue());
        }
        
        if(value instanceof Boolean)
            return ((Boolean) value) ? TRUE : FALSE;
        
        if(value instanceof Character || value instanceof String)
            return new UDLString(value.toString());
        
        if(value instanceof List)
        
        if(value instanceof Map)
        {
            
        }
        
        if(value.getClass().isArray())
        {
            
        }
    }*/
    
    public static final UDLValue valueOf(UDLValue value) { return value; }
    
    public static final UDLValue valueOf(byte value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(short value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(int value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(long value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(float value) { return new UDLFloat(value); }
    public static final UDLValue valueOf(double value) { return new UDLFloat(value); }
    public static final UDLValue valueOf(boolean value) { return value ? TRUE : FALSE; }
    public static final UDLValue valueOf(char value) { return new UDLString(Character.toString(value)); }
    
    public static final UDLValue valueOf(Byte value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(Short value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(Integer value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(Long value) { return new UDLInteger(value); }
    public static final UDLValue valueOf(Float value) { return new UDLFloat(value); }
    public static final UDLValue valueOf(Double value) { return new UDLFloat(value); }
    public static final UDLValue valueOf(Boolean value) { return value ? TRUE : FALSE; }
    public static final UDLValue valueOf(Character value) { return new UDLString(Character.toString(value)); }
    
    public static final UDLValue valueOf(String value) { return new UDLString(value); }
    
    public static final UDLValue valueOf(UDLValue[] value) { return new UDLArray(value); }
    public static final UDLValue valueOf(byte[] value)
    {
        UDLValue[] array = new UDLValue[value.length];
        for(int i = 0; i < array.length; i++)
            array[i] = valueOf(value[i]);
        return new UDLArray(array);
    }
    public static final UDLValue valueOf(short[] value)
    {
        UDLValue[] array = new UDLValue[value.length];
        for(int i = 0; i < array.length; i++)
            array[i] = valueOf(value[i]);
        return new UDLArray(array);
    }
    public static final UDLValue valueOf(int[] value)
    {
        return new UDLArray(Arrays.stream(value).mapToObj(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(long[] value)
    {
        return new UDLArray(Arrays.stream(value).mapToObj(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(float[] value)
    {
        UDLValue[] array = new UDLValue[value.length];
        for(int i = 0; i < array.length; i++)
            array[i] = valueOf(value[i]);
        return new UDLArray(array);
    }
    public static final UDLValue valueOf(double[] value)
    {
        return new UDLArray(Arrays.stream(value).mapToObj(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(boolean[] value)
    {
        UDLValue[] array = new UDLValue[value.length];
        for(int i = 0; i < array.length; i++)
            array[i] = valueOf(value[i]);
        return new UDLArray(array);
    }
    public static final UDLValue valueOf(char[] value)
    {
        UDLValue[] array = new UDLValue[value.length];
        for(int i = 0; i < array.length; i++)
            array[i] = valueOf(value[i]);
        return new UDLArray(array);
    }
    
    public static final UDLValue valueOf(Byte[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(Short[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(Integer[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(Long[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(Float[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(Double[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(Boolean[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    public static final UDLValue valueOf(Character[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    
    public static final UDLValue valueOf(String[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    
    public static final UDLValue valueOf(List<UDLValue>[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    
    public static final UDLValue valueOf(Map<UDLValue, UDLValue>[] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    
    public static final UDLValue valueOf(UDLValue[][] value)
    {
        return new UDLArray(Arrays.stream(value).map(UDLValue::valueOf).toArray(UDLValue[]::new));
    }
    
    public static final UDLValue valueOf(List<UDLValue> value) { return new UDLArray(value); }
    
    public static final UDLValue valueOf(Map<UDLValue, UDLValue> value) { return new UDLObject(value); }
    
    
    /* Quick creators */
    
    public static final UDLValue array(int length) { return new UDLArray(Arrays.asList(new UDLValue[length])); }
    public static final UDLValue arrayList() { return new UDLArray(new ArrayList<>()); }
    public static final UDLValue linkedList() { return new UDLArray(new LinkedList<>()); }
    public static final UDLValue object() { return new UDLObject(new HashMap<>()); }
    public static final UDLValue linkedObject() { return new UDLObject(new LinkedHashMap<>()); }
    public static final UDLValue identifier(String identifier) { return new UDLIdentifier(identifier); }
    public static final UDLValue variableValue(String varName) { return new UDLVariableValue(varName); }
    public static final UDLValue command(String commandName, UDLValue... args) { return new UDLCommand(commandName, args); }
}
