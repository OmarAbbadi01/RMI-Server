package repository;

import model.Employee;

import java.util.List;

public interface EmployeeRepository {

    Employee findById(Long id) throws Exception;

    List<Employee> findAll() throws Exception;

    Long create(Employee employee) throws Exception;

    boolean update(Employee employee) throws Exception;

    boolean deleteById(Long id) throws Exception;

    void deleteAll() throws Exception;

}
