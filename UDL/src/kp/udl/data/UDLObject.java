/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Asus
 */
public final class UDLObject extends UDLAbstractData
{
    private final Map<UDLValue, UDLValue> map;
    
    public UDLObject(Map<UDLValue, UDLValue> map)
    {
        if(map == null)
            throw new NullPointerException();
        this.map = map;
    }
    public UDLObject() { this(new HashMap<>()); }
    
    public static final UDLObject adapt(Map<String, UDLValue> map)
    {
        return new UDLObject(map.entrySet().stream().collect(Collectors.toMap(
                e -> new UDLString(e.getKey()), 
                e -> e.getValue()
        )));
    }
    
    @Override
    public final UDLDataType getDataType() { return UDLDataType.OBJECT; }

    @Override
    public final int getInt() { return map.size(); }

    @Override
    public final long getLong() { return map.size(); }

    @Override
    public final float getFloat() { return map.size(); }

    @Override
    public final double getDouble() { return map.size(); }

    @Override
    public final boolean getBoolean() { return !map.isEmpty(); }

    @Override
    public final String getString() { return map.toString(); }

    @Override
    public final List<UDLValue> getList()
    {
        UDLValue[] array = new UDLValue[map.size()];
        int count = 0;
        for(Map.Entry<UDLValue, UDLValue> e : map.entrySet())
            array[count++] = new UDLArray(e.getKey(), e.getValue());
        return Arrays.asList(array);
    }

    @Override
    public final Map<UDLValue, UDLValue> getMap() { return map; }

    @Override
    final boolean equals(UDLAbstractData o)
    {
        return o.getDataType() == UDLDataType.OBJECT && map.equals(o.getMap());
    }

    @Override
    public final int hashCode() { return map.hashCode(); }
    
    @Override public final <V extends UDLValue> V get(UDLValue key) { return (V) map.get(key); }
    @Override public final int getInt(UDLValue key) { return map.get(key).getInt(); }
    @Override public final long getLong(UDLValue key) { return map.get(key).getLong(); }
    @Override public final float getFloat(UDLValue key) { return map.get(key).getFloat(); }
    @Override public final double getDouble(UDLValue key) { return map.get(key).getDouble(); }
    @Override public final boolean getBoolean(UDLValue key) { return map.get(key).getBoolean(); }
    @Override public final String getString(UDLValue key) { return map.get(key).getString(); }
    @Override public final List<UDLValue> getList(UDLValue key) { return map.get(key).getList(); }
    @Override public final Map<UDLValue, UDLValue> getMap(UDLValue key) { return map.get(key).getMap(); }
    
    @Override public final <V extends UDLValue> V get(String key) { return (V) map.get(new UDLString(key)); }
    @Override public final int getInt(String key) { return map.get(new UDLString(key)).getInt(); }
    @Override public final long getLong(String key) { return map.get(new UDLString(key)).getLong(); }
    @Override public final float getFloat(String key) { return map.get(new UDLString(key)).getFloat(); }
    @Override public final double getDouble(String key) { return map.get(new UDLString(key)).getDouble(); }
    @Override public final boolean getBoolean(String key) { return map.get(new UDLString(key)).getBoolean(); }
    @Override public final String getString(String key) { return map.get(new UDLString(key)).getString(); }
    @Override public final List<UDLValue> getList(String key) { return map.get(new UDLString(key)).getList(); }
    @Override public final Map<UDLValue, UDLValue> getMap(String key) { return map.get(new UDLString(key)).getMap(); }
    
    @Override public final <V extends UDLValue> V get(int index) { return (V) map.get(new UDLInteger(index)); }
    @Override public final int getInt(int index) { return map.get(new UDLInteger(index)).getInt(); }
    @Override public final long getLong(int index) { return map.get(new UDLInteger(index)).getLong(); }
    @Override public final float getFloat(int index) { return map.get(new UDLInteger(index)).getFloat(); }
    @Override public final double getDouble(int index) { return map.get(new UDLInteger(index)).getDouble(); }
    @Override public final boolean getBoolean(int index) { return map.get(new UDLInteger(index)).getBoolean(); }
    @Override public final String getString(int index) { return map.get(new UDLInteger(index)).getString(); }
    @Override public final List<UDLValue> getList(int index) { return map.get(new UDLInteger(index)).getList(); }
    @Override public final Map<UDLValue, UDLValue> getMap(int index) { return map.get(new UDLInteger(index)).getMap(); }
    
    @Override public final UDLValue set(UDLValue key, UDLValue value) { map.put(key, value); return this; }
    @Override public final UDLValue setInt(UDLValue key, int value) { map.put(key, new UDLInteger(value)); return this; }
    @Override public final UDLValue setLong(UDLValue key, long value) { map.put(key, new UDLInteger(value)); return this; }
    @Override public final UDLValue setFloat(UDLValue key, float value) { map.put(key, new UDLFloat(value)); return this; }
    @Override public final UDLValue setDouble(UDLValue key, double value) { map.put(key, new UDLFloat(value)); return this; }
    @Override public final UDLValue setBoolean(UDLValue key, boolean value) { map.put(key, value ? TRUE : FALSE); return this; }
    @Override public final UDLValue setString(UDLValue key, String value) { map.put(key, new UDLString(value)); return this; }
    @Override public final UDLValue setList(UDLValue key, List<UDLValue> value) { map.put(key, new UDLArray(value)); return this; }
    @Override public final UDLValue setMap(UDLValue key, Map<UDLValue, UDLValue> value) { map.put(key, new UDLObject(value)); return this; }
    @Override public final UDLValue setIdentifier(UDLValue key, String identifier) { map.put(key, new UDLIdentifier(identifier)); return this; }
    @Override public final UDLValue setVariableValue(UDLValue key, String varvalue) { map.put(key, new UDLVariableValue(varvalue)); return this; }
    
