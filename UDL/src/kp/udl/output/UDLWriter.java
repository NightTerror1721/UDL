/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kp.udl.data.UDLCommand;
import kp.udl.data.UDLElementType;
import kp.udl.data.UDLValue;
import kp.udl.exception.UDLException;

/**
 *
 * @author Asus
 */
public final class UDLWriter implements AutoCloseable
{
    private final BufferedWriter output;
    
    public UDLWriter(OutputStream output, int bufferLen)
    {
        if(bufferLen < 1)
            throw new IllegalArgumentException("bufferLen cannot be less than 1");
        this.output = new BufferedWriter(new OutputStreamWriter(output));
    }
    
    public UDLWriter(Writer writer, int bufferLen)
    {
        if(bufferLen < 1)
            throw new IllegalArgumentException("bufferLen cannot be less than 1");
        output = new BufferedWriter(Objects.requireNonNull(writer));
    }
    
    private void writeValue(String identation, UDLValue value, boolean isPropertyName) throws IOException
    {
        switch(value.getElementType())
        {
            case DATA: switch(value.getDataType())
            {
                case NULL:
                case INTEGER:
                case FLOAT:
                case BOOLEAN:
                    output.write(value.getString());
                    break;
                case STRING: output.write("\"" + value.getString()+ "\""); break;
                case ARRAY:
                    writeArray(identation, value);
                    break;
                case OBJECT: writeObject(identation, value, true); break;
                default:
                    throw new IllegalStateException();
            } break;
            case IDENTIFIER:
                output.write(value.getString());
                break;
            case VARIABLE_VALUE:
                output.write('$' + value.getString());
                break;
            case COMMAND:
                if(isPropertyName)
                {
                    UDLCommand cmd = (UDLCommand) value;
                    output.write(cmd.getCommandName());
                    for(UDLValue arg : cmd.getArgsIterable())
                    {
                        output.write(" ");
                        writeValue(identation, arg, false);
                    }
                }
                else throw new UDLException("Cannot put command in value position. Only in key");
                break;
            default: throw new IllegalStateException();
        }
        
    }
    
    private void writeArray(String identation, UDLValue array) throws IOException
    {
        output.write("[");
        List<UDLValue> list = array.getList();
        int len = list.size(), count = 0;
        for(UDLValue value : list)
        {
            writeValue(identation, value, false);
            if(++count < len)
                output.write(", ");
        }
        output.write("]");
    }
    
    private void writeObject(String identation, UDLValue object, boolean wrapped) throws IOException
    {
        Map<UDLValue, UDLValue> map = object.getMap();
        String newIdentation;
        if(wrapped)
        {
            if(map.isEmpty())
            {
                output.write("{}");
                return;
            }
            newIdentation = identation + "    ";
            output.write("{\n");
        }
        else
        {
            if(map.isEmpty())
                return;
            newIdentation = identation;
        }
        int len = map.size(), count = 0;
        for(Map.Entry<UDLValue, UDLValue> e : map.entrySet())
        {
            UDLValue key = e.getKey();
            output.write(newIdentation);
            writeValue(newIdentation, key, true);
            if(key.getElementType() != UDLElementType.COMMAND)
            {
                output.write(": ");
                writeValue(newIdentation, e.getValue(), false);
            }
            output.append(++count < len ? ",\n" : "\n");
        }
        if(wrapped)
            output.write(identation + "}");
    }
    
    public final void writeObject(UDLValue object, boolean wrapped) throws IOException
    {
        writeObject("", object, wrapped);
        flush();
    }
    
    public final void flush() throws IOException
    {
        output.flush();
    }

    @Override
    public final void close() throws IOException
    {
        output.close();
    }
}
