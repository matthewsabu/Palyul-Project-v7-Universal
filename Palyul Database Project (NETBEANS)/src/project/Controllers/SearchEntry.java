/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.Controllers;

/**
 *
 * @author Malcolm Co
 */
public class SearchEntry {
    String ornum;
    String name;
    String dtype;
    String checknm;
    String notes;
    String amount;
    String event;
    
    SearchEntry() {
        
    }
    
    public SearchEntry(String orn, String nm, String amnt, String dtyp, String chkn, String evnt, String nts ) {
        ornum = orn;
        name = nm;
        amount = amnt;
        dtype = dtyp;
        checknm = chkn;
        event = evnt;
        notes = nts;
    }
    
    public void setOrnum(String orn) {
        ornum = orn;
    }
    
    public void setName(String nm) {
        name = nm;
    }
    
    public void setAmount (String amnt) {
        amount = amnt;
    }
    
     public void setDtype(String dtyp) {
        dtype = dtyp;
    }
    
    public void setChecknm(String chkn) {
        checknm = chkn;
    }
    
    public void setEvent (String evnt) {
        event = evnt;
    }
    
    public void setNotes (String nts) {
        notes = nts;
    }
    
    
    public String getOrnum() {
        return ornum;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAmount () {
        return amount;
    }
    
     public String getDtype() {
        return dtype;
    }
    
    public String getChecknm() {
        return checknm;
    }
    
    public String getEvent () {
        return event;
    }
    
    public String getNotes () {
       return notes;
    }
    
}
