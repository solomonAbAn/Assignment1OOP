package PartBB;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ComputerStoreDriver {
    private static final String PASSWORD = "password";
    private static final int MAX_TRIES = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxComputers;
        Computer[] inventory;

        // Display welcome message
        System.out.println("Welcome to the Computer Store!");

        // Prompt for the maximum number of computers
        System.out.print("Enter the maximum number of computers in your store: ");
        try {
            maxComputers = validatePositiveIntegerInput(scanner.nextLine());
            inventory = new Computer[maxComputers];
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a positive integer for the maximum number of computers.");
            return;
        }

        int choice = 0;
        do {
            displayMainMenu();
            System.out.print("Please enter your choice (1-5): ");
            try {
                choice = validateMenuChoice(scanner.nextLine());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {
            case 1:
                if (authenticateUser(scanner)) {
                    enterNewComputers(scanner, inventory);
                } else {
                    System.out.println("Too many incorrect password attempts. Returning to the main menu.");
                }
                break;
            case 2:
                changeComputerInformation(scanner, inventory);
                break;
            case 3:
                displayComputersByBrand(scanner, inventory);
                break;
            case 4:
                displayComputersUnderPrice(scanner, inventory);
                break;
            case 5:
                System.out.println("Exiting the Computer Store. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
        }

        } while (choice != 5);

        scanner.close();
    }

    private static boolean authenticateUser(Scanner scanner) {
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
    
	private static int validateMenuChoice(String input) throws InputMismatchException, NumberFormatException {
        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > 5) {
                throw new InputMismatchException();
            }
            return choice;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid choice. Please enter a number between 1 and 5.");
        }
    }

    private static void displayMainMenu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Enter new computers (password required)");
        System.out.println("2. Change information of a computer (password required)");
        System.out.println("3. Display all computers by a specific brand");
        System.out.println("4. Display all computers under a certain price");
        System.out.println("5. Quit");
    }

    private static int validatePositiveIntegerInput(String input) throws InputMismatchException {
        try {
            int value = Integer.parseInt(input);
            if (value <= 0) {
                throw new InputMismatchException();
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Invalid input. Please enter a positive integer.");
        }
    }

    private static void enterNewComputers(Scanner scanner, Computer[] inventory) {
        int numComputers;
        try {
            System.out.print("Enter the number of computers you want to enter: ");
            numComputers = validatePositiveIntegerInput(scanner.nextLine());
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a positive integer for the number of computers.");
            return;
        }

        if (numComputers <= inventory.length) {
            for (int i = 0; i < numComputers; i++) {
                System.out.println("Enter information for Computer " + (i + 1) + ":");
                Computer newComputer = createComputer(scanner, inventory);
                inventory[i] = newComputer;
            }

            System.out.println("Computers successfully added to the inventory.");
        } else {
            System.out.println("Not enough space in the inventory.");
        }
    }

    private static Computer createComputer(Scanner scanner, Computer[] inventory) {
        String brand;
        String model;

        do {
            System.out.print("Brand: ");
            brand = scanner.nextLine();
            if (brand.trim().isEmpty()) {
                System.out.println("Brand cannot be empty. Please enter a valid brand.");
            }
        } while (brand.trim().isEmpty());

        do {
            System.out.print("Model: ");
            model = scanner.nextLine();
            if (model.trim().isEmpty()) {
                System.out.println("Model cannot be empty. Please enter a valid model.");
            }
        } while (model.trim().isEmpty());

        int serialNumber = 1000000 + findLastSerialNumber(inventory);
        double price;
        try {
            System.out.print("Price: ");
            price = validateNonNegativeDoubleInput(scanner.nextLine());
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a non-negative number for the price.");
            return null;
        }

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

    private static double validateNonNegativeDoubleInput(String input) throws InputMismatchException, NumberFormatException {
        try {
            double value = Double.parseDouble(input);
            if (value < 0) {
                throw new InputMismatchException();
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Invalid input. Please enter a non-negative number.");
        }
    }

    private static void changeComputerInformation(Scanner scanner, Computer[] inventory) {
        System.out.print("Enter the computer number you wish to update (index in the array): ");
        int computerNumber = scanner.nextInt();
        scanner.nextLine();

        if (isValidIndex(computerNumber, inventory.length) && inventory[computerNumber - 1] != null) {
            displayComputerInfo(inventory[computerNumber - 1]);

            char updateChoice;
            do {
                displayUpdateMenu();
                System.out.print("Enter your choice (1-5): ");
                updateChoice = scanner.next().charAt(0);
                scanner.nextLine();

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
                        scanner.nextLine();
                        inventory[computerNumber - 1].setSerialNumber(newSerialNumber);
                        break;
                    case '4':
                        System.out.print("Enter the new price: ");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();
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

    private static void displayComputerInfo(Computer computer) {
        System.out.println(computer);
        System.out.println("--------------------------");
    }

    private static void displayComputersByBrand(Scanner scanner, Computer[] inventory) {
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

    private static void displayComputersUnderPrice(Scanner scanner, Computer[] inventory) {
        System.out.print("Enter the maximum price to search for: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();

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

    private static boolean isValidIndex(int index, int arrayLength) {
        
    	return index >= 1 && index <= arrayLength;
    }
    
}