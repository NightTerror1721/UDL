/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import kp.udl.autowired.Autowired;
import kp.udl.data.UDLValue;

/**
 *
 * @author Asus
 */
public final class Main
{
    private List<List<String>> testList;
    private int test23;
    
    public static void main(String[] args) throws Throwable
    {
        UDLValue value = UDL.read(new File("test.udl"));
        System.out.println(value);
        
        System.out.println(Arrays.toString(((ParameterizedType) Main.class.getDeclaredField("testList").getGenericType()).getActualTypeArguments()));
        System.out.println(Arrays.toString(Main.class.getDeclaredField("test23").getType().getTypeParameters()));
        System.out.println(int[][].class.getComponentType());
        
        Main m = new Main();
        Main.class.getDeclaredField("test23").set(m, 50);
        Main.class.getDeclaredField("testList").set(m, new ArrayList());
        System.out.println(m.test23);
        System.out.println(m.testList);
        
        
        TestAuto a = new TestAuto();
        a.setIntVar(60);
        a.setArrayInts(new int[] { 35, 50, 15 });
        a.setElems(new HashMap<>());
        a.setTextList(Arrays.asList("ola", "k", "ase"));
        
        UDLValue autoValue = Autowired.extract(a);
        System.out.println(autoValue);
        
        UDL.write(value, new File("output.udl"));
    }
}
