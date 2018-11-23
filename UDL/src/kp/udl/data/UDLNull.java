/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public final class UDLNull extends UDLAbstractData
{
    public static final UDLNull INSTANCE = new UDLNull();
    
    private UDLNull() {}

    @Override
    public final UDLDataType getDataType() { return UDLDataType.NULL; }

    @Override
    public final int getInt() { return 0; }

    @Override
    public final long getLong() { return 0L; }

    @Override
    public final float getFloat() { return 0f; }

    @Override
    public final double getDouble() { return 0d; }

    @Override
    public final boolean getBoolean() { return false; }

    @Override
    public final String getString() { return "null"; }

    @Override
    public final List<UDLValue> getList() { return Collections.emptyList(); }

    @Override
    public final Map<UDLValue, UDLValue> getMap() { return Collections.emptyMap(); }

    @Override
    final boolean equals(UDLAbstractData o) { return o == this; }

    @Override
    public final int hashCode() { return 0; }
}
