/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired.serializers;

import kp.udl.autowired.AutowiredSerializer;
import kp.udl.data.UDLValue;

/**
 *
 * @author Asus
 * @param <E>
 */
public class EnumAutowiredSerializer<E extends Enum<E>> extends AutowiredSerializer<E>
{
    protected final E[] values;
    protected final E invalid;
    protected final boolean byId;
    
    public EnumAutowiredSerializer(Class<E> jclass, E invalidValue, boolean byId)
    {
        super(jclass);
        this.values = jclass.getEnumConstants();
        this.invalid = invalidValue;
        this.byId = byId;
    }
    public EnumAutowiredSerializer(Class<E> jclass, E invalidValue) { this(jclass, invalidValue, true); }
    public EnumAutowiredSerializer(Class<E> jclass) { this(jclass, null, true); }
    
    @Override
    public UDLValue serialize(E value)
    {
        if(value == null)
            value = invalid;
        if(byId)
            return UDLValue.valueOf(value.ordinal());
        return UDLValue.valueOf(value.name());
    }

    @Override
    public E unserialize(UDLValue value)
    {
        try
        {
            if(byId)
            {
                int id = value.getInt();
                return id < 0 || id >= values.length ? invalid : values[id];
            }
            return Enum.valueOf(jclass, value.getString());
        }
        catch(Throwable ex) { return invalid; }
    }
    
}
