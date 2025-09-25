package EmployeeDataAnalyzer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Employee class to represent employee data
class Employee {
    private int id;
    private String name;
    private String email;
    private String department;
    private String position;
    private double salary;
    private LocalDate hireDate;

    public Employee(int id, String name, String email, String department,
                    String position, double salary, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("ID: %d | Name: %s | Email: %s | Department: %s | Position: %s | Salary: $%.2f | Hire Date: %s",
                id, name, email, department, position, salary, hireDate.format(formatter));
    }
}

// Employee Management System class
class EmployeeManagementSystem {
    private Map<Integer, Employee> employees;
    private int nextId;

    public EmployeeManagementSystem() {
        this.employees = new HashMap<>();
        this.nextId = 1;
    }

    // Add a new employee
    public boolean addEmployee(String name, String email, String department,
                               String position, double salary, LocalDate hireDate) {
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            System.out.println("Error: Name and email are required fields.");
            return false;
        }

        // Check for duplicate email
        for (Employee emp : employees.values()) {
            if (emp.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Error: Employee with this email already exists.");
                return false;
            }
        }

        Employee employee = new Employee(nextId++, name, email, department, position, salary, hireDate);
        employees.put(employee.getId(), employee);
        System.out.println("Employee added successfully with ID: " + employee.getId());
        return true;
    }

    // Update employee information
    public boolean updateEmployee(int id, String name, String email, String department,
                                  String position, Double salary, LocalDate hireDate) {
        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("Error: Employee with ID " + id + " not found.");
            return false;
        }

        // Check for duplicate email (excluding current employee)
        if (email != null && !email.equals(employee.getEmail())) {
            for (Employee emp : employees.values()) {
                if (emp.getId() != id && emp.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("Error: Another employee with this email already exists.");
                    return false;
                }
            }
        }

        // Update fields if new values are provided
        if (name != null && !name.trim().isEmpty()) employee.setName(name);
        if (email != null && !email.trim().isEmpty()) employee.setEmail(email);
        if (department != null) employee.setDepartment(department);
        if (position != null) employee.setPosition(position);
        if (salary != null) employee.setSalary(salary);
        if (hireDate != null) employee.setHireDate(hireDate);

        System.out.println("Employee updated successfully.");
        return true;
    }

    // Delete an employee
    public boolean deleteEmployee(int id) {
        Employee removed = employees.remove(id);
        if (removed == null) {
            System.out.println("Error: Employee with ID " + id + " not found.");
            return false;
        }
        System.out.println("Employee deleted successfully: " + removed.getName());
        return true;
    }

    // Find employee by ID
    public Employee findEmployeeById(int id) {
        return employees.get(id);
    }

    // Find employees by name (partial match)
    public List<Employee> findEmployeesByName(String name) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees.values()) {
            if (emp.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(emp);
            }
        }
        return result;
    }

    // Find employees by department
    public List<Employee> findEmployeesByDepartment(String department) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees.values()) {
            if (emp.getDepartment().equalsIgnoreCase(department)) {
                result.add(emp);
            }
        }
        return result;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    // Display all employees
    public void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n=== ALL EMPLOYEES ===");
        for (Employee emp : employees.values()) {
            System.out.println(emp);
        }
        System.out.println("Total employees: " + employees.size());
    }

    // Display employee statistics
    public void displayStatistics() {
        if (employees.isEmpty()) {
            System.out.println("No employees to analyze.");
            return;
        }

        Map<String, Integer> departmentCount = new HashMap<>();
        double totalSalary = 0;
        double maxSalary = Double.MIN_VALUE;
        double minSalary = Double.MAX_VALUE;

        for (Employee emp : employees.values()) {
            // Department count
            departmentCount.put(emp.getDepartment(),
                    departmentCount.getOrDefault(emp.getDepartment(), 0) + 1);

            // Salary statistics
            totalSalary += emp.getSalary();
            maxSalary = Math.max(maxSalary, emp.getSalary());
            minSalary = Math.min(minSalary, emp.getSalary());
        }

        System.out.println("\n=== EMPLOYEE STATISTICS ===");
        System.out.println("Total Employees: " + employees.size());
        System.out.println("Average Salary: $" + String.format("%.2f", totalSalary / employees.size()));
        System.out.println("Highest Salary: $" + String.format("%.2f", maxSalary));
        System.out.println("Lowest Salary: $" + String.format("%.2f", minSalary));

        System.out.println("\nEmployees by Department:");
        for (Map.Entry<String, Integer> entry : departmentCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " employees");
        }
    }
}

// Main class with console interface
public class EmployeeManagementApp {
    private static EmployeeManagementSystem ems = new EmployeeManagementSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Add some sample data
        initializeSampleData();

