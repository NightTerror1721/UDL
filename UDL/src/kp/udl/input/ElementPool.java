/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import kp.udl.data.UDLValue;
import kp.udl.exception.UDLException;

/**
 *
 * @author Asus
 */
public class ElementPool
{
    private final HashMap<String, Element> vars;
    private final ElementPool parent;
    
    public ElementPool(ElementPool parent)
    {
        this.vars = new HashMap<>();
        this.parent = parent;
    }
    public ElementPool() { this(null); }
    
    public final ElementPool getParent() { return parent; }
    
    public final boolean exists(String name)
    {
        if(vars.containsKey(name))
            return true;
        return parent == null ? false : parent.exists(name);
    }
    
    public final boolean existsInCurrentPool(String name)
    {
        return vars.containsKey(name);
    }
    
    public final Element getElement(String name)
    {
        Element e;
        return (e = vars.getOrDefault(name, null)) != null ? e :
                parent == null ? null : parent.getElement(name);
    }
    
    public final void putValue(String name, UDLValue value)
    {
        vars.put(name, new Constant(value));
    }
    
    public final void putVariable(String name, UDLValue defaultValue)
    {
        vars.put(name, new Variable(name, defaultValue));
    }
    public final void putVariable(String name) { putVariable(name, UDLValue.NULL); }
    
    public final void putCommand(String name, Command command)
    {
        vars.put(name, Objects.requireNonNull(command));
    }
    
    public final void putPool(ElementPool pool)
    {
        if(pool.parent != null)
            putPool(pool.parent);
        for(Map.Entry<String, Element> e : pool.vars.entrySet())
            vars.put(e.getKey(), e.getValue());
    }
    
    
    
    /* Private utilities */
    
    final void setVariableValue(String name, UDLValue value)
    {
        Element var = vars.getOrDefault(name, null);
        if(var == null)
        {
            var = new Variable(name, value);
            vars.put(name, var);
        }
        else
        {
            if(!var.isVariable())
                throw new UDLException("Element \"" + name + "\" is not a valid variable");
            var.getVariable().setValue(value);
        }
    }
    
    final UDLValue getVariableValue(String name)
    {
        Element var = getElement(name);
        if(var == null)
            return UDLValue.NULL;
        if(!var.isVariable())
            throw new UDLException("Element \"" + name + "\" is not a valid variable");
        return var.getVariable().getValue();
    }
    
    /*final void createConstant(String name, UDLValue value)
    {
        if(vars.containsKey(name))
            throw new UDLException("Cannot redefine variable: " + name + " in constant type");
        vars.put(name, new Constant(value));
    }*/
    
    static final String DEFAULT_RET_VAR_NAME = "__ret";
    static final Variable defaultReturnVariable()
    {
        return new Variable(DEFAULT_RET_VAR_NAME, UDLValue.NULL);
    }
    
    static final String SELF_VARIABLE = "__self";
    static final Variable selfVariable()
    {
        return new Variable(SELF_VARIABLE, UDLValue.NULL);
    }
    
    
    public static final class Variable implements Element
    {
        private final String name;
        private UDLValue value;
        
        private Variable(String name, UDLValue value)
        {
            this.name = name;
            this.value = value == null ? UDLValue.NULL : value;
        }
        
        public final String getName() { return name; }
        
        public final UDLValue getValue() { return value; }
        
        public final void setValue(UDLValue value) throws UDLException
        {
            this.value = value == null ? UDLValue.NULL : value;
        }
        
        @Override
        public final boolean isVariable() { return true; }
        
        @Override
        public final Variable getVariable() { return this; }
    }
    
    private static final class Constant implements Element
    {
        private final UDLValue value;
        
        public Constant(UDLValue value)
        {
            this.value = value == null ? UDLValue.NULL : value;
        }
        
        @Override
        public final boolean isValue() { return true; }
        
        @Override
        public final UDLValue getUDLValue() { return value; }
    }
}
