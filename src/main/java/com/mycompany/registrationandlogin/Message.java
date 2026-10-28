/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandlogin;
import java.util.List;
import java. util.concurrent.ThreadLocalRandom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
/**
 *
 * @author Student
 */
public class Message {
    
    // Shared across all messages while the program runs
    private static final List<Message> sentMessages = new LinkedList<>();
    private static final List<Message> storedMessages = new LinkedList<>();
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
    
    // Random 10-digit number (leading zeros allowed)
    private static String generateId() {
        long n = ThreadLocalRandom.current().nextLong(10_000_000_000L);
        return String.format("%010d", n);
    }

    Message(String string, int i, String string0, String string1, String where_are_you_You_are_late_I_have_asked_y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
    public String checkMessageLength() {
        int length = messageText.length();
        if (length <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (length - 250) + "; please reduce the size.";
    }

    // Format: first two ID digits : message number : FIRSTWORDLASTWORD (all caps)
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageId.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }
    
    // choice: 1 = send, 2 = disregard, 3 = store
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageId).append("\n")
              .append("Message Hash: ").append(m.messageHash).append("\n")
              .append("Recipient: ").append(m.recipient).append("\n")
              .append("Message: ").append(m.messageText).append("\n\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalSent;
    }

    // Lets tests start from a clean slate
    public static void resetAll() {
        sentMessages.clear();
        storedMessages.clear();
        totalSent = 0;
    }

    // JSON storage approach based on the Gson user guide:
    // https://github.com/google/gson/blob/main/UserGuide.md
    public void storeMessage() {
        storedMessages.add(this);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("messages.json")) {
            gson.toJson(storedMessages, writer);
        } catch (IOException e) {
            System.out.println("Could not save messages: " + e.getMessage());
        }
    }
    
    private String MessageId;
    private int MessageNumber;
    private String sender;
    private String Recipient;
    private String text;
    private String flag;           // "Sent", "Stored" or "Disregard"
    private String MessageHash;
    
    // Constructor: runs when we create a new Message
    public Messages(String MessageId, int MessageNumber, String sender, String Recipient, String text) {
        this.MessageId = MessageId;
        this.MessageNumber = MessageNumber;
        this.sender = sender;
        this.Recipient = Recipient;
        this.text = text;
        this.flag = "Captured";
        this.MessageHash = createMessageHash();
    }
    
    // Makes a random 10-digit ID from two random 5-digit numbers
    public static String generateMessageId() {
        Random random = new Random();
        int firstHalf = random.nextInt(90000) + 10000;
        int secondHalf = random.nextInt(90000) + 10000;
        return "" + firstHalf + secondHalf;
    }
    
    // Checks that the ID has exactly 10 characters
    public boolean checkMessageId() {
        return MessageId.length() == 10;
    }

    // A recipient must be +27 followed by 9 digits, e.g. +27834557896
    public static String checkRecipient(String number) {
        if (!number.startsWith("+27") || number.length() != 12) {
            return "Recipient must start with +27 and have 9 digits after it.";
        }
        for (int i = 1; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return "Recipient must start with +27 and have 9 digits after it.";
            }
        }
        return "Recipient accepted.";
    }
    
    // A message may not be longer than 250 characters
    public static String checkText(String text) {
        if (text.length() <= 250) {
            return "Message accepted.";
        } else {
            int over = text.length() - 250;
            return "Message is " + over + " characters too long.";
        }
    }
    
    // Hash = first 2 digits of the ID : message number : FIRST WORD + LAST WORD, in CAPITALS
    // Example: ID 2900000002, message 2, "Where are you? ... on time." -> 29:2:WHERETIME.
    public String createdMessageHash() {
        String cleaned = text.trim();

        // First 2 digits of the ID
        String idPart = messageId.substring(0, 2);

        // First word: from the start up to the first space
        String firstWord;
        int firstSpace = cleaned.indexOf(" ");
        if (firstSpace == -1) {
            firstWord = cleaned;               // only one word
        } else {
            firstWord = cleaned.substring(0, firstSpace);
        }

        // Last word: from after the last space to the end
        int lastSpace = cleaned.lastIndexOf(" ");
        String lastWord = cleaned.substring(lastSpace + 1);

        String hash = idPart + ":" + MessageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }
    
    // 1 = send, 2 = disregard, 3 = store
    public String processMessage(int choice) {
        if (choice == 1) {
            flag = "Sent";
            return "Message sent.";
        } else if (choice == 2) {
            flag = "Disregard";
            return "Message disregarded.";
        } else if (choice == 3) {
            flag = "Stored";
            return "Message stored.";
        } else {
            return "Invalid option.";
        }
    }

    // All the details in one String, ready to print
    public String getDetails() {
        return "Message ID: " + MessageId
             + "\nMessage Hash: " + MessageHash
             + "\nRecipient: " + Recipient
             + "\nMessage: " + text
             + "\nStatus: " + flag;
    }
    
     // ---- Getters ----

    public String getMessagesId() {
        return MessageId;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipients() {
        return Recipient;
    }

    public String getText() {
        return text;
    }

    public String getFlag() {
        return flag;
    }

    public String getMessagesHash() {
        return MessageHash;
    }

}
