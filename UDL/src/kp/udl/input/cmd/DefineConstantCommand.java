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
public final class DefineConstantCommand extends Command
{
    @Override
    public final void execute(UDLReader base, ElementPool pool, Element[] args)
    {
        String name = args[0].getUDLValue().toString();
        UDLValue value = args[1].getUDLValue();
        if(pool.exists(name))
            throw new UDLException("Cannot redefine variable: " + name + " in constant type");
        pool.putValue(name, value);
    }
    
}
