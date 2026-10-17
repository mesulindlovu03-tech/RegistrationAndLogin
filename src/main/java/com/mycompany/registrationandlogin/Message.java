/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandlogin;
import java.util.ArrayList;
import java. util.concurrent.ThreadLocalRandom;
/**
 *
 * @author Student
 */
public class Message {
    
    // Shared across all messages while the program runs
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static int totalSent = 0;

    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Normal constructor: ID is auto-generated
    public Message(int messageNumber, String recipient, String messageText) {
        this(generateId(), messageNumber, recipient, messageText);
    }
    
    // Second constructor: lets unit tests supply a known ID
    public Message(String messageId, int messageNumber, String recipient, String messageText) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Format: first two ID digits : message number : FIRSTWORDLASTWORD (all caps)
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageId.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }
    // Random 10-digit number (leading zeros allowed)
    private static String generateId() {
        long n = ThreadLocalRandom.current().nextLong(10_000_000_000L);
        return String.format("%010d", n);
    }
    
     public boolean checkMessageID() {
        return messageId.length() <= 10;
    }

    public String messageIdGenerated() {
        return "Message ID generated: " + messageId;
    }

    // Same rule as Part 1 (reused, as the brief's hint says)
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    
    public String getMessageId() { return messageId; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    
}
