/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationandlogin;

import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
/**
 *
 * @author Student
 */
public class MessageDepotTest {
    
    public MessageDepotTest() {  
    }
    private MessageDepot depot;

    @BeforeEach
    void loadTestData() {
        depot = new MessageDepot();
        addMessage("0011223344", 1, "+27834557896", "Did you get the cake?", 1);
        addMessage("2900000002", 2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", 3);
        addMessage("3300000003", 3, "+27834484567", "Yohoooo, I am at your gate.", 2);
        addMessage("0838884567", 4, "0838884567", "It is dinner time!", 1);   // developer entry
        addMessage("4400000005", 5, "+27838884567", "Ok, I am leaving without you.", 3);
    }
    
    private void addMessage(String id, int number, String recipient, String text, int choice) {
        Message message = new Message(id, number, "+27831234567", recipient, text);
        message.processMessage(choice);
        depot.recordMessage(message);
    }

    @Test
    void sentMessagesArrayIsCorrectlyPopulated() {
        assertEquals(2, depot.getSentCount());
        assertEquals("Did you get the cake?", depot.getSentMessage(0));
        assertEquals("It is dinner time!", depot.getSentMessage(1));
    }

    @Test
    void disregardedAndStoredArraysArePopulated() {
        assertEquals(1, depot.getDisregardedCount());
        assertEquals("Yohoooo, I am at your gate.", depot.getDisregardedMessage(0));
        assertEquals(2, depot.getStoredCount());
        assertEquals("Ok, I am leaving without you.", depot.getStoredMessage(1));
    }

    @Test
    void hashAndIdArraysContainEveryMessage() {
        assertEquals(5, depot.getTotalMessages());
        assertEquals("0838884567", depot.getMessageId(3));
        assertEquals("00:1:DIDCAKE?", depot.getMessageHash(0));
        assertEquals("29:2:WHERETIME.", depot.getMessageHash(1));
    }
    
     @Test
    void storedMessagesLoadFromJsonIntoTheArrays() {
        String fileName = "test_stored.json";
        new File(fileName).delete();

        Message stored = new Message("2900000002", 2, "+27831234567", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        stored.processMessage(3);
        stored.saveToJson(fileName);

        MessageDepot fresh = new MessageDepot();
        assertEquals("1 stored message(s) loaded from " + fileName, fresh.loadStoredFromJson(fileName));
        assertEquals("Where are you? You are late! I have asked you to be on time.", fresh.getStoredMessage(0));

        new File(fileName).delete();
    }
     @Test
    void displayStoredShowsSenderAndRecipient() {
        String expected = "Sender: +27831234567 | Recipient: +27838884567\n"
                        + "Sender: +27831234567 | Recipient: +27838884567\n";
        assertEquals(expected, depot.displayStored());
    }

    @Test
    void longestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", depot.longestStoredMessage());
    }
    
    
}
