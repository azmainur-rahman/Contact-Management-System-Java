import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Contact {
    private String name;
    private String number;
    private String email;

    public Contact(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%-25s || %-25s || %-25s", name, number, email);
    }
}

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getChoice();
            switch (choice) {
                case 1 -> addContact();
                case 2 -> displayAllContacts();
                case 3 -> searchContact();
                case 4 -> editContact();
                case 5 -> deleteContact();
                case 6 -> exitProgram();
                default -> System.out.println("Invalid option. Please select between 1 and 6.");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("=====================================");
        System.out.println(" Welcome to Contact Management System");
        System.out.println("        Made by Kazi Azmainur Rahman");
        System.out.println("=====================================");
        System.out.println("1. Add new Contact");
        System.out.println("2. View all Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Edit Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Exit");
        System.out.print("Select your Option: ");
    }

    private static int getChoice() {
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 6.");
            sc.nextLine();
            return -1;
        }
    }

    private static void addContact() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Number: ");
        String number = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        contacts.add(new Contact(name, number, email));
        System.out.println("Contact added successfully!");
    }

    private static void displayAllContacts() {
        System.out.println("--- All Contacts ---");
        System.out.println(String.format("%-25s || %-25s || %-25s", "Name", "Number", "Email"));
        System.out.println("-------------------------------------------------------------------------");
        if (contacts.isEmpty()) {
            System.out.println("No contacts to display yet.");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private static void searchContact() {
        System.out.print("Enter the name or number to search: ");
        String searchTerm = sc.nextLine();
        boolean found = false;
        System.out.println("--- Search Results ---");
        System.out.println(String.format("%-25s || %-25s || %-25s", "Name", "Number", "Email"));
        System.out.println("-------------------------------------------------------------------------");

        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    contact.getNumber().contains(searchTerm)) {
                System.out.println(contact);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No contact found matching '" + searchTerm + "'.");
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private static void editContact() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to edit.");
            return;
        }

        displayAllContacts();
        int editChoice = getSearchMethod("edit");
        if (editChoice == -1) return;

        Contact target = findContact(editChoice);
        if (target == null) {
            System.out.println("No contact found matching your criteria.");
            return;
        }

        System.out.println("Editing contact: " + target.getName() + " (" + target.getNumber() + ")");
        System.out.print("Enter New Name (current: " + target.getName() + "): ");
        String newName = sc.nextLine();
        System.out.print("Enter New Number (current: " + target.getNumber() + "): ");
        String newNumber = sc.nextLine();
        System.out.print("Enter New Email (current: " + target.getEmail() + "): ");
        String newEmail = sc.nextLine();

        if (!newName.isEmpty()) target.setName(newName);
        if (!newNumber.isEmpty()) target.setNumber(newNumber);
        if (!newEmail.isEmpty()) target.setEmail(newEmail);

        System.out.println("Contact edited successfully!");
    }

    private static void deleteContact() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to delete.");
            return;
        }

        displayAllContacts();
        int deleteChoice = getSearchMethod("delete");
        if (deleteChoice == -1) return;

        Contact target = findContact(deleteChoice);
        if (target == null) {
            System.out.println("No contact found matching your criteria.");
            return;
        }

        contacts.remove(target);
        System.out.println("Contact deleted successfully!");
    }

    private static int getSearchMethod(String action) {
        while (true) {
            System.out.println("How do you want to " + action + " the contact?");
            System.out.println("1. By Name");
            System.out.println("2. By Number (exact match)");
            System.out.println("3. By List Number");
            System.out.print("Enter your choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 1 && choice <= 3) return choice;
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
                sc.nextLine();
            }
        }
    }

    private static Contact findContact(int method) {
        if (method == 1) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            for (Contact c : contacts) {
                if (c.getName().equalsIgnoreCase(name)) return c;
            }
        } else if (method == 2) {
            System.out.print("Enter Number: ");
            String number = sc.nextLine();
            for (Contact c : contacts) {
                if (c.getNumber().equals(number)) return c;
            }
        } else if (method == 3) {
            System.out.print("Enter List Number: ");
            try {
                int index = sc.nextInt();
                sc.nextLine();
                if (index > 0 && index <= contacts.size()) return contacts.get(index - 1);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            }
        }
        return null;
    }

    private static void exitProgram() {
        System.out.println("Exiting Contact Management System. Goodbye!");
        sc.close();
        System.exit(0);
    }
}
