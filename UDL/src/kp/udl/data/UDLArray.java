/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public final class UDLArray extends UDLAbstractData
{
    private final List<UDLValue> array;
    
    public UDLArray(UDLValue... values)
    {
        if(values == null)
            throw new NullPointerException();
        this.array = Arrays.asList(values);
    }
    public UDLArray(List<UDLValue> list)
    {
        if(list == null)
            throw new NullPointerException();
        this.array = list;
    }
    public UDLArray() { this(new LinkedList<>()); }
    
    @Override
    public final UDLDataType getDataType() { return UDLDataType.ARRAY; }

    @Override
    public final int getInt() { return array.size(); }

    @Override
    public final long getLong() { return array.size(); }

    @Override
    public final float getFloat() { return array.size(); }

    @Override
    public final double getDouble() { return array.size(); }

    @Override
    public final boolean getBoolean() { return !array.isEmpty(); }

    @Override
    public final String getString() { return array.toString(); }

    @Override
    public final List<UDLValue> getList() { return array; }

    @Override
    public final Map<UDLValue, UDLValue> getMap()
    {
        HashMap<UDLValue, UDLValue> map = new HashMap<>();
        int count = 0;
        for(UDLValue value : array)
            map.put(new UDLInteger(count), value);
        return map;
    }

    @Override
    final boolean equals(UDLAbstractData o)
    {
        return o.getDataType() == UDLDataType.ARRAY && array.equals(o.getList());
    }

    @Override
    public final int hashCode() { return array.hashCode(); }
    
    
    @Override public final <V extends UDLValue> V get(int index) { return (V) array.get(index); }
    @Override public final int getInt(int index) { return array.get(index).getInt(); }
    @Override public final long getLong(int index) { return array.get(index).getLong(); }
    @Override public final float getFloat(int index) { return array.get(index).getFloat(); }
    @Override public final double getDouble(int index) { return array.get(index).getDouble(); }
    @Override public final boolean getBoolean(int index) { return array.get(index).getBoolean(); }
    @Override public final String getString(int index) { return array.get(index).getString(); }
    @Override public final List<UDLValue> getList(int index) { return array.get(index).getList(); }
    @Override public final Map<UDLValue, UDLValue> getMap(int index) { return array.get(index).getMap(); }
    
    @Override public final UDLValue set(int index, UDLValue value) { array.set(index, value); return this; }
    @Override public final UDLValue setNull(int index) { array.set(index, NULL); return this; }
    @Override public final UDLValue setInt(int index, int value) { array.set(index, new UDLInteger(value)); return this; }
    @Override public final UDLValue setLong(int index, long value) { array.set(index, new UDLInteger(value)); return this; }
    @Override public final UDLValue setFloat(int index, float value) { array.set(index, new UDLFloat(value)); return this; }
    @Override public final UDLValue setDouble(int index, double value) { array.set(index, new UDLFloat(value)); return this; }
    @Override public final UDLValue setBoolean(int index, boolean value) { array.set(index, value ? TRUE : FALSE); return this; }
    @Override public final UDLValue setString(int index, String value) { array.set(index, new UDLString(value)); return this; }
    @Override public final UDLValue setList(int index, List<UDLValue> value) { array.set(index, new UDLArray(value)); return this; }
    @Override public final UDLValue setMap(int index, Map<UDLValue, UDLValue> value) { array.set(index, new UDLObject(value)); return this; }
    @Override public final UDLValue setIdentifier(int index, String identifier) { array.set(index, new UDLIdentifier(identifier)); return this; }
    @Override public final UDLValue setVariableValue(int index, String varvalue) { array.set(index, new UDLVariableValue(varvalue)); return this; }
    
    @Override public final UDLValue add(UDLValue value) { array.add(value); return this; }
    @Override public final UDLValue addNull() { array.add(NULL); return this; }
    @Override public final UDLValue addInt(int value) { array.add(new UDLInteger(value)); return this; }
    @Override public final UDLValue addLong(long value) { array.add(new UDLInteger(value)); return this; }
    @Override public final UDLValue addFloat(float value) { array.add(new UDLFloat(value)); return this; }
    @Override public final UDLValue addDouble(double value) { array.add(new UDLFloat(value)); return this; }
    @Override public final UDLValue addBoolean(boolean value) { array.add(value ? TRUE : FALSE); return this; }
    @Override public final UDLValue addString(String value) { array.add(new UDLString(value)); return this; }
    @Override public final UDLValue addList(List<UDLValue> value) { array.add(new UDLArray(value)); return this; }
    @Override public final UDLValue addMap(Map<UDLValue, UDLValue> value) { array.add(new UDLObject(value)); return this; }
    @Override public final UDLValue addIdentifier(String identifier) { array.add(new UDLIdentifier(identifier)); return this; }
    @Override public final UDLValue addVariableValue(String varvalue) { array.add(new UDLVariableValue(varvalue)); return this; }
    
    @Override public final UDLValue add(int index, UDLValue value) { array.add(index, value); return this; }
    @Override public final UDLValue addNull(int index) { array.add(index, NULL); return this; }
    @Override public final UDLValue addInt(int index, int value) { array.add(index, new UDLInteger(value)); return this; }
    @Override public final UDLValue addLong(int index, long value) { array.add(index, new UDLInteger(value)); return this; }
    @Override public final UDLValue addFloat(int index, float value) { array.add(index, new UDLFloat(value)); return this; }
    @Override public final UDLValue addDouble(int index, double value) { array.add(index, new UDLFloat(value)); return this; }
    @Override public final UDLValue addBoolean(int index, boolean value) { array.add(index, value ? TRUE : FALSE); return this; }
    @Override public final UDLValue addString(int index, String value) { array.add(index, new UDLString(value)); return this; }
    @Override public final UDLValue addList(int index, List<UDLValue> value) { array.add(index, new UDLArray(value)); return this; }
    @Override public final UDLValue addMap(int index, Map<UDLValue, UDLValue> value) { array.add(index, new UDLObject(value)); return this; }
    @Override public final UDLValue addIdentifier(int index, String identifier) { array.add(index, new UDLIdentifier(identifier)); return this; }
    @Override public final UDLValue addVariableValue(int index, String varvalue) { array.add(index, new UDLVariableValue(varvalue)); return this; }
    
    @Override public final UDLValue remove(int index) { return array.remove(index); }
    @Override public final boolean contains(UDLValue value) { return array.contains(value); }
    @Override public final boolean contains(int value) { return array.contains(new UDLInteger(value)); }
    @Override public final boolean contains(long value) { return array.contains(new UDLInteger(value)); }
    @Override public final boolean contains(float value) { return array.contains(new UDLFloat(value)); }
    @Override public final boolean contains(double value) { return array.contains(new UDLFloat(value)); }
    @Override public final boolean contains(boolean value) { return array.contains(value ? TRUE : FALSE); }
    @Override public final boolean contains(String value) { return array.contains(new UDLString(value)); }
    @Override public final boolean contains(List<UDLValue> value) { return array.contains(new UDLArray(value)); }
    @Override public final boolean contains(Map<UDLValue, UDLValue> value) { return array.contains(new UDLObject(value)); }
    
    @Override public final int size() { return array.size(); }
    @Override public final int length() { return array.size(); }
    @Override public final boolean isEmpty() { return array.isEmpty(); }
}
