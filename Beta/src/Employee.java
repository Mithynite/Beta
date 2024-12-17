import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Employee extends Person implements Runnable {
    protected int salary;
    protected OrderManager orderManager;
    protected static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public Employee(String name, String surname, int salary, OrderManager orderManager) {
        super(name, surname);
        this.salary = validateSalary(salary);
        this.orderManager = orderManager;
    }

    public String processOrders() throws InterruptedException{
        return null;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() && isRunning.get()) {
                System.out.println(processOrders());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(name + " was interrupted.");
        }
    }

    public void sleep(long timeInMilliseconds) throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Thread was interrupted before sleep.");
        }
        Thread.sleep(timeInMilliseconds);
    }

    public boolean checkForPoisonPill(Order order) {
        return orderManager.isPoisonPill(order);
    }

    private int validateSalary(int salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive integer!");
        }
        return salary;
    }
}
