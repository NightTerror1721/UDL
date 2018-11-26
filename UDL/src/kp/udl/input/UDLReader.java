/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import kp.udl.data.UDLArray;
import kp.udl.data.UDLFloat;
import kp.udl.data.UDLInteger;
import kp.udl.data.UDLObject;
import kp.udl.data.UDLString;
import kp.udl.data.UDLValue;
import kp.udl.exception.UDLParseException;
import kp.udl.input.cmd.ArrayCommands;
import kp.udl.input.cmd.DefineConstantCommand;
import kp.udl.input.cmd.EchoCommand;
import kp.udl.input.cmd.ImportCommand;
import kp.udl.input.cmd.SetVarCommand;
import kp.udl.input.cmd.TemplateCommand;

/**
 *
 * @author Asus
 */
public class UDLReader
{
    private static final char EOF = Character.MAX_VALUE;
    private static final char EOL = '\n';
    private static final char NAME_VALUE_SEPARATOR = ':';
    private static final char PROPERTY_SEPARATOR = ',';
    private static final char OPEN_OBJECT = '{';
    private static final char CLOSE_OBJECT = '}';
    private static final char STRING_DELIMITER_A = '\'';
    private static final char STRING_DELIMITER_B = '\"';
    private static final char OPEN_ARRAY = '[';
    private static final char CLOSE_ARRAY = ']';
    private static final char VAR_ID = '$';
    
    private final Reader source;
    private final char[] buffer;
    private int index;
    private int max;
    private int line;
    private char lastChar = EOF;
    
    private final ElementPool vars;
    private final ElementPool.Variable defaultReturnVar = ElementPool.defaultReturnVariable();
    private final ElementPool.Variable selfVar = ElementPool.selfVariable();
    
    public UDLReader(Reader reader, int bufferLen, ElementPool vars)
    {
        if(reader == null)
            throw new NullPointerException();
        if(vars == null)
            throw new NullPointerException();
        if(bufferLen < 1)
            throw new IllegalArgumentException("bufferLen cannot be less than 1");
        this.source = reader;
        this.buffer = new char[bufferLen];
        this.max = index = 0;
        this.vars = vars;
    }
    public UDLReader(Reader reader, int bufferLen) { this(reader, bufferLen, new ElementPool()); }
    public UDLReader(InputStream is, int bufferLen, ElementPool vars) { this(new InputStreamReader(is), bufferLen, vars); }
    public UDLReader(InputStream is, int bufferLen) { this(new InputStreamReader(is), bufferLen, new ElementPool()); }
    
    private char read() throws IOException
    {
        for(;;)
        {
            if(index >= max)
            {
                max = source.read(buffer, 0, buffer.length);
                if(max <= 0)
                    return lastChar = EOF;
                index = 0;
            }
            char c = buffer[index++];
            switch(c)
            {
                case '\r': break;
                case EOL:
                    line++;
                default: return lastChar = c;
            }
        }
    }
    
    private char seek(boolean checkLastChar, char character) throws IOException, UDLParseException
    {
        int currentLine = line;
        if(checkLastChar && lastChar == character)
            return lastChar;
        for(char c = read();; c = read())
        {
            if(c == character)
                return c;
            if(c == EOF)
                throw new UDLParseException("Unexpected End of File", currentLine);
        }
    }
    
    private char seek(boolean checkLastChar, char character0, char character1) throws IOException, UDLParseException
    {
        int currentLine = line;
        if(checkLastChar && (lastChar == character0 || lastChar == character1))
            return lastChar;
        for(char c = read();; c = read())
        {
            if(c == character0 || c == character1)
                return c;
            if(c == EOF)
                throw new UDLParseException("Unexpected End of File", currentLine);
        }
    }
    
    private char seekSpaceOrEnd(boolean checkLastChar, boolean validEof) throws IOException, UDLParseException
    {
        int currentLine = line;
        for(char c = checkLastChar ? lastChar : read();; c = read())
        {
            switch(c)
            {
                case ' ':
                case '\t':
                case EOL:
                    return ' ';
                case PROPERTY_SEPARATOR:
                    return EOL;
                case EOF:
                    if(validEof)
                        return EOL;
                    throw new UDLParseException("Unexpected End of File", currentLine);
            }
        }
    }
    
    private char readIgnoreSpaces() throws IOException
    {
        for(;;)
        {
            char c = read();
            switch(c)
            {
                case EOL:
                case '\t':
                case '\r':
                case ' ':
                    continue;
                case EOF:
                    return EOF;
            }
            return c;
        }
    }
    
