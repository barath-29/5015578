import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class EcomProduct {
    private final int productId;
    private String productName;
    private String category;

    EcomProduct(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    int getProductId() {
        return productId;
    }

    String getProductName() {
        return productName;
    }

    String getCategory() {
        return category;
    }

    void setProductName(String productName) {
        this.productName = productName;
    }

    void setCategory(String category) {
        this.category = category;
    }
}

class Inventory {
    private ArrayList<EcomProduct> inventory;

    Inventory() {
        this.inventory = new ArrayList<>();
    }

    boolean isEmpty() {
        return inventory.isEmpty();
    }

    boolean addProduct(EcomProduct product) {
        inventory.add(product);
        return true;
    }

    int linearSearch(int productId) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getProductId() == productId) {
                return i;
            }
        }
        return -1;
    }

    int binarySearch(int productId) {
        ArrayList<EcomProduct> inventoryCopy = new ArrayList<>(inventory);
        Collections.sort(inventoryCopy, (p1, p2) -> Integer.compare(p1.getProductId(), p2.getProductId()));

        int start = 0, end = inventoryCopy.size() - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            int midProductId = inventoryCopy.get(mid).getProductId();

            if (midProductId == productId) {
                return mid;
            } else if (midProductId > productId) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    void displayInventory() {
        System.out.println("_______________________________________________________________");
        System.out.printf("| %-10s | %-20s | %-20s |\n", "Product ID", "Product Name", "Category");
        System.out.println("|____________|______________________|______________________|");

        for (EcomProduct product : inventory) {
            System.out.printf("| %-10d | %-20s | %-20s |\n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getCategory());
        }
        System.out.println("_______________________________________________________________");
    }

    void displayProductDetails(int index) {
        EcomProduct product = inventory.get(index);
        System.out.println("\nProduct Details:");
        System.out.println("ID: " + product.getProductId());
        System.out.println("Name: " + product.getProductName());
        System.out.println("Category: " + product.getCategory());
    }
}

public class Ex2ECommercePlatformSearch {
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
                    searchProductById();
                    break;
                case 3:
                    displayInventory();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while(choice != 4);

        sc.close();
        System.out.println("Exiting the application.... Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n=============================================\n");
        System.out.println("E-Commerce Platform Product Search");
        System.out.println("\n=============================================\n");
        System.out.println("1. Add Product");
        System.out.println("2. Search Product by ID");
        System.out.println("3. Display All Products");
        System.out.println("4. Quit");
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
        System.out.print("Enter product category: ");
        String category = sc.nextLine();

        EcomProduct product = new EcomProduct(productId, productName, category);
        if (inventory.addProduct(product)) {
            System.out.println("\nProduct added successfully.");
        } else {
            System.out.println("\nFailed to add product.");
        }
    }

    private static void searchProductById() {
        if (emptyInventory())
            return;

        System.out.print("Enter product ID to search: ");
        int productId = sc.nextInt();

        System.out.println("\nChoose search method:");
        System.out.println("1. Linear Search");
        System.out.println("2. Binary Search");
        System.out.print("Enter your choice: ");
        int searchChoice = sc.nextInt();
        System.out.println();

        int index = -1;

        switch (searchChoice) {
            case 1:
                index = inventory.linearSearch(productId);
                break;
            case 2:
                index = inventory.binarySearch(productId);
                break;
            default:
                System.out.println("\nInvalid choice. Defaulting to Linear Search.");
                index = inventory.linearSearch(productId);
                break;
        }

        if (index != -1) {
            inventory.displayProductDetails(index);
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
