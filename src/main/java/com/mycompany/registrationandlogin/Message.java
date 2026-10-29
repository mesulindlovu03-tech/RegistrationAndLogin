/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandlogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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

    // PART 3: who sent the message, and what happened to it ("Sent", "Stored" or "Disregard")
    private String sender = "";
    private String flag = "Captured";

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

    // PART 3: same as above, but also records the sender
    public Message(String messageId, int messageNumber, String sender, String recipient, String messageText) {
        this(messageId, messageNumber, recipient, messageText);
        this.sender = sender;
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

    // PART 3 getters and setters
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

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
                flag = "Sent";
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                flag = "Disregard";
                return "Press 0 to delete the message.";
            case 3:
                flag = "Stored";
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

    // PART 3: saves THIS message as one line at the end of a JSON file
    // (Gson user guide: https://github.com/google/gson/blob/main/UserGuide.md)
    public String saveToJson(String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        try {
            FileWriter writer = new FileWriter(fileName, true);   // true = add to the end
            writer.write(json + "\n");
            writer.close();
            return "Message stored in " + fileName;
        } catch (IOException e) {
            return "Could not store the message.";
        }
    }
}