    private char readIgnoreSpacesNotEol() throws IOException
    {
        for(;;)
        {
            char c = read();
            switch(c)
            {
                case '\t':
                case '\r':
                case ' ':
                    continue;
                case EOF:
                    return EOF;
                case EOL:
                    return EOL;
            }
            return c;
        }
    }
    
    private String readStringLiteral(char endChar) throws IOException, UDLParseException
    {
        int currentLine = line;
        StringBuilder sb = new StringBuilder(16);
        for(char c = read(); c != EOF; c = read())
        {
            if(c == endChar)
                return sb.toString();
            if(c == '\\')
            {
                c = read();
                switch(c)
                {
                    case EOF: throw new UDLParseException("Unexpected End of File", currentLine);
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case 'r': sb.append('\r'); break;
                    case '0': sb.append('\u0000'); break;
                    case STRING_DELIMITER_A: sb.append(STRING_DELIMITER_A); break;
                    case STRING_DELIMITER_B: sb.append(STRING_DELIMITER_B); break;
                    case '\\': sb.append('\\'); break;
                    default: sb.append(c); break;
                }
            }
            else sb.append(c);
        }
        throw new UDLParseException("Unexpected End of File", currentLine);
    }
    
    private String readVariableName() throws IOException
    {
        int currentLine = line;
        StringBuilder sb = new StringBuilder(8);
        for(char c = read();; c = read())
        {
            switch(c)
            {
                case ' ':
                case '\t':
                case EOF:
                case EOL:
                    return sb.toString();
                case NAME_VALUE_SEPARATOR:
                    return sb.toString();
                case PROPERTY_SEPARATOR:
                    return sb.toString();
                case STRING_DELIMITER_A:
                case STRING_DELIMITER_B:
                case OPEN_OBJECT:
                case CLOSE_OBJECT:
                case OPEN_ARRAY:
                case CLOSE_ARRAY:
                case VAR_ID:
                    throw new UDLParseException("Invalid varname Character: " + c, currentLine);
                default: sb.append(c); break;
            }
        }
    }
    
    private NormalValue getVariableValue() throws IOException
    {
        String name = readVariableName();
        Element el = decodeValue(name);
        if(el.isVariable())
            return new NormalValue(el.getVariable().getValue());
        if(el.isValue())
            return new NormalValue(vars.getVariableValue(el.getUDLValue().toString()));
        throw new UDLParseException("Unexpected command in variable getter: " + name, line);
    }
    
    private Element readPropertyName(boolean useLastChar, boolean first) throws IOException, UDLParseException
    {
        int currentLine = line;
        char c = useLastChar ? lastChar : readIgnoreSpaces();
        switch(c)
        {
            case EOF:
                if(first)
                    return null;
                throw new UDLParseException("Unexpected End of File", currentLine);
            case EOL:
                throw new IllegalStateException();
                //throw new UDLParseException("Unexpected End of Line", currentLine);
            case STRING_DELIMITER_A:
            case STRING_DELIMITER_B:
                return new NormalValue(new UDLString(readStringLiteral(c)));
            case VAR_ID:
                return getVariableValue();
            case CLOSE_OBJECT:
                if(first)
                    return null;
            case NAME_VALUE_SEPARATOR:
            case CLOSE_ARRAY:
            case PROPERTY_SEPARATOR:
                throw new UDLParseException("Invalid name Character: " + c, currentLine);
            case OPEN_OBJECT: return readObject(CLOSE_OBJECT, false);
            case OPEN_ARRAY: return readArray();
            default: {
                StringBuilder sb = new StringBuilder(16);
                sb.append(c);
                for(c = read();; c = read())
                {
                    switch(c)
                    {
                        case ' ':
                        case '\t':
                        case EOF:
                        case EOL:
                        case NAME_VALUE_SEPARATOR:
                            //seek(NAME_VALUE_SEPARATOR);
                            //return new UDLString(sb.toString());
                            return decodeValue(sb.toString());
                        case OPEN_OBJECT:
                        case CLOSE_OBJECT:
                        case OPEN_ARRAY:
                        case CLOSE_ARRAY:
                        case PROPERTY_SEPARATOR:
                        case STRING_DELIMITER_A:
                        case STRING_DELIMITER_B:
                        case VAR_ID:
                            throw new UDLParseException("Invalid name Character: " + c, currentLine);
                        default: sb.append(c);
                    }
                }
            }
        }
    }
    
