/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandlogin;

/**
 *
 * @author Student
 */
public class Login {
    private String StoredUsername;
    private String StoredPassword;
    private String StoredCellPhone;
    private String StoredFirstname;
    private String StroredLastName;
    private boolean isRegistered = false;
    
    public boolean CheckUserName(String username){
        if (username ==null){
            return false;
        }
        return username.contains("_") && username.length() <=5;
    }
    public boolean CheckPasswordComplexity(String password){
        if (password == null || password.length() <8){
            return false;
        }
        boolean hasUpper  =password.matches(".*[A-Z].*");
        boolean hasDigit  = password.matches(".*\\d.*");
        boolean hasSpecial  = password.matches(".*[^a-zA-Z0-9].*");
        return hasUpper &&hasDigit && hasSpecial;
    }
    public boolean ChceakCellPhone(String cellphone){
        if (cellphone == null){
            return false;
        }
        return cellphone.matches("^\\+27\\d{9}$");
    }
}