    @Override public final UDLValue set(String key, UDLValue value) { map.put(new UDLString(key), value); return this; }
    @Override public final UDLValue setInt(String key, int value) { map.put(new UDLString(key), new UDLInteger(value)); return this; }
    @Override public final UDLValue setLong(String key, long value) { map.put(new UDLString(key), new UDLInteger(value)); return this; }
    @Override public final UDLValue setFloat(String key, float value) { map.put(new UDLString(key), new UDLFloat(value)); return this; }
    @Override public final UDLValue setDouble(String key, double value) { map.put(new UDLString(key), new UDLFloat(value)); return this; }
    @Override public final UDLValue setBoolean(String key, boolean value) { map.put(new UDLString(key), value ? TRUE : FALSE); return this; }
    @Override public final UDLValue setString(String key, String value) { map.put(new UDLString(key), new UDLString(value)); return this; }
    @Override public final UDLValue setList(String key, List<UDLValue> value) { map.put(new UDLString(key), new UDLArray(value)); return this; }
    @Override public final UDLValue setMap(String key, Map<UDLValue, UDLValue> value) { map.put(new UDLString(key), new UDLObject(value)); return this; }
    @Override public final UDLValue setIdentifier(String key, String identifier) { map.put(new UDLString(key), new UDLIdentifier(identifier)); return this; }
    @Override public final UDLValue setVariableValue(String key, String varvalue) { map.put(new UDLString(key), new UDLVariableValue(varvalue)); return this; }
    
    @Override public final UDLValue set(int index, UDLValue value) { map.put(new UDLInteger(index), value); return this; }
    @Override public final UDLValue setNull(int index) { map.put(new UDLInteger(index), NULL); return this; }
    @Override public final UDLValue setInt(int index, int value) { map.put(new UDLInteger(index), new UDLInteger(value)); return this; }
    @Override public final UDLValue setLong(int index, long value) { map.put(new UDLInteger(index), new UDLInteger(value)); return this; }
    @Override public final UDLValue setFloat(int index, float value) { map.put(new UDLInteger(index), new UDLFloat(value)); return this; }
    @Override public final UDLValue setDouble(int index, double value) { map.put(new UDLInteger(index), new UDLFloat(value)); return this; }
    @Override public final UDLValue setBoolean(int index, boolean value) { map.put(new UDLInteger(index), value ? TRUE : FALSE); return this; }
    @Override public final UDLValue setString(int index, String value) { map.put(new UDLInteger(index), new UDLString(value)); return this; }
    @Override public final UDLValue setList(int index, List<UDLValue> value) { map.put(new UDLInteger(index), new UDLArray(value)); return this; }
    @Override public final UDLValue setMap(int index, Map<UDLValue, UDLValue> value) { map.put(new UDLInteger(index), new UDLObject(value)); return this; }
    @Override public final UDLValue setIdentifier(int index, String identifier) { map.put(new UDLInteger(index), new UDLIdentifier(identifier)); return this; }
    @Override public final UDLValue setVariableValue(int index, String varvalue) { map.put(new UDLInteger(index), new UDLVariableValue(varvalue)); return this; }
    
    @Override public final UDLValue remove(UDLValue key) { return map.remove(key); }
    @Override public final UDLValue remove(String key) { return map.remove(new UDLString(key)); }
    @Override public final UDLValue remove(int index) { return map.remove(new UDLInteger(index)); }
    
    @Override public final boolean containsKey(UDLValue key) { return map.containsKey(key); }
    @Override public final boolean containsKey(String key) { return map.containsKey(new UDLString(key)); }
    @Override public final boolean containsKey(int key) { return map.containsKey(new UDLInteger(key)); }
    
    @Override public final boolean contains(UDLValue value) { return map.containsValue(value); }
    @Override public final boolean contains(int value) { return map.containsValue(new UDLInteger(value)); }
    @Override public final boolean contains(long value) { return map.containsValue(new UDLInteger(value)); }
    @Override public final boolean contains(float value) { return map.containsValue(new UDLFloat(value)); }
    @Override public final boolean contains(double value) { return map.containsValue(new UDLFloat(value)); }
    @Override public final boolean contains(boolean value) { return map.containsValue(value ? TRUE : FALSE); }
    @Override public final boolean contains(String value) { return map.containsValue(new UDLString(value)); }
    @Override public final boolean contains(List<UDLValue> value) { return map.containsValue(new UDLArray(value)); }
    @Override public final boolean contains(Map<UDLValue, UDLValue> value) { return map.containsValue(new UDLObject(value)); }
    
    @Override public final int size() { return map.size(); }
    @Override public final int length() { return map.size(); }
    @Override public final boolean isEmpty() { return map.isEmpty(); }
    
    
    @Override
    public final UDLValue setCommand(String commandName, UDLValue... args)
    {
        UDLCommand cmd = new UDLCommand(commandName, args);
        map.put(cmd, NULL);
        return this;
    }
}
