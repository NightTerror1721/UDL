/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;
import kp.udl.autowired.SerializerManager;
import kp.udl.data.UDLValue;
import kp.udl.exception.UDLException;
import kp.udl.input.ElementPool;

/**
 *
 * @author Asus
 */
public final class UDLManager
{
    private final SerializerManager serials;
    private final ElementPool elems;
    
    public UDLManager(SerializerManager serializers, ElementPool elementPool)
    {
        this.serials = Objects.requireNonNull(serializers);
        this.elems = Objects.requireNonNull(elementPool);
    }
    public UDLManager(SerializerManager serializers) { this(serializers, new ElementPool()); }
    public UDLManager(ElementPool elementPool) { this(new SerializerManager(), elementPool); }
    public UDLManager() { this(new SerializerManager(), new ElementPool()); }
    
    public final SerializerManager getSerializerManager() { return serials; }
    public final ElementPool getElementPool() { return elems; }
    
    
    /* Read ops */
    public final UDLValue read(Reader reader) throws UDLException, IOException { return UDL.read(reader, elems); }
    public final UDLValue read(InputStream is) throws UDLException, IOException { return UDL.read(is, elems); }
    public final UDLValue read(File file) throws UDLException, IOException { return UDL.read(file, elems); }
    public final UDLValue decode(String text) throws UDLException, IOException { return UDL.decode(text, elems); }
    
    /* Write ops */
    public final void write(Writer writer, UDLValue value) throws UDLException, IOException { UDL.write(value, writer); }
    public final void write(OutputStream os, UDLValue value) throws UDLException, IOException { UDL.write(value, os); }
    public final void write(File file, UDLValue value) throws UDLException, IOException { UDL.write(value, file); }
    public final String encode(UDLValue value, boolean wrapped) throws UDLException, IOException { return UDL.encode(value, wrapped); }
    
    /* Autowired ops */
    public final UDLValue extract(Object obj) { return serials.extract(obj); }
    public final <T> T inject(Class<T> jclass, UDLValue value) { return serials.inject(jclass, value); }
}
