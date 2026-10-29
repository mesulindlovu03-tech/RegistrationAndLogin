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
    
   private MessageDepot depot;

    // ---- Test data (from the task) ----
    // Message 1: +27834557896, "Did you get the cake?"                                  -> Sent
    // Message 2: +27838884567, "Where are you? You are late! I have asked you to be on time." -> Stored
    // Message 3: +27834484567, "Yohoooo, I am at your gate."                              -> Disregard
    // Message 4: Developer 0838884567, "It is dinner time!"                               -> Sent
    // Message 5: +27838884567, "Ok, I am leaving without you."                            -> Stored
    @BeforeEach
    void loadTestData() {
        depot = new MessageDepot();
        addMessage("0011223344", 1, "+27834557896", "Did you get the cake?", "Sent");
        addMessage("2900000002", 2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        addMessage("3300000003", 3, "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        addMessage("0838884567", 4, "0838884567", "It is dinner time!", "Sent");   // developer entry
        addMessage("4400000005", 5, "+27838884567", "Ok, I am leaving without you.", "Stored");
    }

    private void addMessage(String id, int number, String recipient, String text, String flag) {
        Message message = new Message(id, number, "+27831234567", recipient, text);
        message.setFlag(flag);
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
        assertEquals("00:1:DIDCAKE", depot.getMessageHash(0));
        assertEquals("29:2:WHERETIME", depot.getMessageHash(1));
    }

    @Test
    void storedMessagesLoadFromJsonIntoTheArrays() {
        String fileName = "test_stored.json";
        new File(fileName).delete();

        Message stored = new Message("2900000002", 2, "+27831234567", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        stored.setFlag("Stored");
        stored.saveToJson(fileName);

        MessageDepot fresh = new MessageDepot();
        assertEquals("1 stored message(s) loaded from " + fileName, fresh.loadStoredFromJson(fileName));
        assertEquals("Where are you? You are late! I have asked you to be on time.", fresh.getStoredMessage(0));
        assertEquals("29:2:WHERETIME", fresh.getMessageHash(0));

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

    @Test
    void searchForMessageId() {
        assertEquals("It is dinner time!", depot.findMessageById("0838884567"));
        assertEquals("Recipient: 0838884567 | Message: It is dinner time!", depot.searchById("0838884567"));
    }

    @Test
    void searchForMessageIdNotFound() {
        assertEquals("Message ID 0000000000 not found.", depot.findMessageById("0000000000"));
    }

    @Test
    void searchAllMessagesForARecipient() {
        String expected = "Where are you? You are late! I have asked you to be on time.\n"
                        + "Ok, I am leaving without you.\n";
        assertEquals(expected, depot.searchByRecipient("+27838884567"));
    }

    @Test
    void searchRecipientIgnoresDisregardedMessages() {
        assertEquals("No messages found for +27834484567.", depot.searchByRecipient("+27834484567"));
    }

    @Test
    void deleteMessageUsingMessageHash() {
        String expected = "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        assertEquals(expected, depot.deleteByHash("29:2:WHERETIME"));
        assertEquals(4, depot.getTotalMessages());
        assertEquals(1, depot.getStoredCount());
        assertEquals("Ok, I am leaving without you.", depot.getStoredMessage(0));   // moved from index 1 to 0
        assertNull(depot.getStoredMessage(1));                                       // old last slot cleared
        assertEquals("Message ID 2900000002 not found.", depot.findMessageById("2900000002"));
    }

    @Test
    void deleteUnknownHash() {
        assertEquals("Message hash 99:9:NONE not found.", depot.deleteByHash("99:9:NONE"));
        assertEquals(5, depot.getTotalMessages());
    }

    @Test
    void sentMessagesCannotBeDeletedByHash() {
        assertEquals("Message hash 00:1:DIDCAKE not found.", depot.deleteByHash("00:1:DIDCAKE"));
    }

    @Test
    void deletedMessageIsRemovedFromTheJsonFile() {
        String fileName = "test_stored_delete.json";
        new File(fileName).delete();

        Message stored = new Message("2900000002", 2, "+27831234567", "+27838884567", "Where are you?");
        stored.setFlag("Stored");
        stored.saveToJson(fileName);

        depot.removeStoredFromJson(fileName, stored.getMessageHash());

        MessageDepot fresh = new MessageDepot();
        assertEquals("0 stored message(s) loaded from " + fileName, fresh.loadStoredFromJson(fileName));

        new File(fileName).delete();
    }

    @Test
    void reportShowsHashRecipientAndMessage() {
        String report = depot.report();
        assertTrue(report.contains("Message Hash: 00:1:DIDCAKE"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Message: It is dinner time!"));
        assertFalse(report.contains("Yohoooo"));            // disregarded messages are not reported
        assertTrue(report.endsWith("Total messages: 4"));
    }
}