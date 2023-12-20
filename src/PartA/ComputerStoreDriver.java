package PartA;

public class ComputerStoreDriver {
    public static void main(String[] args) {
        // Creating Computer objects
        Computer computer1 = new Computer("Dell", "XPS", 123456, 999.99);
        Computer computer2 = new Computer("HP", "Envy", 789012, 1299.99);

        // Displaying information of Computer objects
        System.out.println("Computer 1 Information:");
        System.out.println(computer1);
        
        System.out.println("\nComputer 2 Information:");
        System.out.println(computer2);

        // Modifying attributes of Computer objects
        computer1.setPrice(899.99);
        computer2.setModel("Pavilion");

        // Displaying modified information
        System.out.println("\nComputer 1 (After Modification) Information:");
        System.out.println(computer1);
        System.out.println("\nComputer 2 (After Modification) Information:");
        System.out.println(computer2);

        // Finding the number of created computers
        System.out.println("\nNumber of Created Computers: " + Computer.findNumberOfCreatedComputers());

        // Comparing Computer objects for equality
        if (computer1.equals(computer2)) {
            System.out.println("\nComputer 1 and Computer 2 are equal.");
        } else {
            System.out.println("\nComputer 1 and Computer 2 are not equal.");
        }
    }
}

