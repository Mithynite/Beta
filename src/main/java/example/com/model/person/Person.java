package example.com.model.person;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a person with a unique identifier, name, and surname.
 */
public class Person {
    protected static final AtomicInteger idGenerator = new AtomicInteger(0);
    protected int id;
    protected String name;
    protected String surname;

    /**
     * Constructs a new Person object with a unique identifier, validated name, and validated surname.
     *
     * @param name  The person's name. Must not be null or empty, and must contain only letters.
     * @param surname  The person's surname. Must not be null or empty, and must contain only letters.
     */
    public Person(String name, String surname) {
        this.id = idGenerator.incrementAndGet();
        this.name = validateName(name, "Name");
        this.surname = validateName(surname, "Surname");
    }

    /**
     * Validates the input name or surname.
     *
     * @param input  The input name or surname to be validated.
     * @param fieldName  The name of the field being validated (either "Name" or "Surname").
     * @return  The trimmed input if it is valid.
     * @throws IllegalArgumentException  If the input is null, empty, or contains non-letter characters.
     */
    private String validateName(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        if (!input.matches("^[A-Za-z]+$")) {
            throw new IllegalArgumentException(fieldName + " must contain only letters!");
        }
        String lowercasedInput = input.charAt(0) + input.substring(1).toLowerCase();
        return lowercasedInput.trim();
    }

    /**
     * Returns the person's name.
     *
     * @return  The person's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the person's surname.
     *
     * @return  The person's surname.
     */
    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }
}
