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
public class UDLParseException extends UDLException
{
    private final int line;
    
    public UDLParseException(String message, int line)
    {
        super(message);
        this.line = line;
    }
    
    public UDLParseException(Throwable cause, int line)
    {
        super(cause);
        this.line = line;
    }
    
    public UDLParseException(String message, Throwable cause, int line)
    {
        super(message, cause);
        this.line = line;
    }
    
    public final int getSourceLine() { return line; }
}
