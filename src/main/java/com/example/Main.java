package com.example;

import com.example.dao.EmployeeDAO;
import org.jdbi.v3.core.Jdbi;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = DatabaseConnection.getJdbi();

        EmployeeDAO employeeDAO = jdbi.onDemand(EmployeeDAO.class);

        // CRUD операції
        System.out.println("Adding new employee...");
        employeeDAO.addEmployee("Doe", "John", "Developer");
        System.out.println("Employee added successfully.\n");

        System.out.println("All Employees:");
        employeeDAO.getAllEmployees().forEach(System.out::println);
        System.out.println();

        System.out.println("Updating employee with ID 1...");
        employeeDAO.updateEmployee(1, "Senior Developer");
        System.out.println("Employee updated successfully.\n");

        System.out.println("Deleting employee with ID 2...");
        employeeDAO.deleteEmployeeWithDependencies(2);
        System.out.println("Employee deleted successfully.\n");

        // Запити
        System.out.println("Order Details:");
        employeeDAO.getOrderDetails().forEach(System.out::println);
        System.out.println();

        System.out.println("Employees in USA:");
        employeeDAO.getEmployeesByCountry("USA").forEach(System.out::println);
        System.out.println();

        System.out.println("Total Orders:");
        System.out.println(employeeDAO.getOrderCount());
        System.out.println();
    }
}
