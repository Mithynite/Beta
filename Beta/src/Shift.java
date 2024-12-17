import java.util.ArrayList;

public class Shift {
    private int length;  // Duration of shift in hours
    private int maxAmountOfEmployees;
    private ArrayList<Thread> employeeThreadsPool;

    public Shift(int maxAmountOfEmployees, int length) {
        this.maxAmountOfEmployees = maxAmountOfEmployees;
        this.length = length;
        this.employeeThreadsPool = new ArrayList<>();  // Ensure the list is initialized
    }

    public String addEmployee(Employee employee) {
        if (employeeThreadsPool.size() < maxAmountOfEmployees) {
            Thread employeeThread = new Thread(employee);
            employeeThreadsPool.add(employeeThread);
            return "Employee successfully added to the shift.";
        }
        return "Employee is over the max amount of employees.";
    }

    public void start() {
        System.out.println("Shift started. Employees are now working...");
        for (Thread t : employeeThreadsPool) {
            t.start();
        }
    }

    public void stop() {
        System.out.println("Shift is ending. Shutting down all employees...");
        for (Thread t : employeeThreadsPool) {
            t.interrupt();
        }
        employeeThreadsPool.clear();
    }
}
