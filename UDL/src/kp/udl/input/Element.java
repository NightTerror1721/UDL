/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input;

import kp.udl.data.UDLValue;
import kp.udl.exception.UDLException;
import kp.udl.input.ElementPool.Variable;

/**
 *
 * @author Asus
 */
public interface Element
{
    default boolean isValue() { return false; }
    default boolean isVariable() { return false; }
    default boolean isCommand() { return false; }
    
    default UDLValue getUDLValue() { throw new UDLException("Invalid UDLValue"); }
    default Variable getVariable() { throw new UDLException("Invalid Variable"); }
    default Command getCommand() { throw new UDLException("Invalid Command"); }
}
