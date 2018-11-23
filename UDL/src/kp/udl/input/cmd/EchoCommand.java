/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl.input.cmd;

import kp.udl.input.Command;
import kp.udl.input.Element;
import kp.udl.input.ElementPool;
import kp.udl.input.ElementPool.Variable;
import kp.udl.input.UDLReader;

/**
 *
 * @author Asus
 */
public final class EchoCommand extends Command
{
    private static String str(Element element)
    {
        if(element.isValue())
            return element.getUDLValue().toString();
        if(element.isVariable())
        {
            Variable var = element.getVariable();
            return "<<var " + var.getName() + ": " + var.getValue().toString() + ">>";
        }
        else return "<<command::" + element.getCommand().getClass().getName() + ">>";
    }
    
    @Override
    public final void execute(UDLReader base, ElementPool pool, Element[] args)
    {
        switch(args.length)
        {
            case 0: System.out.println(); break;
            case 1: System.out.println(str(args[0])); break;
            default:
                for(int i=0;i<args.length;i++)
                    if(i + 1 == args.length)
                        System.out.println(str(args[i]));
                    else System.out.print(str(args[i]) + " ");
        }
    }
}
