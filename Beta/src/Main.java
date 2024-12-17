import java.util.Date;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        RestaurantConsole console = new RestaurantConsole();
        console.start();

        OrderManager orderManager = new OrderManager();

        int maxOrdersPerWaiter = 3; // Maximum number of orders a waiter can handle at once
        Waiter waiter = new Waiter("Alice", "Smith", new Date(), 2500, maxOrdersPerWaiter, orderManager);
        Chef chef = new Chef("Bob", "Johnson", new Date(), 3000, orderManager);
        Bartender bartender = new Bartender("Charlie", "Williams", new Date(), 2800, orderManager);

        Thread waiterThread = new Thread(waiter);
        Thread chefThread = new Thread(chef);
        Thread bartenderThread = new Thread(bartender);

        waiterThread.start();
        chefThread.start();
        bartenderThread.start();

        Customer customer1 = new Customer("John", "Doe");
        Customer customer2 = new Customer("Jane", "Roe");
        Customer customer3 = new Customer("Jake", "Lee");

        Order order1 = new Order(customer1, "Pizza", "Coke");
        Order order2 = new Order(customer2, "Burger", "");
        Order order3 = new Order(customer3, "", "Beer");

        orderManager.forwardToWaiterQueue(order1);
        orderManager.forwardToWaiterQueue(order2);
        orderManager.forwardToWaiterQueue(order3);

        try {
            Thread.sleep(20000); // Simulate 15 seconds of runtime
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        orderManager.sendPoisonPillToAllQueues();

        try {
            waiterThread.join();
            chefThread.join();
            bartenderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulation complete. Program terminated cleanly.");
    }
}
