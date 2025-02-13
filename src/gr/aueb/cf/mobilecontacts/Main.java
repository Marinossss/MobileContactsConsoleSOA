package gr.aueb.cf.mobilecontacts;

import gr.aueb.cf.mobilecontacts.controller.MobileContactController;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final MobileContactController controller = new MobileContactController();

    public static void main(String[] args) {
        String choice;

        while (true) {
            printMenu();
            choice = getToken();

            if (choice.equals("q") || (choice.equals("Q"))) {
                break;
            }

            handleChoice(choice);
        }

        System.out.println("Thank you for using the Mobile Contacts App!");
    }

    public static void handleChoice(String choice) {
        String firstname;
        String lastname;
        String phoneNumber;
        String response;
        long id;

        switch (choice) {
            case "1":
                System.out.println("Please enter First Name, Last Name, Phone Number:");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();
                MobileContactInsertDTO insertDTO = new MobileContactInsertDTO(firstname, lastname, phoneNumber);
                response = controller.insertContact(insertDTO);

                if (response.startsWith("OK")) {
                    System.out.println("Contact successfully added!");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Failed to add contact.");
                    System.out.println(response.substring(7));
                }
                break;
            case "2":
                System.out.println("Enter Phone Number:");
                phoneNumber = getToken();
                response = controller.getContactByPhoneNumber(phoneNumber);
                if (response.startsWith("Error")) {
                    System.out.println("Contact not found.");
                    System.out.println(response.substring(3));
                    return;
                }
                System.out.println("Failed to update contact.");
                System.out.println(response.substring(6));
                System.out.println("Enter existing ID:");
                long oldId = Long.parseLong(getToken());
                System.out.println("Please enter new first name:");
                firstname = getToken();
                System.out.println("Please enter new last name:");
                lastname = getToken();
                System.out.println("Please enter new phone number:");
                phoneNumber = getToken();
                MobileContactUpdateDTO mobileContactUpdateDTO = new MobileContactUpdateDTO(oldId, firstname, lastname, phoneNumber);
                response = controller.updateContact(mobileContactUpdateDTO);
                System.out.println(response);
                break;
            case "3":
                System.out.println("Enter Contact ID:");
                id = Long.parseLong(getToken());
                response = controller.deleteContactById(id);
                if (response.startsWith("OK")) {
                    System.out.println("Contact successfully deleted!");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Failed to delete contact.");
                    System.out.println(response.substring(6));
                }
                break;
            case "4":
                System.out.println("Enter Contact ID:");
                id = Long.parseLong(getToken());
                response = controller.getContactById(id);
                if (response.startsWith("OK")) {
                    System.out.println("Contact found successfully!");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Failed to find contact.");
                    System.out.println(response.substring(6));
                }
                break;
            case "5":
                List<String> mobileContacts = controller.getAllContacts();
                if (mobileContacts.isEmpty()) System.out.println("Contact list is empty.");
                mobileContacts.forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public static void printMenu() {
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Add a new contact");
        System.out.println("2. Update an existing contact");
        System.out.println("3. Delete a contact");
        System.out.println("4. Search for a contact");
        System.out.println("5. View all contacts");
        System.out.println("Q/q. Exit");
    }

    public static String getToken() {
        return in.nextLine().trim();
    }
}
