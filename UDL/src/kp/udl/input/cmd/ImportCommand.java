/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import kp.udl.UDL;
import kp.udl.data.UDLValue;
import kp.udl.exception.UDLException;
import kp.udl.input.Command;
import kp.udl.input.Element;
import kp.udl.input.ElementPool;
import kp.udl.input.UDLReader;

/**
 *
 * @author Asus
 */
public final class ImportCommand extends Command
{
    static UDLValue toObj(UDLReader base, Element[] args, int index)
    {
        if(args.length <= index)
            return base.getSelfVar().getValue();
        Element el = args[index];
        if(el.isValue())
            return el.getUDLValue();
        if(el.isVariable())
            return el.getVariable().getValue();
        throw new UDLException("Expected valid Variable or Value to import data into");
    }
    
    @Override
    public final void execute(UDLReader base, ElementPool pool, Element[] args)
    {
        String path = args[0].getUDLValue().toString();
        UDLValue objTo = toObj(base, args, 1);
        
        File file = new File(path);
        if(!file.exists() || !file.isFile())
            throw new UDLException("File " + file.getAbsolutePath() + " not found");
       try(FileInputStream fis = new FileInputStream(file))
       {
           UDLReader reader = new UDLReader(new InputStreamReader(fis), UDL.DEFAULT_BUFFER_SIZE, pool);
           UDLValue obj = reader.readObject();
           for(Map.Entry<UDLValue, UDLValue> e : obj.getMap().entrySet())
               objTo.set(e.getKey(), e.getValue());
       }
       catch(IOException ex) { throw new UDLException(ex); }
    }
}
