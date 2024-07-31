import java.util.ArrayList;
import java.util.Scanner;

class Order {
    private final int OrderId;
    private String customerName;
    private double totalPrice;

    Order(int OrderId, String customerName, double totalPrice) {
        this.OrderId = OrderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    int getOrderId() {
        return OrderId;
    }

    String getCustomerName() {
        return customerName;
    }

    double getTotalPrice() {
        return totalPrice;
    }

    void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

class CustomerOrders {
    private ArrayList<Order> customerOrder;

    CustomerOrders() {
        customerOrder = new ArrayList<>();
    }

    void addOrder(Order order) {
        customerOrder.add(order);
    }

    boolean isEmpty() {
        return customerOrder.isEmpty();
    }

    void displayOrders() {

        if (customerOrder.isEmpty()) {
            System.out.println("\nNo orders to display.\n");

            return;
        }

        System.out.println("_______________________________________________________________");
        System.out.printf("| %-10s | %-20s | %-10s |\n", "Order ID", "Customer Name", "Total Price");
        System.out.println("|____________|______________________|______________|");

        for (Order order : customerOrder) {
            System.out.printf("| %-10d | %-20s | %-12.2f |\n",
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getTotalPrice());
        }
        System.out.println("_______________________________________________________________");
    }

    Order[] bubbleSortOrders() {
        Order[] ordersCopy = customerOrder.toArray(new Order[0]);

        int n = ordersCopy.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ordersCopy[j].getTotalPrice() > ordersCopy[j + 1].getTotalPrice()) {
                    Order temp = ordersCopy[j];
                    ordersCopy[j] = ordersCopy[j + 1];
                    ordersCopy[j + 1] = temp;
                }
            }
        }

        return ordersCopy;
    }

    Order[] quickSortOrders() {
        Order[] ordersCopy = customerOrder.toArray(new Order[0]);

        quickSort(ordersCopy, 0, ordersCopy.length - 1);

        return ordersCopy;
    }

    private void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);
            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    private int partition(Order[] orders, int low, int high) {
        Order pivot = orders[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot.getTotalPrice()) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }

        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }
}

public class Ex3SortingCustomerOrders {
    private static CustomerOrders customerOrders = new CustomerOrders();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = sc.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    addOrder();
                    break;
                case 2:
                    displayOrders();
                    break;
                case 3:
                    sortOrders();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
        sc.close();
        System.out.println("Exiting the application.... Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n=============================================\n");
        System.out.println("Customer Order Management");
        System.out.println("\n=============================================\n");
        System.out.println("1. Add Order");
        System.out.println("2. Display All Orders");
        System.out.println("3. Sort Orders by Total Price");
        System.out.println("4. Quit");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    private static void addOrder() {
        System.out.print("Enter order ID: ");
        int orderId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();
        System.out.print("Enter total price: ");
        double totalPrice = sc.nextDouble();

        Order order = new Order(orderId, customerName, totalPrice);
        customerOrders.addOrder(order);
        System.out.println("Order added successfully.");
    }

    private static void displayOrders() {

        customerOrders.displayOrders();

    }

    private static void sortOrders() {

        if (customerOrders.isEmpty()) {
            System.out.println("\nNo orders to sort.\n");
            return;
        }

        System.out.println("Choose sorting method:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Quick Sort");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        Order[] sortedOrders;
        if (choice == 1) {
            sortedOrders = customerOrders.bubbleSortOrders();
        } else if (choice == 2) {
            sortedOrders = customerOrders.quickSortOrders();
        } else {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        System.out.println("Orders sorted successfully. Displaying sorted orders:");
        displaySortedOrders(sortedOrders);
    }

    private static void displaySortedOrders(Order[] orders) {
        System.out.println("_______________________________________________________________");
        System.out.printf("| %-10s | %-20s | %-10s |\n", "Order ID", "Customer Name", "Total Price");
        System.out.println("|____________|______________________|____________|");

        for (Order order : orders) {
            System.out.printf("| %-10d | %-20s | %-10.2f |\n",
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getTotalPrice());
        }
        System.out.println("_______________________________________________________________");
    }
}
