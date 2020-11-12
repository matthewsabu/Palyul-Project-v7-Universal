/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.Controllers;

/**
 *
 * @author New
 */
public class PreviewUsers {
    String username;
    String password;
    String access;
    
    PreviewUsers() {
        
    }
    
    public PreviewUsers(String un, String pw, String acs) {
        username = un;
        password = pw;
        access = acs;
    }
    
    public void setUsername(String un) {
        username = un;
    }
    
    public void setPassword(String pw) {
        password = pw;
    }
    
    public void setAccess (String acs) {
        access = acs;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getAccess() {
        return access;
    }
    
}
