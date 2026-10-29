/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationandlogin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
    @BeforeEach
    public void setUp() {
        Message.resetAll();
    }

    // Test Case 1 data from the brief
    private Message testCase1() {
        return new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }

    // Test Case 2 data from the brief
    private Message testCase2() {
        return new Message("0098765432", 1, "08575975889", "Hi Keegan, did you receive the payment?");
    }

    @Test
    public void testMessageLength_success() {
        assertEquals("Message ready to send.", testCase1().checkMessageLength());
    }

    @Test
    public void testMessageLength_failure() {
        String longText = "a".repeat(260);
        Message m = new Message("0012345678", 0, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", m.checkMessageLength());
    }

    @Test
    public void testRecipient_success() {
        assertEquals("Cell phone number successfully captured.", testCase1().checkRecipientCell());
    }

    @Test
    public void testRecipient_failure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                testCase2().checkRecipientCell());
    }

    @Test
    public void testMessageHash_testCase1() {
        assertEquals("00:0:HITONIGHT", testCase1().createMessageHash());
    }

    @Test
    public void testMessageHash_allMessagesInLoop() {
        Message[] messages = { testCase1(), testCase2() };
        String[] expected = { "00:0:HITONIGHT", "00:1:HIPAYMENT" };
        for (int i = 0; i < messages.length; i++) {
            assertEquals(expected[i], messages[i].createMessageHash());
        }
    }

    @Test
    public void testMessageId_created() {
        Message m = testCase1();
        assertEquals("Message ID generated: 0012345678", m.messageIdGenerated());
        assertTrue(m.checkMessageID());
    }

    @Test
    public void testSentMessage_send() {
        assertEquals("Message successfully sent.", testCase1().SentMessage(1));
    }

    @Test
    public void testSentMessage_disregard() {
        assertEquals("Press 0 to delete the message.", testCase2().SentMessage(2));
    }

    @Test
    public void testSentMessage_store() {
        assertEquals("Message successfully stored.", testCase1().SentMessage(3));
    }

    @Test
    public void testTotalMessages_sentAndDiscarded() {
        testCase1().SentMessage(1); // sent
        testCase2().SentMessage(2); // discarded: should not count
        assertEquals(1, Message.returnTotalMessages());
    }

    // ---- PART 3: the flag and sender are set so MessageDepot can sort the message ----

    @Test
    public void testFlagIsSetBySentMessage() {
        Message m = testCase1();
        assertEquals("Captured", m.getFlag());
        m.SentMessage(1);
        assertEquals("Sent", m.getFlag());
        m.SentMessage(2);
        assertEquals("Disregard", m.getFlag());
    }

    @Test
    public void testSenderIsRecorded() {
        Message m = new Message("0012345678", 0, "+27831234567", "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("+27831234567", m.getSender());
        assertEquals("+27718693002", m.getRecipient());
    }
}