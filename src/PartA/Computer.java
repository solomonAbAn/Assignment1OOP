package PartA;


public class Computer {
	
    private String brand;
    private String model;
    private long serialNumber;
    private double price;
    private static int numberOfCreatedComputers = 0;

    // Constructor
    public Computer(String brand, String model, long serialNumber, double price) throws ExceptionHandling {
    	if (brand == null || brand.isEmpty()) {
            throw new EmptyBrandNameException("Brand name cannot be empty");
        }

        if (model == null || model.isEmpty()) {
            throw new EmptyModelNameException("Model name cannot be empty");
        }

        if (serialNumber < 0) {
            throw new IllegalArgumentException("Serial number cannot be less than zero");
        }

        if (String.valueOf(serialNumber).length() < 6) {
            throw new IllegalArgumentException("Serial number must be at least 6 digits");
        }

        if (price < 0) {
            throw new NegativePriceException("Price cannot be negative");
        }
    	
    	this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.price = price;
        numberOfCreatedComputers++;
    }

    // Accessors and Mutators
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    // Display information
    @Override
	public String toString() {
		return "Computer [brand=" + brand + ", model=" + model + ", serialNumber=" + serialNumber + ", price=" + price
				+ "]";
	}

    
    

    // Find number of created computers
    public static int findNumberOfCreatedComputers() {
        return numberOfCreatedComputers;
    }
    

	// Equals method for comparing two Computer objects
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Computer computer = (Computer) obj;
        return brand.equals(computer.brand) && model.equals(computer.model) && Double.compare(computer.price, price) == 0;
    }
}
