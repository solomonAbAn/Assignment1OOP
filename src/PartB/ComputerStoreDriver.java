package PartB;

import java.util.Scanner;

public class ComputerStoreDriver {
    private static final String PASSWORD = "password";
    private static final int MAX_TRIES = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxComputers;

        System.out.println("Welcome to the Computer Store Management System!");

        System.out.print("Enter the maximum number of computers in your store: ");
        maxComputers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Computer[] inventory = new Computer[maxComputers];

        int choice;
        int tries = 0;
        do {
            displayMainMenu();
            System.out.print("Please enter your choice (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    if (authenticateUser(scanner, tries)) {
                        enterNewComputers(inventory, scanner);
                    } else {
                        System.out.println("Too many incorrect password attempts. Returning to the main menu.");
                    }
                    break;
                case 2:
                    if (authenticateUser(scanner, tries)) {
                        changeComputerInformation(inventory, scanner);
                    } else {
                        System.out.println("Too many incorrect password attempts. Returning to the main menu.");
                    }
                    break;
                case 3:
                    displayComputersByBrand(inventory, scanner);
                    break;
                case 4:
                    displayComputersUnderPrice(inventory, scanner);
                    break;
                case 5:
                    System.out.println("Exiting the Computer Store Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }

        } while (choice != 5);

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Enter new computers (password required)");
        System.out.println("2. Change information of a computer (password required)");
        System.out.println("3. Display all computers by a specific brand");
        System.out.println("4. Display all computers under a certain price");
        System.out.println("5. Quit");
    }

    private static boolean authenticateUser(Scanner scanner, int tries) {
        for (int i = 0; i < MAX_TRIES; i++) {
            System.out.print("Enter the password (attempt " + (i + 1) + "): ");
            String enteredPassword = scanner.nextLine();
            if (enteredPassword.equals(PASSWORD)) {
                return true;
            } else {
                System.out.println("Incorrect password. Try again.");
            }
        }
        return false;
    }

    private static void enterNewComputers(Computer[] inventory, Scanner scanner) {
        System.out.print("Enter the number of computers you want to enter: ");
        int numComputers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (numComputers <= inventory.length) {
            for (int i = 0; i < numComputers; i++) {
                System.out.println("Enter information for Computer " + (i + 1) + ":");
                Computer newComputer = createComputer(inventory, scanner);
                inventory[i] = newComputer;
            }

            System.out.println("Computers successfully added to the inventory.");
        } else {
            System.out.println("Not enough space in the inventory.");
        }
    }

    private static Computer createComputer(Computer[] inventory, Scanner scanner) {
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        int serialNumber = 1000000 + findLastSerialNumber(inventory); // Increment from the last serial number
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        return new Computer(brand, model, serialNumber, price);
    }

    private static int findLastSerialNumber(Computer[] inventory) {
        int lastSerialNumber = 0;
        for (Computer computer : inventory) {
            if (computer != null && computer.getSerialNumber() > lastSerialNumber) {
                lastSerialNumber = computer.getSerialNumber();
            }
        }
        return lastSerialNumber;
    }

    private static void changeComputerInformation(Computer[] inventory, Scanner scanner) {
        System.out.print("Enter the computer number you wish to update (index in the array): ");
        int computerNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (isValidIndex(computerNumber, inventory.length) && inventory[computerNumber - 1] != null) {
            displayComputerInfo(inventory[computerNumber - 1]);

            char updateChoice;
            do {
                displayUpdateMenu();
                System.out.print("Enter your choice (1-5): ");
                updateChoice = scanner.next().charAt(0);
                scanner.nextLine(); // Consume the newline character

                switch (updateChoice) {
                    case '1':
                        System.out.print("Enter the new brand: ");
                        String newBrand = scanner.nextLine();
                        inventory[computerNumber - 1].setBrand(newBrand);
                        break;
                    case '2':
                        System.out.print("Enter the new model: ");
                        String newModel = scanner.nextLine();
                        inventory[computerNumber - 1].setModel(newModel);
                        break;
                    case '3':
                        System.out.print("Enter the new SN: ");
                        int newSerialNumber = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        inventory[computerNumber - 1].setSerialNumber(newSerialNumber);
                        break;
                    case '4':
                        System.out.print("Enter the new price: ");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character
                        inventory[computerNumber - 1].setPrice(newPrice);
                        break;
                    case '5':
                        System.out.println("Update operation canceled. Returning to the main menu.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }

                displayComputerInfo(inventory[computerNumber - 1]);
            } while (updateChoice != '5');
        } else {
            System.out.println("Invalid computer number or no computer found at the specified index.");
        }
    }

    private static void displayUpdateMenu() {
        System.out.println("\n===== Update Computer Information =====");
        System.out.println("1. Update brand");
        System.out.println("2. Update model");
        System.out.println("3. Update SN");
        System.out.println("4. Update price");
        System.out.println("5. Quit update");
    }

    private static void displayComputersByBrand(Computer[] inventory, Scanner scanner) {
        System.out.print("Enter the brand name to search for: ");
        String brandToSearch = scanner.nextLine();

        System.out.println("\nComputers with brand \"" + brandToSearch + "\":");
        boolean found = false;
        for (Computer computer : inventory) {
            if (computer != null && computer.getBrand().equalsIgnoreCase(brandToSearch)) {
                displayComputerInfo(computer);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No computers found with brand \"" + brandToSearch + "\".");
        }
    }

    private static void displayComputersUnderPrice(Computer[] inventory, Scanner scanner) {
        System.out.print("Enter the maximum price to search for: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.println("\nComputers under $" + maxPrice + ":");
        boolean found = false;
        for (Computer computer : inventory) {
            if (computer != null && computer.getPrice() < maxPrice) {
                displayComputerInfo(computer);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No computers found under $" + maxPrice + ".");
        }
    }

    private static void displayComputerInfo(Computer computer) {
        System.out.println(computer);
        System.out.println("--------------------------");
    }

    private static boolean isValidIndex(int index, int arrayLength) {
        return index >= 1 && index <= arrayLength;
    }
}

