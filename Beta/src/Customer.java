import java.util.concurrent.atomic.AtomicInteger;

public class Customer {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private int id;
    private String name;
    private String surname;

    public Customer(String name, String surname) {
        this.id = idGenerator.incrementAndGet();
        this.name = name;
        this.surname = surname;
    }

    public Order createOrder(String food, String drink) {
        return new Order(this, food, drink);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
