/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.udl;

import java.util.List;
import java.util.Map;
import kp.udl.autowired.Property;

/**
 *
 * @author Asus
 */
public class TestAuto
{
    @Property
    private int intVar;
    
    @Property
    private List<String> textList;
    
    @Property(name = "powers")
    private int[] arrayInts;
    
    @Property
    private Map<String, int[]> elems;
    
    
    

    public int getIntVar() {
        return intVar;
    }

    public void setIntVar(int intVar) {
        this.intVar = intVar;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    public int[] getArrayInts() {
        return arrayInts;
    }

    public void setArrayInts(int[] arrayInts) {
        this.arrayInts = arrayInts;
    }

    public Map<String, int[]> getElems() {
        return elems;
    }

    public void setElems(Map<String, int[]> elems) {
        this.elems = elems;
    }
}
