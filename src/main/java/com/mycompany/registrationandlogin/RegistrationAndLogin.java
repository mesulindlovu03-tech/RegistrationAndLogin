/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationandlogin;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class RegistrationAndLogin {

   public static void main(String[] args) {
        // one scanner for all keyboard input
        Scanner input = new Scanner(System.in);
        Login login = new Login();

        System.out.println("Enter your firstname:");
        String firstname = input.nextLine();
        System.out.println("Enter last name");
        String lastname = input.nextLine();
        System.out.println("Enter username(must cotain _ and be maximum of 5 characters):");
        String username = input.nextLine();
        System.out.println("Enter password (8 or more characters with a capital letter, a number and special character):");
        String password = input.nextLine();
        System.out.println("Enter South African cellphone (e.g +27123456789):");
        String cellphone = input.nextLine();

        String regResult = login.RegisterUser(username, password, cellphone, firstname, lastname);
        System.out.println(regResult);

        if (regResult.equals("Registration successful.")) {
            System.out.println("\nNow login:");
            System.out.println("Username:");
            String loginUsername = input.nextLine();
            System.out.println("Password:");
            String loginPassword = input.nextLine();

            System.out.println(login.ReturnLoginStatus(loginUsername, loginPassword));
        }

        // ---- Part 3: the depot keeps every message in arrays ----
        MessageDepot depot = new MessageDepot();
        String fileName = "stored_messages.json";
        System.out.println(depot.loadStoredFromJson(fileName));

        // ---- Part 2: QuickChat ----
        System.out.println("Welcome to QuickChat.");
        System.out.println("How many messages do you wish to enter?");
        int maxMessages = Integer.parseInt(input.nextLine().trim());
        int entered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit\n4) Stored Messages");
            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                if (entered >= maxMessages) {
                    System.out.println("You have reached your limit of " + maxMessages + " messages.");
                    continue;
                }

                String recipient;
                Message msg;
                while (true) {
                    System.out.println("Enter recipient cell number (e.g. +27718693002):");
                    recipient = input.nextLine().trim();
                    Message check = new Message(entered, recipient, "x");
                    String result = check.checkRecipientCell();
                    System.out.println(result);
                    if (result.startsWith("Cell phone number successfully")) break;
                }

                String text;
                while (true) {
                    System.out.println("Enter your message (max 250 characters):");
                    text = input.nextLine();
                    msg = new Message(entered, recipient, text);
                    String lengthResult = msg.checkMessageLength();
                    System.out.println(lengthResult);
                    if (lengthResult.equals("Message ready to send.")) break;
                }

                System.out.println(msg.messageIdGenerated());
                System.out.println("1) Send Message\n2) Disregard Message\n3) Store Message to send later");
                int option = Integer.parseInt(input.nextLine().trim());
                System.out.println(msg.SentMessage(option));

                if (option == 1) {
                    System.out.println("Message ID: " + msg.getMessageId());
                    System.out.println("Message Hash: " + msg.getMessageHash());
                    System.out.println("Recipient: " + msg.getRecipient());
                    System.out.println("Message: " + msg.getMessageText());
                } else if (option == 2) {
                    input.nextLine(); // user presses 0 to delete; message is simply not kept
                }

                // Part 3: save stored messages to JSON, then put the message in the arrays
                if (option == 3) {
                    System.out.println(msg.saveToJson(fileName));
                }
                depot.recordMessage(msg);
                entered++;

            } else if (choice.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (choice.equals("3")) {
                running = false;
            } else if (choice.equals("4")) {
                showStoredMenu(input, depot, fileName);
            } else {
                System.out.println("Please choose 1, 2, 3 or 4.");
            }
        }

        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }

    // Part 3: everything we can do with the arrays
    private static void showStoredMenu(Scanner input, MessageDepot depot, String fileName) {
        boolean inStored = true;

        while (inStored) {
            System.out.println("\nSTORED MESSAGES");
            System.out.println("1) Show the sender and recipient of all stored messages");
            System.out.println("2) Show the longest stored message");
            System.out.println("3) Search for a message by ID");
            System.out.println("4) Search for all messages to a recipient");
            System.out.println("5) Delete a message by message hash");
            System.out.println("6) Full message report");
            System.out.println("7) Back to the main menu");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(input.nextLine().trim());

            if (choice == 1) {
                System.out.println(depot.displayStored());
            } else if (choice == 2) {
                System.out.println("Longest stored message: " + depot.longestStoredMessage());
            } else if (choice == 3) {
                System.out.print("Message ID: ");
                System.out.println(depot.searchById(input.nextLine().trim()));
            } else if (choice == 4) {
                System.out.print("Recipient: ");
                System.out.println(depot.searchByRecipient(input.nextLine().trim()));
            } else if (choice == 5) {
                System.out.print("Message hash: ");
                String hash = input.nextLine().trim().toUpperCase();
                String result = depot.deleteByHash(hash);
                System.out.println(result);
                if (result.endsWith("successfully deleted.")) {
                    depot.removeStoredFromJson(fileName, hash);
                }
            } else if (choice == 6) {
                System.out.println(depot.report());
            } else if (choice == 7) {
                inStored = false;
            } else {
                System.out.println("Please choose a number from 1 to 7.");
            }
        }
    }
}

    