        System.out.println("Welcome to Employee Management System");
        System.out.println("=====================================");

        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addEmployeeMenu();
                    break;
                case 2:
                    updateEmployeeMenu();
                    break;
                case 3:
                    deleteEmployeeMenu();
                    break;
                case 4:
                    findEmployeeMenu();
                    break;
                case 5:
                    ems.displayAllEmployees();
                    break;
                case 6:
                    searchEmployeesMenu();
                    break;
                case 7:
                    ems.displayStatistics();
                    break;
                case 8:
                    System.out.println("Thank you for using Employee Management System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. Find Employee by ID");
        System.out.println("5. Display All Employees");
        System.out.println("6. Search Employees");
        System.out.println("7. Display Statistics");
        System.out.println("8. Exit");
    }

    private static void addEmployeeMenu() {
        System.out.println("\n=== ADD EMPLOYEE ===");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter department: ");
        String department = scanner.nextLine();

        System.out.print("Enter position: ");
        String position = scanner.nextLine();

        double salary = getDoubleInput("Enter salary: ");

        LocalDate hireDate = getDateInput("Enter hire date (YYYY-MM-DD): ");

        ems.addEmployee(name, email, department, position, salary, hireDate);
    }

    private static void updateEmployeeMenu() {
        System.out.println("\n=== UPDATE EMPLOYEE ===");
        int id = getIntInput("Enter employee ID to update: ");

        Employee employee = ems.findEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Current employee details:");
        System.out.println(employee);
        System.out.println("\nEnter new values (press Enter to keep current value):");

        System.out.print("Name [" + employee.getName() + "]: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) name = null;

        System.out.print("Email [" + employee.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) email = null;

        System.out.print("Department [" + employee.getDepartment() + "]: ");
        String department = scanner.nextLine();
        if (department.trim().isEmpty()) department = null;

        System.out.print("Position [" + employee.getPosition() + "]: ");
        String position = scanner.nextLine();
        if (position.trim().isEmpty()) position = null;

        System.out.print("Salary [" + employee.getSalary() + "]: ");
        String salaryStr = scanner.nextLine();
        Double salary = null;
        if (!salaryStr.trim().isEmpty()) {
            try {
                salary = Double.parseDouble(salaryStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format. Keeping current value.");
            }
        }

        System.out.print("Hire Date [" + employee.getHireDate() + "] (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate hireDate = null;
        if (!dateStr.trim().isEmpty()) {
            try {
                hireDate = LocalDate.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid date format. Keeping current value.");
            }
        }

        ems.updateEmployee(id, name, email, department, position, salary, hireDate);
    }

    private static void deleteEmployeeMenu() {
        System.out.println("\n=== DELETE EMPLOYEE ===");
        int id = getIntInput("Enter employee ID to delete: ");

        Employee employee = ems.findEmployeeById(id);
        if (employee != null) {
            System.out.println("Employee to delete:");
            System.out.println(employee);
            System.out.print("Are you sure? (y/N): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                ems.deleteEmployee(id);
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }

    private static void findEmployeeMenu() {
        System.out.println("\n=== FIND EMPLOYEE ===");
        int id = getIntInput("Enter employee ID: ");

        Employee employee = ems.findEmployeeById(id);
        if (employee != null) {
            System.out.println("Employee found:");
            System.out.println(employee);
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void searchEmployeesMenu() {
        System.out.println("\n=== SEARCH EMPLOYEES ===");
        System.out.println("1. Search by name");
        System.out.println("2. Search by department");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                System.out.print("Enter name to search: ");
                String name = scanner.nextLine();
                List<Employee> byName = ems.findEmployeesByName(name);
                displaySearchResults(byName, "name containing '" + name + "'");
                break;
            case 2:
                System.out.print("Enter department: ");
                String department = scanner.nextLine();
                List<Employee> byDept = ems.findEmployeesByDepartment(department);
                displaySearchResults(byDept, "department '" + department + "'");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void displaySearchResults(List<Employee> employees, String criteria) {
        if (employees.isEmpty()) {
            System.out.println("No employees found with " + criteria);
        } else {
            System.out.println("\nEmployees found with " + criteria + ":");
            for (Employee emp : employees) {
                System.out.println(emp);
            }
            System.out.println("Total found: " + employees.size());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Please enter date in YYYY-MM-DD format.");
            }
        }
    }

    private static void initializeSampleData() {
        // Add some sample employees
        ems.addEmployee("John Doe", "john.doe@company.com", "Engineering", "Software Developer", 75000, LocalDate.of(2020, 3, 15));
        ems.addEmployee("Jane Smith", "jane.smith@company.com", "Marketing", "Marketing Manager", 68000, LocalDate.of(2019, 7, 10));
        ems.addEmployee("Bob Johnson", "bob.johnson@company.com", "Engineering", "Senior Developer", 85000, LocalDate.of(2018, 1, 20));
        ems.addEmployee("Alice Brown", "alice.brown@company.com", "HR", "HR Specialist", 55000, LocalDate.of(2021, 5, 8));
        ems.addEmployee("Charlie Wilson", "charlie.wilson@company.com", "Finance", "Financial Analyst", 62000, LocalDate.of(2020, 9, 12));
    }
}