/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.data;

/**
 *
 * @author Asus
 */
abstract class UDLAbstractData extends UDLValue
{
    @Override
    public final UDLElementType getElementType() { return UDLElementType.DATA; }
    
    @Override
    public final boolean equals(UDLValue o)
    {
        return o.getElementType() == UDLElementType.DATA && equals((UDLAbstractData) o);
    }
    
    abstract boolean equals(UDLAbstractData o);
}
