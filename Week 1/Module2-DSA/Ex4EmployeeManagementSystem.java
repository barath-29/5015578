import java.util.Scanner;

class Employee {
    private final int employeeId;
    private String name;
    private String position;
    private double salary;

    Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    int getEmployeeId() {
        return employeeId;
    }

    void getInfo() {
        System.out.println();
        System.out.println("Employee Id: " + employeeId);
        System.out.println("Name:     " + name);
        System.out.println("Position: " + position);
        System.out.println("Salary:   " + salary);
        System.out.println();
    }
}

class EmployeeRecords {
    private Employee[] employees;
    private int size;
    private int n;

    EmployeeRecords(int size) {
        this.employees = new Employee[size];
        this.size = size;
        this.n = 0;
    }

    public void add(Employee employee, int index) {
        if (size == n) {
            System.out.println("Array is Full");
            return;
        }

        if (index < 0 || index > n) {
            System.out.println("Invalid index");
            return;
        }

        for (int i = n - 1; i >= index; i++) {
            employees[i + 1] = employees[i];
        }
        employees[index] = employee;
        n++;
    }

    public int search(int employeeId) {
        if (n == 0) {
            System.out.println("Array is Empty");
            return -1;
        }

        for (int i = 0; i < n; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                employees[i].getInfo();

                return i;
            }
        }

        return -1;
    }

    public void traverse() {
        if (n == 0) {
            System.out.println("Array is Empty");
            return;
        }

        for (int i = 0; i < n; i++) {
            employees[i].getInfo();
        }

    }

    public void delete(int index) {
        if (n == 0) {
            System.out.println("Array is Empty");
            return;
        }

        if (index < 0 || index > n) {
            System.out.println("Invalid index");
            return;
        }

        for (int i = index + 1; i < n; i++) {
            employees[i - 1] = employees[i];
        }

        n--;
    }

}

public class Ex4EmployeeManagementSystem {
    private static EmployeeRecords records = new EmployeeRecords(10);
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = sc.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    searchEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    displayAllEmployees();
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
        System.out.println("Employee Management System");
        System.out.println("\n=============================================\n");
        System.out.println("1. Add Employee");
        System.out.println("2. Search Employee by ID");
        System.out.println("3. Delete Employee");
        System.out.println("4. Display All Employees");
        System.out.println("5. Quit");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    private static void addEmployee() {
        System.out.print("Enter employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter position: ");
        String position = sc.nextLine();
        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();

        System.out.print("Enter index to insert the employee: ");
        int index = sc.nextInt();

        Employee employee = new Employee(employeeId, name, position, salary);
        records.add(employee, index);
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        int employeeId = sc.nextInt();
        int index = records.search(employeeId);

        if (index == -1) {
            System.out.println("Employee not found.");
        } else {
            System.out.println("Index: " + index);
        }
    }

    private static void deleteEmployee() {
        System.out.print("Enter index of employee to delete: ");
        int index = sc.nextInt();
        records.delete(index);
    }

    private static void displayAllEmployees() {
        records.traverse();
    }
}
