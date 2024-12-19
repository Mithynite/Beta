package example.com.app;

import example.com.model.person.Bartender;
import example.com.model.person.Chef;
import example.com.model.order.OrderManager;
import example.com.model.person.Employee;
import example.com.model.person.Waiter;
import example.com.util.EmployeeRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantConsoleTest {
    private RestaurantConsole restaurantConsole;
    private OrderManager orderManager;

    @BeforeEach
    public void setUp() {
        restaurantConsole = new RestaurantConsole();
        orderManager = new OrderManager();
    }

    @Test
    public void testAddingNewChefWithInvalidSalaryInput() {
        int invalidSalary = -1000; // Invalid salary input

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Chef chef = new Chef("John", "Doe", invalidSalary, orderManager, 5000);
        });

        String expectedMessage = "Salary must be a positive integer!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testAddNewChefWithInvalidOrderPreparationTime() {
        int invalidOrderPreparationTime = -1000;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Chef("John", "Doe", 3000, orderManager, invalidOrderPreparationTime);
        });

        String expectedMessage = "Order preparation time must be a positive integer!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testAddNewBartenderWithInvalidSalaryInput() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Bartender bartender = new Bartender("John", "Doe", -500, orderManager, 1000);
        });

        assertEquals("Salary must be a positive integer!", exception.getMessage());
    }

}