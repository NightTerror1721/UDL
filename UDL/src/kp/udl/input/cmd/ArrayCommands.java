/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input.cmd;

import kp.udl.data.UDLValue;
import kp.udl.exception.UDLException;
import kp.udl.input.Command;
import kp.udl.input.Element;
import kp.udl.input.ElementPool;
import kp.udl.input.ElementPool.Variable;
import kp.udl.input.UDLReader;

/**
 *
 * @author Asus
 */
public final class ArrayCommands
{
    private ArrayCommands() {}
    
    private static UDLValue array(Element element)
    {
         if(element.isValue())
            return element.getUDLValue();
        else if(element.isVariable())
            return element.getVariable().getValue();
        throw new UDLException("Expected valid Variable or String to set variable value");
    }
    
    static Variable retVar(UDLReader base, Element[] args, int index)
    {
        if(args.length <= index)
            return base.getDefaultReturnVar();
        Element el = args[index];
        if(!el.isVariable())
            throw new UDLException("Expected valid Variable or String to set variable value");
        return el.getVariable();
    }
    
    public static final class ArraySetCommand extends Command
    {
        @Override
        public final void execute(UDLReader base, ElementPool pool, Element[] args)
        {
            UDLValue index = args[1].getUDLValue();
            UDLValue value = args.length < 3 ? UDLValue.NULL : args[2].getUDLValue();
            array(args[0]).set(index.getInt(), value);
        }
    }
    
    public static final class ArrayGetCommand extends Command
    {
        @Override
        public final void execute(UDLReader base, ElementPool pool, Element[] args)
        {
            UDLValue index = args[1].getUDLValue();
            UDLValue value = array(args[0]).get(index.getInt());
            retVar(base, args, 2).setValue(value);
        }
    }
    
    public static final class ArrayRemoveCommand extends Command
    {
        @Override
        public final void execute(UDLReader base, ElementPool pool, Element[] args)
        {
            UDLValue index = args[1].getUDLValue();
            array(args[0]).remove(index.getInt());
        }
    }
    
    public static final class ArrayAddCommand extends Command
    {
        @Override
        public final void execute(UDLReader base, ElementPool pool, Element[] args)
        {
            UDLValue value = args.length < 2 ? UDLValue.NULL : args[1].getUDLValue();
            array(args[0]).add(value);
        }
    }
}
