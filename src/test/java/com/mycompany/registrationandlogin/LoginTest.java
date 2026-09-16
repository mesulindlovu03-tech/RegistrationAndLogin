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
        assertTrue (login.CheckCellPhone("0123456789"));
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
        assertTrue (login.LoginUser("mes_1", "WronPassword1!"));
    }
}
