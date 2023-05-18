import repository.EmployeeRepository;
import repository.EmployeeRepositoryImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) {
        try {
            EmployeeRepository repository = new EmployeeRepositoryImpl();
            LocateRegistry.createRegistry(5000);
            Naming.rebind("rmi://localhost:5000/repository", repository);
            System.out.println("Server is running...");

        } catch (Exception e) {
            System.err.println("Remote Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}