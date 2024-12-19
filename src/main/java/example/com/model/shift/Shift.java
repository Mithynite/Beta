package example.com.model.shift;

import example.com.model.person.Employee;
import example.com.util.EmployeeRole;

import java.util.ArrayList;

public class Shift {
    private long lengthInMs;  // Duration of shift in hours
    private int maxAmountOfEmployees;
    private ArrayList<Thread> employeeThreadsPool;
    private ArrayList<Employee> employees; // List for tracking down the employees
    private boolean isStarted;

    public Shift(int maxAmountOfEmployees, long lengthInMs) {
        validateNumberValue(maxAmountOfEmployees);
        validateNumberValue(lengthInMs);
        this.maxAmountOfEmployees = maxAmountOfEmployees;
        this.lengthInMs = lengthInMs;
        this.employeeThreadsPool = new ArrayList<>();
        this.employees = new ArrayList<>();
        isStarted = false;
    }

    public long getLengthInMs() {
        return lengthInMs;
    }

    public int getMaxAmountOfEmployees() {
        return maxAmountOfEmployees;
    }

    public String addEmployee(Employee employee) {
        if (employeeThreadsPool.size() < maxAmountOfEmployees) {
            Thread employeeThread = new Thread(employee);
            employeeThreadsPool.add(employeeThread);
            employees.add(employee);
            return "Employee successfully added to the shift.";
        }
        return "Employee is over the max amount of employees.";
    }

    public void validateNumberValue(long value){
        if(value <= 0){
            throw new IllegalArgumentException("Invalid input!");
        }
    }

    public void start() {
        System.out.println("Shift started. Employees are now working...");
        for (Thread t : employeeThreadsPool) {
            t.start();
        }
        isStarted = true;
    }

    public void stop() {
        System.out.println("Shift is ending. Shutting down all employees...");
        for (Thread t : employeeThreadsPool) {
            t.interrupt();
        }
        employeeThreadsPool.clear();
        employees.clear();
        isStarted = false;
    }

    private boolean hasEmployeeOfRole(EmployeeRole role) {
        for (Employee employee : employees) {
            if (employee.getRole().equals(role)) { // Check the thread name for role
                return true;
            }
        }
        return false;
    }

    public boolean containsAllTypesOfEmployees() {
        boolean hasChef = hasEmployeeOfRole(EmployeeRole.Chef);
        boolean hasBartender = hasEmployeeOfRole(EmployeeRole.Bartender);
        boolean hasWaiter = hasEmployeeOfRole(EmployeeRole.Waiter);

        return hasChef && hasBartender && hasWaiter;
    }
}
