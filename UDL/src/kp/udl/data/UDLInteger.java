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

/**
 *
 * @author Asus
 */
public final class UDLInteger extends UDLAbstractData
{
    private final long value;
    
    public UDLInteger(int value) { this.value = value; }
    public UDLInteger(long value) { this.value = value; }
    
    @Override
    public final UDLDataType getDataType() { return UDLDataType.INTEGER; }

    @Override
    public final int getInt() { return (int) value; }

    @Override
    public final long getLong() { return value; }

    @Override
    public final float getFloat() { return value; }

    @Override
    public final double getDouble() { return value; }

    @Override
    public final boolean getBoolean() { return value != 0L; }

    @Override
    public final String getString() { return Long.toString(value); }

    @Override
    public final List<UDLValue> getList() { return Arrays.asList(this); }

    @Override
    public final Map<UDLValue, UDLValue> getMap()
    {
        HashMap<UDLValue,UDLValue> map = new HashMap<>();
        map.put(ZERO, this);
        return map;
    }

    @Override
    final boolean equals(UDLAbstractData o)
    {
        UDLDataType otype = o.getDataType();
        return otype == UDLDataType.INTEGER ? value == o.getLong()
                : otype == UDLDataType.FLOAT ? value == o.getDouble() : false;
    }

    @Override
    public final int hashCode() { return Long.hashCode(value); }
    
}