    private Element readPropertyValue(boolean first, boolean isObject) throws IOException, UDLParseException
    {
        int currentLine = line;
        char c = readIgnoreSpaces();
        switch(c)
        {
            case OPEN_OBJECT: return readObject(CLOSE_OBJECT, false);
            case OPEN_ARRAY: return readArray();
            case STRING_DELIMITER_A: return new NormalValue(new UDLString(readStringLiteral(STRING_DELIMITER_A)));
            case STRING_DELIMITER_B: return new NormalValue(new UDLString(readStringLiteral(STRING_DELIMITER_B)));
            case VAR_ID: return getVariableValue();
            case CLOSE_ARRAY:
                if(first && !isObject)
                    return null;
            case CLOSE_OBJECT:
                if(first && isObject)
                    return null;
            case NAME_VALUE_SEPARATOR:
            case PROPERTY_SEPARATOR:
                throw new UDLParseException("Invalid name Character: " + c, currentLine);
        }
        
        StringBuilder sb = new StringBuilder(16);
        sb.append(c);
        for(c = read();; c = read())
        {
            switch(c)
            {
                case ' ':
                case '\t':
                case EOL:
                case EOF:
                case PROPERTY_SEPARATOR:
                    return decodeValue(sb.toString());
                case CLOSE_ARRAY:
                    if(!isObject)
                        return decodeValue(sb.toString());
                    else throw new UDLParseException("Invalid name Character: " + c, currentLine);
                case CLOSE_OBJECT:
                    if(isObject)
                        return decodeValue(sb.toString());
                    else throw new UDLParseException("Invalid name Character: " + c, currentLine);
                case OPEN_OBJECT:
                case OPEN_ARRAY:
                case STRING_DELIMITER_A:
                case STRING_DELIMITER_B:
                case NAME_VALUE_SEPARATOR:
                    throw new UDLParseException("Invalid name Character: " + c, currentLine);
                default: sb.append(c); break;
            }
        }
    }
    
    private Element readArgument(boolean first) throws IOException, UDLParseException
    {
        int currentLine = line;
        char c = readIgnoreSpaces();
        switch(c)
        {
            case EOF:
            case EOL:
                return null;
            case OPEN_OBJECT: return readObject(CLOSE_OBJECT, false);
            case OPEN_ARRAY: return readArray();
            case STRING_DELIMITER_A: return new NormalValue(new UDLString(readStringLiteral(STRING_DELIMITER_A)));
            case STRING_DELIMITER_B: return new NormalValue(new UDLString(readStringLiteral(STRING_DELIMITER_B)));
            case VAR_ID: return getVariableValue();
            case PROPERTY_SEPARATOR:
                if(first)
                    return null;
            case CLOSE_ARRAY:
            case CLOSE_OBJECT:
            case NAME_VALUE_SEPARATOR:
                throw new UDLParseException("Invalid name Character: " + c, currentLine);
        }
        
        StringBuilder sb = new StringBuilder(16);
        sb.append(c);
        for(c = read();; c = read())
        {
            switch(c)
            {
                case ' ':
                case '\t':
                case EOL:
                case EOF:
                case PROPERTY_SEPARATOR:
                    return decodeValue(sb.toString());
                case CLOSE_ARRAY:
                case CLOSE_OBJECT:
                case OPEN_OBJECT:
                case OPEN_ARRAY:
                case STRING_DELIMITER_A:
                case STRING_DELIMITER_B:
                case NAME_VALUE_SEPARATOR:
                    throw new UDLParseException("Invalid name Character: " + c, currentLine);
                default: sb.append(c); break;
            }
        }
    }
    
    
    private Element decodeValue(String text)
    {
        switch(text)
        {
            case "null": return E_NULL;
            case "true": return E_TRUE;
            case "false": return E_FALSE;
            case "-1": return E_MINUSONE;
            case "0": return E_ZERO;
            case "1": return E_ONE;
            case ElementPool.DEFAULT_RET_VAR_NAME: return defaultReturnVar;
            case ElementPool.SELF_VARIABLE: return selfVar;
            case "set": return C_SET;
            case "define": return C_DEF;
            case "array_set": return C_ARRAY_SET;
            case "array_get": return C_ARRAY_GET;
            case "array_remove": return C_ARRAY_REMOVE;
            case "array_add": return C_ARRAY_ADD;
            case "import": return C_IMPORT;
            case "template": return C_TEMPLATE;
            case "echo": return C_ECHO;
        }
        
        try { return new NormalValue(new UDLInteger(Long.decode(text))); } catch(NumberFormatException ex) {}
        try { return new NormalValue(new UDLFloat(Double.parseDouble(text))); } catch(NumberFormatException ex) {}
        
        if(vars.exists(text))
            return vars.getElement(text);
        
        return new NormalValue(new UDLString(text));
    }
    
