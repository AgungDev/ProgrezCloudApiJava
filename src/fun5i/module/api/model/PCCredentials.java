/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fun5i.module.api.model;

/**
 *
 * @author fun5i
 */

// update 1.2.0
public class PCCredentials {
    
    private final String d,s,o;
    
    public PCCredentials(String d, String s, String o){
        this.d = d;
        this.s = s;
        this.o = o;
    }
    
    public String getD(){
        return this.d;
    }
    
    public String getS(){
        return this.s;
    }
    
    public String getO(){
        return this.o;
    }
    
}
