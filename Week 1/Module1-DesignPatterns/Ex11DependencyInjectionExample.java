import java.util.List;
import java.util.ArrayList;

// Repository Interface
interface CustomerRepository {
    abstract void findCustomerById(int id);
}

// Concrete Repository
class CustomerRepositoryImpl implements CustomerRepository {

    private List<Customer> customers;

    public CustomerRepositoryImpl() {
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void findCustomerById(int id) {
        for (Customer customer : customers) {
            if(customer.getId() == id){
                customer.showInfo();
                return;
            }
        }

        System.out.println();
        System.out.println("No Such Customer found");
        System.out.println();
    }
}

// Service Class
class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void findCustomer(int id){
        customerRepository.findCustomerById(id);
    }
}

class Customer {
    private final int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void showInfo() {
        System.out.println();
        System.out.println("Customer Id: " + id);
        System.out.println("Customer Name: " + name);
        System.out.println();
    }
}

public class Ex11DependencyInjectionExample {
    public static void main(String[] args) {
        CustomerRepositoryImpl repo = new CustomerRepositoryImpl();
        CustomerService service = new CustomerService(repo);

        repo.addCustomer(new Customer(1, "John"));
        repo.addCustomer(new Customer(2, "Susan"));
        repo.addCustomer(new Customer(3, "Alice"));
        repo.addCustomer(new Customer(4, "Bob"));
        repo.addCustomer(new Customer(5, "Tyler"));

        service.findCustomer(4);
        service.findCustomer(10);
    }
}
