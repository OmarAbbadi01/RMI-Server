package repository;

import model.Employee;
import model.Gender;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl extends UnicastRemoteObject implements EmployeeRepository {

    private final Connection connection;

    public EmployeeRepositoryImpl() throws RemoteException {
        super();
        this.connection = MySqlConnectionFactory.getInstance().getConnection();
    }

    @Override
    public Employee findById(Long employeeID) throws RemoteException, Exception {
        String sql = "SELECT * FROM employee WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setLong(1, employeeID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String department = resultSet.getString("department");
            Gender gender = Gender.valueOf(resultSet.getString("gender"));
            Integer age = resultSet.getInt("age");
            Year year = Year.of(resultSet.getInt("year"));
            return new Employee(id, name, department, gender, age, year);
        }
        return null;
    }

    @Override
    public List<Employee> findAll() throws RemoteException, Exception {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String department = resultSet.getString("department");
            Gender gender = Gender.valueOf(resultSet.getString("gender"));
            Integer age = resultSet.getInt("age");
            Year year = Year.of(resultSet.getInt("year"));

            Employee employee = new Employee(id, name, department, gender, age, year);
            employees.add(employee);
        }
        return employees;
    }

@Override
    public Long create(Employee employee) throws RemoteException, Exception {
        System.out.println("Called");
        String sql = "INSERT INTO employee (id, name, department, gender, age, year) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setLong(1, employee.getId());
        statement.setString(2, employee.getName());
        statement.setString(3, employee.getDepartment());
        statement.setString(4, employee.getGender().toString());
        statement.setInt(5, employee.getAge());
        statement.setInt(6, employee.getYear().getValue());

        int affectedRows = statement.executeUpdate();

        if (affectedRows > 0) {
            return this.findById(employee.getId()).getId();
        }
        return null;
    }


    @Override
    public boolean update(Employee employee) throws RemoteException, Exception {
        String sql = "UPDATE employee SET name = ?, department = ?, gender = ?, age = ?, year = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, employee.getName());
        statement.setString(2, employee.getDepartment());
        statement.setString(3, employee.getGender().toString());
        statement.setInt(4, employee.getAge());
        statement.setInt(5, employee.getYear().getValue());
        statement.setLong(6, employee.getId());

        int affectedRows = statement.executeUpdate();

        return affectedRows != 0;
    }

    @Override
    public boolean deleteById(Long id) throws RemoteException, Exception {
        String sql = "DELETE FROM employee WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        int affectedRows = statement.executeUpdate();
        return affectedRows != 0;
    }

    @Override
    public void deleteAll() throws RemoteException, Exception {
        String sql = "DELETE FROM employee WHERE 1 = 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }
}
