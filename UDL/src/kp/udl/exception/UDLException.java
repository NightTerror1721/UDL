/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.exception;

/**
 *
 * @author Asus
 */
public class UDLException extends RuntimeException
{
    public UDLException(String message)
    {
        super(message);
    }
    
    public UDLException(Throwable cause)
    {
        super(cause);
    }
    
    public UDLException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
