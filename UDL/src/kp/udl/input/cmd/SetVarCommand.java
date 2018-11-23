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
import kp.udl.input.UDLReader;

/**
 *
 * @author Asus
 */
public final class SetVarCommand extends Command
{
    @Override
    public final void execute(UDLReader base, ElementPool pool, Element[] args)
    {
        UDLValue value = args.length < 2 ? UDLValue.NULL : args[1].getUDLValue();
        if(args[0].isValue())
        {
            String name = args[0].getUDLValue().toString();
            if(!pool.exists(name))
            {
                pool.putVariable(name, value);
            }
            else
            {
                Element var = pool.getElement(name);
                if(!var.isVariable())
                    throw new UDLException("Element \"" + name + "\" is not a valid variable");
                var.getVariable().setValue(value);
            }
        }
        else if(args[0].isVariable())
            args[0].getVariable().setValue(value);
        else throw new UDLException("Expected valid Variable or String to set variable value");
    }
    
}
