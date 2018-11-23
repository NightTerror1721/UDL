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
import static kp.udl.data.UDLValue.ZERO;

/**
 *
 * @author Asus
 */
public final class UDLFloat extends UDLAbstractData
{
    private final double value;
    
    public UDLFloat(float value) { this.value = value; }
    public UDLFloat(double value) { this.value = value; }
    
    @Override
    public final UDLDataType getDataType() { return UDLDataType.INTEGER; }

    @Override
    public final int getInt() { return (int) value; }

    @Override
    public final long getLong() { return (long) value; }

    @Override
    public final float getFloat() { return (float) value; }

    @Override
    public final double getDouble() { return value; }

    @Override
    public final boolean getBoolean() { return value != 0d; }

    @Override
    public final String getString() { return Double.toString(value); }

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
        return otype == UDLDataType.INTEGER || otype == UDLDataType.FLOAT ? value == o.getFloat() : false;
    }

    @Override
    public final int hashCode() { return Double.hashCode(value); }
}
