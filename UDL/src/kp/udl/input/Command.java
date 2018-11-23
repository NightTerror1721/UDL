/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input;

/**
 *
 * @author Asus
 */
public abstract class Command implements Element
{
    public abstract void execute(UDLReader base, ElementPool pool, Element[] args);
    
    @Override
    public final boolean isCommand() { return true; }

    @Override
    public final Command getCommand() { return this; }
    
}
