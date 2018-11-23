/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static kp.udl.data.UDLValue.ZERO;

/**
 *
 * @author Asus
 */
public final class UDLCommand extends UDLValue
{
    private final String commandName;
    private final UDLValue[] args;
    
    public UDLCommand(String cmdName, UDLValue... args)
    {
        this.commandName = Objects.requireNonNull(cmdName);
        this.args = Objects.requireNonNull(args);
    }
    
    public final String getCommandName() { return commandName; }
    public final Iterable<UDLValue> getArgsIterable()
    {
        return () -> new Iterator<UDLValue>()
        {
            private int it = 0;
            @Override public final boolean hasNext() { return it < args.length; }
            @Override public final UDLValue next() { return args[it++]; }
        };
    }
    
    @Override
    public UDLDataType getDataType() { return null; }

    @Override
    public UDLElementType getElementType() { return UDLElementType.COMMAND; }

    @Override
    public int getInt() { return args.length; }

    @Override
    public long getLong() { return args.length; }

    @Override
    public float getFloat() { return args.length; }

    @Override
    public double getDouble() { return args.length; }

    @Override
    public boolean getBoolean() { return args.length > 0; }

    @Override
    public String getString() { return commandName; }

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
        if(o.getElementType() == UDLElementType.COMMAND)
        {
            UDLCommand c = (UDLCommand) o;
            return commandName.equals(c.commandName) && Arrays.equals(args, c.args);
        }
        return false;
    }

    @Override
    public final int hashCode() { return Objects.hash(commandName, Arrays.hashCode(args)); }
    
}
