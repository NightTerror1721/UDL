/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.autowired;

import java.util.Objects;
import java.util.function.Function;
import kp.udl.data.UDLValue;

/**
 *
 * @author Asus
 */
public abstract class AutowiredSerializer<T>
{
    private final Class<T> jclass;
    
    public AutowiredSerializer(Class<T> jclass)
    {
        this.jclass = Objects.requireNonNull(jclass);
    }
    
    public abstract UDLValue serialize(T value);
    public abstract T unserialize(UDLValue value);
    
    public final Class<T> getJavaClass() { return jclass; }
    
    public static final <T> AutowiredSerializer<T> create(Class<T> jclass, Function<T, UDLValue> extractor, Function<UDLValue, T> injector)
    {
        return new DefaultAutowiredSerializer<>(jclass, extractor, injector);
    }
    
    
    public static class DefaultAutowiredSerializer<T> extends AutowiredSerializer<T>
    {
        private Function<T, UDLValue> extractor;
        private Function<UDLValue, T> injector;
        
        public DefaultAutowiredSerializer(Class<T> jclass, Function<T, UDLValue> extractor, Function<UDLValue, T> injector)
        {
            super(jclass);
            this.extractor = Objects.requireNonNull(extractor);
            this.injector = Objects.requireNonNull(injector);
        }

        @Override
        public UDLValue serialize(T value)
        {
            return extractor.apply(value);
        }

        @Override
        public T unserialize(UDLValue value)
        {
            return injector.apply(value);
        }
    }
}