    private NormalValue readArray() throws IOException, UDLParseException
    {
        boolean first = true;
        ArrayList<UDLValue> array = new ArrayList<>(8);
        UDLArray self_array = new UDLArray(array);
        for(;;)
        {
            selfVar.setValue(self_array);
            Element value = readPropertyValue(first, false);
            if(value == null)
            {
                if(first)
                    return new NormalValue(new UDLArray());
                throw new IllegalStateException();
            }
            if(!value.isValue())
                throw new UDLParseException("Expected valid value. Not Variable or Command.", line);
            if(first)
                first = false;
            array.add(value.getUDLValue());
            char end = seek(true, PROPERTY_SEPARATOR, CLOSE_ARRAY);
            if(end == CLOSE_ARRAY)
                return new NormalValue(self_array);
        }
    }
    
    private NormalValue readObject(char endChar, boolean useLastChar) throws IOException, UDLParseException
    {
        HashMap<UDLValue, UDLValue> object = new HashMap<>();
        UDLObject self_object = new UDLObject(object);
        int count = 0;
        for(;;)
        {
            selfVar.setValue(self_object);
            boolean first = count++ == 0;
            Element name = readPropertyName(first && useLastChar, first);
            if(name == null)
            {
                if(first)
                    return new NormalValue(self_object);
                throw new IllegalStateException();
            }
            if(name.isCommand())
                executeCommand(name.getCommand(), endChar == EOF);
            else
            {
                if(!name.isValue())
                    throw new UDLParseException("Expected valid value. Not Variable or Command.", line);
                seek(true, NAME_VALUE_SEPARATOR);
                Element value = readPropertyValue(false, true);
                if(!name.isValue())
                    throw new UDLParseException("Expected valid value. Not Variable or Command.", line);
                object.put(name.getUDLValue(), value.getUDLValue());
            }
            char end = seek(true, PROPERTY_SEPARATOR, endChar);
            if(end == endChar)
            {
                read();
                return new NormalValue(self_object);
            }
        }
    }
    
    private void executeCommand(Command cmd, boolean validEof) throws UDLParseException, IOException
    {
        boolean first = true;
        LinkedList<Element> args = new LinkedList<>();
        for(;;)
        {
            Element arg = readArgument(first);
            if(arg == null)
                break;
            args.add(arg);
            if(first)
                first = false;
            char cres = seekSpaceOrEnd(true, validEof);
            if(cres == EOL)
                break;
        }
        cmd.execute(this, vars, args.toArray(new Element[args.size()]));
    }
    
    public final UDLValue readObject() throws IOException, UDLParseException
    {
        char first = readIgnoreSpaces();
        return first == OPEN_OBJECT
                ? readObject(CLOSE_OBJECT, false).value
                : readObject(EOF, true).value;
    }
    
    public final ElementPool.Variable getDefaultReturnVar() { return defaultReturnVar; }
    public final ElementPool.Variable getSelfVar() { return selfVar; }
    
    private static final class NormalValue implements Element
    {
        private final UDLValue value;
        
        public NormalValue(UDLValue value)
        {
            this.value = value == null ? UDLValue.NULL : value;
        }
        
        @Override
        public final boolean isValue() { return true; }
        
        @Override
        public final UDLValue getUDLValue() { return value; }
    }
    
    private static final Element E_NULL = new NormalValue(UDLValue.NULL);
    private static final Element E_TRUE = new NormalValue(UDLValue.TRUE);
    private static final Element E_FALSE = new NormalValue(UDLValue.FALSE);
    private static final Element E_MINUSONE = new NormalValue(UDLValue.MINUSONE);
    private static final Element E_ZERO = new NormalValue(UDLValue.ZERO);
    private static final Element E_ONE = new NormalValue(UDLValue.ONE);
    
    private static final Element C_SET = new SetVarCommand();
    private static final Element C_DEF = new DefineConstantCommand();
    private static final Element C_ARRAY_SET = new ArrayCommands.ArraySetCommand();
    private static final Element C_ARRAY_GET = new ArrayCommands.ArrayGetCommand();
    private static final Element C_ARRAY_REMOVE = new ArrayCommands.ArrayRemoveCommand();
    private static final Element C_ARRAY_ADD = new ArrayCommands.ArrayAddCommand();
    private static final Element C_IMPORT = new ImportCommand();
    private static final Element C_TEMPLATE = new TemplateCommand();
    private static final Element C_ECHO = new EchoCommand();
}
