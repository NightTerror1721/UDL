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
public class UDLIdentifier extends UDLValue
{
    private final String identifier;
    
    public UDLIdentifier(String identifier)
    {
        if(identifier == null)
            throw new NullPointerException();
        this.identifier = identifier;
    }
    
    @Override
    public UDLDataType getDataType() { return UDLDataType.STRING; }
    
    @Override
    public final UDLElementType getElementType() { return UDLElementType.IDENTIFIER; }

    @Override
    public final int getInt() { return Integer.parseInt(identifier); }

    @Override
    public final long getLong() { return Long.decode(identifier); }

    @Override
    public final float getFloat() { return Float.parseFloat(identifier); }

    @Override
    public final double getDouble() { return Double.parseDouble(identifier); }

    @Override
    public final boolean getBoolean() { return identifier.equals("true"); }

    @Override
    public final String getString() { return identifier; }

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
    public final boolean equals(UDLValue o)
    {
        return o.getElementType() == UDLElementType.IDENTIFIER &&
                identifier.equals(((UDLIdentifier) o).identifier);
    }

    @Override
    public final int hashCode() { return identifier.hashCode(); }
    
}
