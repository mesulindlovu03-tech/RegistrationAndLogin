/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationandlogin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    public LoginTest() {
    }
    @Test
    public void testCheckUserName_correctlyFormatted() {
        Login login = new Login();
        assertTrue(login.CheckUserName("mes_1"));
    }
    @Test
    public void testCheckUserName_incorrectlyFormatted() {
        Login login = new Login();
        assertFalse(login.CheckUserName("Mesuli!!!!!!"));
    }
    @Test
    public void testCheckPasswordComplexity_meetsRequirements() {
        Login login = new Login();
        assertTrue(login.CheckPasswordComplexity("Ch&&sec@ke99!"));
    }
    @Test
    public void testCheckPasswordComplexity_doesNotmeetsRequirements() {
        Login login = new Login();
        assertFalse (login.CheckPasswordComplexity("password"));
    }
    @Test
    public void testCheckCellPhone_correctlyFormatted() {
        Login login = new Login();
        assertTrue (login.CheckCellPhone("+27123456789"));
    }
    @Test
    public void testCheckCellPhone_incorrectlyFormatted() {
        Login login = new Login();
        assertFalse (login.CheckCellPhone("0123456789"));
    }
    @Test
    public void testLogin_successful() {
        Login login = new Login();
        login.RegisterUser("mes_1", "Ch&&sec@ke99!", "+27123456789", "Mesuli", "Ndlovu");
        assertTrue (login.LoginUser("mes_1", "Ch&&sec@ke99!"));
    }
    @Test
    public void testLogin_Failed() {
        Login login = new Login();
        login.RegisterUser("mes_1", "Ch&&sec@ke99!", "+27123456789", "Mesuli", "Ndlovu");
        assertFalse (login.LoginUser("mes_1", "WronPassword1!"));
    }
    @Test 
    public void testRegisterUser_invalidUsername() {
        Login login = new Login();
        String result = login.RegisterUser("mesuli!!!!!!", "Ch&&secc@ke99!", "+27123456789", "Mesuli", "Ndlovu");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five character in length.", result);
    }
    @Test 
    public void testRegisterUser_invalidPassword() {
        Login login = new Login();
        String result = login.RegisterUser("mes_1", "password", "+27123456789", "Mesuli", "Ndlovu");
        assertEquals("Password is not correctly formatted; please ensure that your password contains at least eight characters,capital letter, a number, and a special character.", result);
    }
    @Test 
    public void testRegisterUser_invalidCellPhone() {
        Login login = new Login();
        String result = login.RegisterUser("mes_1", "Ch&&secc@ke99!", "0123456789", "Mesuli", "Ndlovu");
        assertEquals("Cellphone number is not correctly formatted or does not cotain an international code; please correct the number and try again.", result);
    }
    @Test 
    public void testRegisterUser_success() {
        Login login = new Login();
        String result = login.RegisterUser("mes_1", "Ch&&secc@ke99!", "+27123456789", "Mesuli", "Ndlovu");
        assertEquals("Registration successful", result);
    }
    @Test 
    public void testReturnLoginStatus_success() {
        Login login = new Login();
        login.RegisterUser("mes_1", "Ch&&secc@ke99!","+27123456789", "Mesuli", "Ndlovu");
        String result = login.ReturnLoginStatus("mes_1", "Ch&&secc@ke99!");
        assertEquals("Welcome Mesuli Ndlovu it is good to see you", result);
    }
    @Test 
    public void testReturnLoginStatus_Failed() {
        Login login = new Login();
        String result = login.ReturnLoginStatus("mesuli!!!!!!", "password");
        assertEquals("Username or password incorrect, please try again", result);
    }
}
