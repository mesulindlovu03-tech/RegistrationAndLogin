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

    public MessageTest() {
    }

    @Test
    public void testSomeMethod() {
    }
   
    private Message makeMessage() {
        return new Message("2900000002", 2, "+27831234567", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
    }
    
    @Test
    void messageIdHasTenCharacters() {
        assertTrue(makeMessage().checkMessageId());
        assertEquals(10, Message.generateMessageId().length());
    }

    @Test
    void messageHashIsCreatedCorrectly() {
        assertEquals("29:2:WHERETIME.", makeMessage().getMessageHash());
    }

    @Test
    void oneWordMessageHash() {
        Message m = new Message("1234567890", 1, "+27831234567", "+27838884567", "Hi");
        assertEquals("12:1:HIHI", m.getMessageHash());
    }

    @Test
    void recipientIsChecked() {
        assertEquals("Recipient accepted.", Message.checkRecipient("+27834557896"));
        assertEquals("Recipient must start with +27 and have 9 digits after it.", Message.checkRecipient("0838884567"));
        assertEquals("Recipient must start with +27 and have 9 digits after it.", Message.checkRecipient("+2783455789A"));
    }

    @Test
    void messageLengthIsChecked() {
        assertEquals("Message accepted.", Message.checkText("Did you get the cake?"));
        assertEquals("Message is 5 characters too long.", Message.checkText("x".repeat(255)));
    }

    @Test
    void processMessageSetsTheFlag() {
        Message m = makeMessage();
        assertEquals("Message sent.", m.processMessage(1));
        assertEquals("Sent", m.getFlag());
        assertEquals("Message disregarded.", m.processMessage(2));
        assertEquals("Disregard", m.getFlag());
        assertEquals("Message stored.", m.processMessage(3));
        assertEquals("Stored", m.getFlag());
        assertEquals("Invalid option.", m.processMessage(9));
    }
    
    private Message makeMessages() {
        return new Message("2900000002", 2, "+27831234567", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
    }

    @Test
    void MessageIdHasTenCharacters() {
        assertTrue(makeMessage().checkMessageId());
        assertEquals(10, Message.generateMessageId().length());
    }

    @Test
    void MessageHashIsCreatedCorrectly() {
        assertEquals("29:2:WHERETIME.", makeMessage().getMessageHash());
    }

    @Test
    void OneWordMessageHash() {
        Message m = new Message("1234567890", 1, "+27831234567", "+27838884567", "Hi");
        assertEquals("12:1:HIHI", m.getMessageHash());
    }

    @Test
    void RecipientIsChecked() {
        assertEquals("Recipient accepted.", Message.checkRecipient("+27834557896"));
        assertEquals("Recipient must start with +27 and have 9 digits after it.", Message.checkRecipient("0838884567"));
        assertEquals("Recipient must start with +27 and have 9 digits after it.", Message.checkRecipient("+2783455789A"));
    }

    @Test
    void MessageLengthIsChecked() {
        assertEquals("Message accepted.", Message.checkText("Did you get the cake?"));
        assertEquals("Message is 5 characters too long.", Message.checkText("x".repeat(255)));
    }

    @Test
    void ProcessMessageSetsTheFlag() {
        Message m = makeMessage();
        assertEquals("Message sent.", m.processMessage(1));
        assertEquals("Sent", m.getFlag());
        assertEquals("Message disregarded.", m.processMessage(2));
        assertEquals("Disregard", m.getFlag());
        assertEquals("Message stored.", m.processMessage(3));
        assertEquals("Stored", m.getFlag());
        assertEquals("Invalid option.", m.processMessage(9));
    }
}
