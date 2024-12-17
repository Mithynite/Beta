import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class OrderManager {
    private BlockingDeque<Order> waitersDeque;
    private BlockingDeque<Order> chefsDeque;
    private BlockingDeque<Order> bartendersDeque;
    private static final Order POISON_PILL = new Order(null, "", "");

    public OrderManager() {
        this.waitersDeque = new LinkedBlockingDeque<>();
        this.chefsDeque = new LinkedBlockingDeque<>();
        this.bartendersDeque = new LinkedBlockingDeque<>();
    }

    public BlockingDeque<Order> getWaitersDeque() {
        return waitersDeque;
    }

    public BlockingDeque<Order> getChefsDeque() {
        return chefsDeque;
    }

    public BlockingDeque<Order> getBartendersDeque() {
        return bartendersDeque;
    }

    public void forwardToChefQueue(Order order) throws InterruptedException {
        if (order.containsFood()) {
            chefsDeque.put(order);
        }
    }

    public void forwardToBartenderQueue(Order order) throws InterruptedException {
        if (order.containsDrink()) {
            bartendersDeque.put(order);
        }
    }

    public void forwardToWaiterQueue(Order order) throws InterruptedException {
        waitersDeque.put(order);
    }

    public boolean isPoisonPill(Order order) {
        return order == POISON_PILL;
    }

    public void sendPoisonPillToAllQueues() {
        waitersDeque.offer(POISON_PILL);
        chefsDeque.offer(POISON_PILL);
        bartendersDeque.offer(POISON_PILL);
    }
}
