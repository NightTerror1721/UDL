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
public final class UDLBoolean extends UDLAbstractData
{
    public static final UDLBoolean TRUE_INSTANCE = new UDLBoolean(true);
    public static final UDLBoolean FALSE_INSTANCE = new UDLBoolean(false);
    
    private final boolean value;
    
    private UDLBoolean(boolean value) { this.value = value; }
    
    @Override
    public final UDLDataType getDataType() { return UDLDataType.BOOLEAN; }

    @Override
    public final int getInt() { return value ? 1 : 0; }

    @Override
    public final long getLong() { return value ? 1 : 0; }

    @Override
    public final float getFloat() { return value ? 1 : 0; }

    @Override
    public final double getDouble() { return value ? 1 : 0; }

    @Override
    public final boolean getBoolean() { return value; }

    @Override
    public final String getString() { return value ? "true" : "false"; }

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
        return o.getDataType() == UDLDataType.BOOLEAN && value == o.getBoolean();
    }

    @Override
    public final int hashCode() { return Boolean.hashCode(value); }
    
}
