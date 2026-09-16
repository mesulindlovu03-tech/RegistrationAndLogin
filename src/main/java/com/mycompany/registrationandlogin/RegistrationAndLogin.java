/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationandlogin;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class RegistrationAndLogin {

    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       Login login = new Login();
       
        System.out.println("Enter your firstname:");
        String firstname = input.nextLine();
        System.out.println("Enter last name");
        String lastname = input.nextLine();
        System.out.println("Enter username(must cotain _ and be maximum of 5 characters):");
        String username = input.nextLine();
        System.out.println("Enter password (8 or more characters with a capital letter, a number and special character):");
        String password = input.nextLine();
        System.out.println("Enter South African cellphone (e.g +27123456789):");
        String cellphone = input.nextLine();
         
        String regResult = login.RegisterUser(username, password, cellphone, firstname, cellphone, lastname);
        System.out.println(regResult);
        
        if (regResult.equals("Registration successful.")){
            System.out.println("\nNow login:");
            System.out.println("Username:");
            String loginUsername = input.nextLine();
            System.out.println("Password:");
            String loginPassword = input.nextLine();
            
            System.out.println(login.ReturnLoginStatus(loginUsername, loginPassword));
        }
    }
}
