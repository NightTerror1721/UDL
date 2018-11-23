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
public final class UDLString extends UDLAbstractData
{
    private final String value;
    
    public UDLString(String value)
    {
        if(value == null)
            throw new NullPointerException();
        this.value = value;
    }
    
    @Override
    public final UDLDataType getDataType() { return UDLDataType.STRING; }

    @Override
    public final int getInt() { return Integer.decode(value); }

    @Override
    public final long getLong() { return Long.decode(value); }

    @Override
    public final float getFloat() { return Float.parseFloat(value); }

    @Override
    public final double getDouble() { return Double.parseDouble(value); }

    @Override
    public final boolean getBoolean() { return value.equals("true"); }

    @Override
    public final String getString() { return value; }

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
        return o.getDataType() == UDLDataType.STRING && value.equals(o.getString());
    }

    @Override
    public final int hashCode() { return value.hashCode(); }
    
}
