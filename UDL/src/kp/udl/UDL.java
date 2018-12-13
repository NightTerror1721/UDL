/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import kp.udl.autowired.Autowired;
import kp.udl.autowired.SerializerManager;
import kp.udl.data.UDLValue;
import kp.udl.exception.UDLException;
import kp.udl.input.ElementPool;
import kp.udl.input.UDLReader;
import kp.udl.output.UDLWriter;

/**
 *
 * @author Asus
 */
public final class UDL
{
    private UDL() {}
    
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    
    public static final UDLValue read(Reader reader, ElementPool parentPool) throws IOException, UDLException
    {
        UDLReader r = new UDLReader(reader, DEFAULT_BUFFER_SIZE, new ElementPool(parentPool));
        return r.readObject();
    }
    public static final UDLValue read(Reader reader) throws IOException, UDLException
    {
        UDLReader r = new UDLReader(reader, DEFAULT_BUFFER_SIZE);
        return r.readObject();
    }
    
    public static final UDLValue read(InputStream is, ElementPool parentPool) throws IOException, UDLException
    {
        UDLReader r = new UDLReader(is, DEFAULT_BUFFER_SIZE, new ElementPool(parentPool));
        return r.readObject();
    }
    public static final UDLValue read(InputStream is) throws IOException, UDLException
    {
        UDLReader r = new UDLReader(is, DEFAULT_BUFFER_SIZE);
        return r.readObject();
    }
    
    public static final UDLValue read(File file, ElementPool parentPool) throws IOException, UDLException
    {
        try(FileInputStream fis = new FileInputStream(file))
        {
            return read(fis, parentPool);
        }
    }
    public static final UDLValue read(File file) throws IOException, UDLException
    {
        try(FileInputStream fis = new FileInputStream(file))
        {
            return read(fis);
        }
    }
    
    public static final UDLValue read(Path file, ElementPool parentPool) throws IOException, UDLException
    {
        try(InputStream is = Files.newInputStream(file))
        {
            return read(is, parentPool);
        }
    }
    public static final UDLValue read(Path file) throws IOException, UDLException
    {
        try(InputStream is = Files.newInputStream(file))
        {
            return read(is);
        }
    }
    
    public static final UDLValue decode(String text, ElementPool parentPool) throws IOException, UDLException
    {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes()))
        {
            return read(bais, parentPool);
        }
    }
    public static final UDLValue decode(String text) throws IOException, UDLException
    {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes()))
        {
            return read(bais);
        }
    }
    
    
    
    public static final void write(UDLValue value, Writer writer, boolean wrapped) throws IOException, UDLException
    {
        UDLWriter w = new UDLWriter(writer, DEFAULT_BUFFER_SIZE);
        w.writeObject(value, wrapped);
    }
    public static final void write(UDLValue value, Writer writer) throws IOException, UDLException { write(value, writer, false); }
    
    public static final void write(UDLValue value, OutputStream os, boolean wrapped) throws IOException, UDLException
    {
        UDLWriter w = new UDLWriter(os, DEFAULT_BUFFER_SIZE);
        w.writeObject(value, wrapped);
    }
    public static final void write(UDLValue value, OutputStream os) throws IOException, UDLException { write(value, os, false); }
    
    public static final void write(UDLValue value, File file, boolean wrapped) throws IOException, UDLException
    {
        try(FileOutputStream fos = new FileOutputStream(file))
        {
            write(value, fos, wrapped);
        }
    }
    public static final void write(UDLValue value, File file) throws IOException, UDLException { write(value, file, false); }
    
    public static final void write(UDLValue value, Path file, boolean wrapped) throws IOException, UDLException
    {
        try(OutputStream os = Files.newOutputStream(file))
        {
            write(value, os, wrapped);
        }
    }
    public static final void write(UDLValue value, Path file) throws IOException, UDLException { write(value, file, false); }
    
    public static final String encode(UDLValue value, boolean wrapped) throws IOException, UDLException
    {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            write(value, baos, wrapped);
            return baos.toString();
        }
    }
    
    
    
    public static final UDLValue extract(Object object, SerializerManager smanager) throws UDLException
    {
        return Autowired.extract(object, smanager, false);
    }
    
    public static final UDLValue extract(Object object) throws UDLException
    {
        return Autowired.extract(object, null, false);
    }
    
    
    public static final <T> T inject(UDLValue value, Class<T> jclass, SerializerManager smanager)
    {
        return Autowired.inject(jclass, value, smanager, false);
    }
    
    public static final <T> T inject(UDLValue value, Class<T> jclass)
    {
        return Autowired.inject(jclass, value, null, false);
    }
    
}
