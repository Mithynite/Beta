import java.util.concurrent.atomic.AtomicInteger;

public abstract class Person {
    protected static final AtomicInteger idGenerator = new AtomicInteger(0);
    protected int id;
    protected String name;
    protected String surname;

    public Person(String name, String surname) {
        this.id = idGenerator.incrementAndGet();
        this.name = validateName(name, "Name");
        this.surname = validateName(surname, "Surname");
    }

    private String validateName(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        if (!input.matches("^[A-Za-z]+$")) {
            throw new IllegalArgumentException(fieldName + " must contain only letters!");
        }
        return input.trim();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
