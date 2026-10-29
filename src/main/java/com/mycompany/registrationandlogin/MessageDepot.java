/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationandlogin;

import com.google.gson.Gson;
import java.io.FileWriter;
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
        messageTexts[totalCount] = message.getMessageText();
        messageFlags[totalCount] = message.getFlag();
        totalCount++;

        String flag = message.getFlag();
        if (flag.equals("Sent")) {
            sentMessages[sentCount] = message.getMessageText();
            sentCount++;
        } else if (flag.equals("Disregard")) {
            disregardedMessages[disregardedCount] = message.getMessageText();
            disregardedCount++;
        } else if (flag.equals("Stored")) {
            storedMessages[storedCount] = message.getMessageText();
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
    
    // c) Search for a message by its ID: returns just the message text
    public String findMessageById(String id) {
        int index = indexOfId(id);
        if (index == -1) {
            return "Message ID " + id + " not found.";
        }
        return messageTexts[index];
    }

    // c) ...the same search, shown as recipient + message
    public String searchById(String id) {
        int index = indexOfId(id);
        if (index == -1) {
            return "Message ID " + id + " not found.";
        }
        return "Recipient: " + messageRecipients[index] + " | Message: " + messageTexts[index];
    }

    // Finds the index of a message ID in the parallel arrays (-1 = not found)
    private int indexOfId(String id) {
        for (int i = 0; i < totalCount; i++) {
            if (messageIds[i].equals(id)) {
                return i;
            }
        }
        return -1;
    }

    // d) Every sent or stored message for one recipient
    public String searchByRecipient(String recipient) {
        String result = "";
        for (int i = 0; i < totalCount; i++) {
            boolean kept = messageFlags[i].equals("Sent") || messageFlags[i].equals("Stored");
            if (kept && messageRecipients[i].equals(recipient)) {
                result = result + messageTexts[i] + "\n";
            }
        }
        if (result.equals("")) {
            return "No messages found for " + recipient + ".";
        }
        return result;
    }
    
    // e) Delete a stored message using its hash
    public String deleteByHash(String hash) {
        // Step 1: find the index of the stored message with this hash
        int deleteIndex = -1;
        for (int i = 0; i < totalCount; i++) {
            if (messageHashes[i].equals(hash) && messageFlags[i].equals("Stored")) {
                deleteIndex = i;
                break;
            }
        }
        if (deleteIndex == -1) {
            return "Message hash " + hash + " not found.";
        }

        String deleted = messageTexts[deleteIndex];

        // Step 2: work out the same message's position in the stored array
        int storedIndex = 0;
        for (int i = 0; i < deleteIndex; i++) {
            if (messageFlags[i].equals("Stored")) {
                storedIndex++;
            }
        }

        // Step 3: shift everything after it one place left, in EVERY array
        removeAt(messageIds, deleteIndex, totalCount);
        removeAt(messageHashes, deleteIndex, totalCount);
        removeAt(messageSenders, deleteIndex, totalCount);
        removeAt(messageRecipients, deleteIndex, totalCount);
        removeAt(messageTexts, deleteIndex, totalCount);
        removeAt(messageFlags, deleteIndex, totalCount);
        totalCount--;

        removeAt(storedMessages, storedIndex, storedCount);
        storedCount--;

        return "Message: \"" + deleted + "\" successfully deleted.";
    }

    // Shifts everything after 'index' one place left and clears the old last slot
    private void removeAt(String[] array, int index, int count) {
        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
        }
        array[count - 1] = null;
    }

    // Also removes the message from the JSON file so it does not come back next time
    public String removeStoredFromJson(String fileName, String hash) {
        Gson gson = new Gson();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            FileWriter writer = new FileWriter(fileName);   // overwrite the file
            boolean removed = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.equals("")) {
                    continue;
                }
                Message m = gson.fromJson(line, Message.class);
                if (!removed && m.getMessageHash().equals(hash)) {
                    removed = true;                          // skip this one line
                } else {
                    writer.write(line + "\n");
                }
            }
            writer.close();
            return "Stored file updated.";
        } catch (IOException e) {
            return "Could not update the stored file.";
        }
    }
    
     // f) Full report of every sent and stored message
    public String report() {
        String result = "MESSAGE REPORT\n";
        int shown = 0;
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals("Sent") || messageFlags[i].equals("Stored")) {
                result = result + "----------\n"
                       + "Message Hash: " + messageHashes[i] + "\n"
                       + "Message ID: " + messageIds[i] + "\n"
                       + "Sender: " + messageSenders[i] + "\n"
                       + "Recipient: " + messageRecipients[i] + "\n"
                       + "Message: " + messageTexts[i] + "\n"
                       + "Status: " + messageFlags[i] + "\n";
                shown++;
            }
        }
        if (shown == 0) {
            return "No messages to report.";
        }
        return result + "----------\nTotal messages: " + shown;
    }
}
