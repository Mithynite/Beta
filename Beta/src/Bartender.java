import java.util.Date;

public class Bartender extends Employee{

    public Bartender(String name, String surname, int salary, OrderManager orderManager) {
        super(name, surname, salary, orderManager);
    }

    @Override
    public String processOrders() throws InterruptedException {
        Order newOrder = orderManager.getBartendersDeque().take();
        if(checkForPoisonPill(newOrder)){
            isRunning.set(false);
            return "Bartender " + name + ": POISON PILL consumed!";
        }
        System.out.println("Bartender " + name + " is preparing drink(s) for Order #" + newOrder.getId());
        sleep(1500);
        newOrder.setDrinkReady();
        orderManager.forwardToWaiterQueue(newOrder);
        return "Bartender " + name + " finished preparing drink(s) for Order #" + newOrder.getId();
    }
}
