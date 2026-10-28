/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandlogin;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Student
 */
public class MessageDepot {
  
    public static final int MAX_MESSAGES = 50;

    // The five arrays the task asks for
    private String[] sentMessages = new String[MAX_MESSAGES];
    private String[] disregardedMessages = new String[MAX_MESSAGES];
    private String[] storedMessages = new String[MAX_MESSAGES];
    private String[] messageHashes = new String[MAX_MESSAGES];
    private String[] messageIds = new String[MAX_MESSAGES];

    // Parallel arrays: the same index belongs to the same message
    private String[] messageSenders = new String[MAX_MESSAGES];
    private String[] messageRecipients = new String[MAX_MESSAGES];
    private String[] messageTexts = new String[MAX_MESSAGES];
    private String[] messageFlags = new String[MAX_MESSAGES];

    private int sentCount = 0;
    private int disregardedCount = 0;
    private int storedCount = 0;
    private int totalCount = 0;

    // Puts a message into the right arrays, using the counters as the next free index
    public String recordMessage(Message message) {
        if (totalCount >= MAX_MESSAGES) {
            return "The message depot is full.";
        }

        messageIds[totalCount] = message.getMessageId();
        messageHashes[totalCount] = message.getMessageHash();
        messageSenders[totalCount] = message.getSender();
        messageRecipients[totalCount] = message.getRecipient();
        messageTexts[totalCount] = message.getText();
        messageFlags[totalCount] = message.getFlag();
        totalCount++;

        String flag = message.getFlag();
        if (flag.equals("Sent")) {
            sentMessages[sentCount] = message.getText();
            sentCount++;
        } else if (flag.equals("Disregard")) {
            disregardedMessages[disregardedCount] = message.getText();
            disregardedCount++;
        } else if (flag.equals("Stored")) {
            storedMessages[storedCount] = message.getText();
            storedCount++;
        }
        return "Message recorded.";
    }

    // ---- Getters ----
    public int getSentCount() { return sentCount; }
    public int getDisregardedCount() { return disregardedCount; }
    public int getStoredCount() { return storedCount; }
    public int getTotalMessages() { return totalCount; }
    public String getSentMessage(int index) { return sentMessages[index]; }
    public String getDisregardedMessage(int index) { return disregardedMessages[index]; }
    public String getStoredMessage(int index) { return storedMessages[index]; }
    public String getMessageHash(int index) { return messageHashes[index]; }
    public String getMessageId(int index) { return messageIds[index]; }

    // Reads the stored messages from the JSON file INTO the arrays
    public String loadStoredFromJson(String fileName) {
        Gson gson = new Gson();
        int loaded = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.equals("") && totalCount < MAX_MESSAGES) {
                    Message m = gson.fromJson(line, Message.class);   // JSON text -> Message object
                    recordMessage(m);
                    loaded++;
                }
            }
            return loaded + " stored message(s) loaded from " + fileName;
        } catch (IOException e) {
            return "No stored messages on file yet.";
        }
    }
    
    // a) Sender and recipient of every stored message
    public String displayStored() {
        String result = "";
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals("Stored")) {
                result = result + "Sender: " + messageSenders[i]
                       + " | Recipient: " + messageRecipients[i] + "\n";
            }
        }
        if (result.equals("")) {
            return "No stored messages.";
        }
        return result;
    }

    // b) The longest stored message
    public String longestStoredMessage() {
        if (storedCount == 0) {
            return "No stored messages.";
        }
        String longest = storedMessages[0];
        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        return longest;
    }
    
    
}
