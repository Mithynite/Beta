package example.com.model.person;

public class Customer extends Person {

    public Customer(String name, String surname) {
        super(name, surname);
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
