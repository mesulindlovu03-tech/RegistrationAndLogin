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
    private String StoredLastName;
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
    public boolean CheckCellPhone(String cellphone){
        if (cellphone == null){
            return false;
        }
        return cellphone.matches("^\\+27\\d{9}$");
    }
    public String RegisterUser(String username, String password, String cellphone,String firstname, String cellphone, String lastname){
        if (!CheckUserName(username)){
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five character in length.";
        }
         if (!CheckPasswordComplexity(password)){
            return "Password is not correctly formatted; please ensure that your password contains at least eight characters,capital letter, a number, and a special character.";
        }
          if (!CheckCellPhone(cellphone)){
            return "Cellphone number is not correctly formatted or does not cotain an international code; please correct the number and try again.";
        }
          this.StoredUsername = username;
          this.StoredPassword = password;
          this.StoredCellPhone = cellphone;
          this.StoredFirstname = firstname;
          this.StoredLastName = lastname;
          this.isRegistered = true;
          
          return "registration successful";
    }
    public boolean LoginUser (String username, String password){
        if (!isRegistered){
            return false;
        }
        return username.equals(StoredUsername) && password.equals(StoredPassword);
    }
    public String ReturnLoginStatus(String username, String password){
        if (LoginUser(username, password)){
            return "Welcome"+StoredFirstname +","+ StoredLastName + "it is great to see you.";
        }
        return "Username or password incorrect, please try again";
    }
}
