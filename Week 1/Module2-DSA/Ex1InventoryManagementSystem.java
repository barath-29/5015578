import java.util.Scanner;
import java.util.HashMap;

class Product {
    private final int productId;
    private String productName;
    private int quantity;
    private double price;

    Product(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    int getProductId() {
        return productId;
    }

    String getProductName() {
        return productName;
    }

    int getQuantity() {
        return quantity;
    }

    double getPrice() {
        return price;
    }

    void setProductName(String productName) {
        this.productName = productName;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    void setPrice(double price) {
        this.price = price;
    }

}

class Inventory {
    private HashMap<Integer, Product> inventory;

    Inventory() {
        this.inventory = new HashMap<>();
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public boolean addProduct(Product product) {

        if (inventory.containsKey(product.getProductId()))
            return false;

        inventory.putIfAbsent(product.getProductId(), product);
        return true;
    }

    public boolean updateProduct(Product product) {

        if (inventory.containsKey(product.getProductId())) {
            inventory.put(product.getProductId(), product);
            return true;
        }

        return false;
    }

    public boolean deleteProduct(Product product) {
        if (inventory.containsKey(product.getProductId())) {
            inventory.remove(product.getProductId());
            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("_______________________________________________________________");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Product ID", "Product Name", "Price", "Quantity");
        System.out.println("|____________|______________________|____________|____________|");

        for (Product product : inventory.values()) {
            System.out.printf("| %-10d | %-20s | %-10.2f | %-10d |\n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getQuantity());
        }
        System.out.println("_______________________________________________________________");
    }

}

public class Ex1InventoryManagementSystem {
    private static Inventory inventory = new Inventory();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            printMenu();
            choice = sc.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    displayInventory();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        sc.close();
        System.out.println("Exiting the application.... Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n=============================================\n");
        System.out.println("Inventory Management System");
        System.out.println("\n=============================================\n");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. Display Inventory");
        System.out.println("5. Quit");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    private static boolean emptyInventory() {

        if (inventory.isEmpty()) {
            System.out.println("\nInventory is Empty \n");
            return true;
        }

        return false;

    }

    private static void addProduct() {

        System.out.print("Enter product ID: ");
        int productId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter product name: ");
        String productName = sc.nextLine();
        System.out.print("Enter product quantity: ");
        int quantity = sc.nextInt();
        System.out.print("Enter product price: ");
        double price = sc.nextDouble();

        Product product = new Product(productId, productName, quantity, price);
        if (inventory.addProduct(product)) {
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Product with this ID already exists.");
        }
    }

    private static void updateProduct() {

        if (emptyInventory())
            return;

        System.out.print("Enter product ID to update: ");
        int productId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new product name: ");
        String productName = sc.nextLine();
        System.out.print("Enter new product quantity: ");
        int quantity = sc.nextInt();
        System.out.print("Enter new product price: ");
        double price = sc.nextDouble();

        Product product = new Product(productId, productName, quantity, price);
        if (inventory.updateProduct(product)) {
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void deleteProduct() {

        if (emptyInventory())
            return;

        System.out.print("Enter product ID to delete: ");
        int productId = sc.nextInt();
        sc.nextLine();

        Product product = new Product(productId, "", 0, 0.0);
        if (inventory.deleteProduct(product)) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void displayInventory() {

        if (emptyInventory())
            return;

        inventory.displayInventory();
    }
}
