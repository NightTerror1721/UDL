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
public class UDLVariableValue extends UDLValue
{
    private final String varname;
    
    public UDLVariableValue(String varname)
    {
        if(varname == null)
            throw new NullPointerException();
        this.varname = varname;
    }
    
    @Override
    public UDLDataType getDataType() { return UDLDataType.STRING; }
    
    @Override
    public final UDLElementType getElementType() { return UDLElementType.VARIABLE_VALUE; }

    @Override
    public final int getInt() { return Integer.parseInt(varname); }

    @Override
    public final long getLong() { return Long.decode(varname); }

    @Override
    public final float getFloat() { return Float.parseFloat(varname); }

    @Override
    public final double getDouble() { return Double.parseDouble(varname); }

    @Override
    public final boolean getBoolean() { return varname.equals("true"); }

    @Override
    public final String getString() { return varname; }

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
        return o.getElementType() == UDLElementType.VARIABLE_VALUE &&
                varname.equals(((UDLVariableValue) o).varname);
    }

    @Override
    public final int hashCode() { return varname.hashCode(); }
}
