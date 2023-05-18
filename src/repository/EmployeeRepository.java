package repository;

import model.Employee;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EmployeeRepository extends Remote {

    Employee findById(Long id) throws RemoteException, Exception;

    List<Employee> findAll() throws RemoteException, Exception;

    Long create(Employee employee) throws RemoteException, Exception;

    boolean update(Employee employee) throws RemoteException, Exception;

    boolean deleteById(Long id) throws RemoteException, Exception;

    void deleteAll() throws RemoteException, Exception;

}
