/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input.cmd;

import kp.udl.input.Command;
import kp.udl.input.Element;
import kp.udl.input.ElementPool;
import kp.udl.input.UDLReader;

/**
 *
 * @author Asus
 */
public abstract class UseCommand extends Command
{
    protected abstract void applyUse(String useIdentifier, ElementPool pool);
    
    @Override
    public final void execute(UDLReader base, ElementPool pool, Element[] args)
    {
        applyUse(args[0].getUDLValue().getString(), pool);
    }
}
