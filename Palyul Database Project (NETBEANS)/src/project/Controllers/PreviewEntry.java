/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.Controllers;

/**
 *
 * @author Jan Go
 */
public class PreviewEntry {
    String ornum;
    String amount;
    String event;
    
    PreviewEntry() {
        
    }
    
    public PreviewEntry(String or, String amt, String evt) {
        ornum = or;
        amount = amt;
        event = evt;
    }
    
    public void setOrnum(String or) {
        ornum = or;
    }
    
    public void setAmount(String amt) {
        amount = amt;
    }
    
    public void setEvent (String evt) {
        event = evt;
    }
    
    public String getOrnum() {
        return ornum;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public String getEvent() {
        return event;
    }
}
