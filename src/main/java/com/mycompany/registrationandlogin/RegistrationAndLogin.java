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
       // added a scanner for the user to write from the keyboard
       Scanner input = new Scanner(System.in);
       Scanner sc = new Scanner(System.in);
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
        
        if (regResult.equals("Registration successful.")){
            System.out.println("\nNow login:");
            System.out.println("Username:");
            String loginUsername = input.nextLine();
            System.out.println("Password:");
            String loginPassword = input.nextLine();
            
            System.out.println(login.ReturnLoginStatus(loginUsername, loginPassword));
        }
        
        // ---- Part 2: QuickChat ----
        System.out.println("Welcome to QuickChat.");
        System.out.println("How many messages do you wish to enter?");
        int maxMessages = Integer.parseInt(sc.nextLine().trim());
        int entered = 0;
        boolean running = true;
        
        while (running) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                if (entered >= maxMessages) {
                    System.out.println("You have reached your limit of " + maxMessages + " messages.");
                    continue;
                }
                
                String recipient;
                Message msg;
                while (true) {
                    System.out.println("Enter recipient cell number (e.g. +27718693002):");
                    recipient = sc.nextLine().trim();
                    Message check = new Message(entered, recipient, "x");
                    String result = check.checkRecipientCell();
                    System.out.println(result);
                    if (result.startsWith("Cell phone number successfully")) break;
                }
                
                String text;
                while (true) {
                    System.out.println("Enter your message (max 250 characters):");
                    text = sc.nextLine();
                    msg = new Message(entered, recipient, text);
                    String lengthResult = msg.checkMessageLength();
                    System.out.println(lengthResult);
                    if (lengthResult.equals("Message ready to send.")) break;
                }
                
                System.out.println(msg.messageIdGenerated());
                System.out.println("1) Send Message\n2) Disregard Message\n3) Store Message to send later");
                int option = Integer.parseInt(sc.nextLine().trim());
                System.out.println(msg.SentMessage(option));

                if (option == 1) {
                    System.out.println("Message ID: " + msg.getMessageId());
                    System.out.println("Message Hash: " + msg.getMessageHash());
                    System.out.println("Recipient: " + msg.getRecipient());
                    System.out.println("Message: " + msg.getMessageText());
                } else if (option == 2) {
                    sc.nextLine(); // user presses 0 to delete; message is simply not kept
                }
                entered++;

            } else if (choice.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (choice.equals("3")) {
                running = false;
            } else {
                System.out.println("Please choose 1, 2 or 3.");
            }
        }

        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }
}

    

