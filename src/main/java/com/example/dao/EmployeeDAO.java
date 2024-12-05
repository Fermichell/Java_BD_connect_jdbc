package com.example.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.example.model.Employee;

import java.util.List;
import java.util.Map;

@RegisterBeanMapper(Employee.class)
public interface EmployeeDAO {

    @SqlUpdate("INSERT INTO Employees (LastName, FirstName, Title) VALUES (:lastName, :firstName, :title)")
    void addEmployee(@Bind("lastName") String lastName, @Bind("firstName") String firstName, @Bind("title") String title);

    @SqlQuery("SELECT * FROM Employees")
    List<Employee> getAllEmployees();

    @SqlUpdate("UPDATE Employees SET Title = :title WHERE EmployeeID = :employeeID")
    void updateEmployee(@Bind("employeeID") int employeeID, @Bind("title") String title);    

    @SqlUpdate("DELETE FROM `Order Details` WHERE OrderID = :orderID")
    void deleteOrderDetailsByOrder(@Bind("orderID") int orderID);

    @SqlUpdate("DELETE FROM Orders WHERE EmployeeID = :employeeID")
    void deleteOrdersByEmployee(@Bind("employeeID") int employeeID);

    @SqlUpdate("DELETE FROM Employees WHERE EmployeeID = :employeeID")
    void deleteEmployee(@Bind("employeeID") int employeeID);

    default void deleteEmployeeWithDependencies(int employeeID) {
        // Отримання списку замовлень працівника
        List<Integer> orderIDs = getOrdersByEmployee(employeeID);

        // Видалення деталей замовлень
        for (int orderID : orderIDs) {
            deleteOrderDetailsByOrder(orderID);
        }

        // Видалення замовлень
        deleteOrdersByEmployee(employeeID);

        // Видалення працівника
        deleteEmployee(employeeID);
    }

    @SqlQuery("SELECT OrderID FROM Orders WHERE EmployeeID = :employeeID")
    List<Integer> getOrdersByEmployee(@Bind("employeeID") int employeeID);

    @SqlQuery("SELECT o.OrderID, e.FirstName AS EmployeeName, c.CompanyName AS CustomerName, o.OrderDate FROM Orders o JOIN Employees e ON o.EmployeeID = e.EmployeeID JOIN Customers c ON o.CustomerID = c.CustomerID")
    List<Map<String, Object>> getOrderDetails();

    @SqlQuery("SELECT * FROM Employees WHERE Country = :country")
    List<Employee> getEmployeesByCountry(@Bind("country") String country);


    @SqlQuery("SELECT COUNT(*) FROM Orders")
    int getOrderCount();
}

