package example.com.model.shift;

import example.com.model.order.OrderManager;
import example.com.model.person.Bartender;
import example.com.model.person.Chef;
import example.com.model.person.Employee;
import example.com.model.person.Waiter;
import example.com.util.EmployeeRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShiftTest {
    @Test
    void testShiftCreationWithMaxNumberOfEmployees() {
        // Given
        int expectedMaxAmountOfEmployees = 10;
        long expectedLengthInMs = 3600000; // 1 hour

        // When
        Shift shift = new Shift(expectedMaxAmountOfEmployees, expectedLengthInMs);

        // Then
        assertEquals(expectedMaxAmountOfEmployees, shift.getMaxAmountOfEmployees());
        assertEquals(expectedLengthInMs, shift.getLengthInMs());
    }
    
    @Test
void testShiftCreationWithZeroMaxNumberOfEmployees() {
    // Given
    int expectedMaxAmountOfEmployees = 0;
    long expectedLengthInMs = 3600000; // 1 hour

    // When
    Shift shift = null;
    try {
        shift = new Shift(expectedMaxAmountOfEmployees, expectedLengthInMs);
    } catch (IllegalArgumentException e) {
        // Expected
    }

    // Then
    assertNull(shift);
}

@Test
void testShiftCreationWithNegativeMaxNumberOfEmployees() {
    // Given
    int expectedMaxAmountOfEmployees = -1;
    long expectedLengthInMs = 3600000; // 1 hour

    // When
    Shift shift = null;
    try {
        shift = new Shift(expectedMaxAmountOfEmployees, expectedLengthInMs);
    } catch (IllegalArgumentException e) {
        // Expected
    }

    // Then
    assertNull(shift);
}

@Test
void testShiftCreationWithMaxDuration() {
    // Given
    int expectedMaxAmountOfEmployees = 10;
    long expectedLengthInMs = 86400000; // 24 hours

    // When
    Shift shift = new Shift(expectedMaxAmountOfEmployees, expectedLengthInMs);

    // Then
    assertEquals(expectedMaxAmountOfEmployees, shift.getMaxAmountOfEmployees());
    assertEquals(expectedLengthInMs, shift.getLengthInMs());
}

@Test
void testShiftCreationWithZeroDuration() {
    // Given
    int expectedMaxAmountOfEmployees = 10;
    long expectedLengthInMs = 0; // Zero duration

    // When
    Shift shift = null;
    try {
        shift = new Shift(expectedMaxAmountOfEmployees, expectedLengthInMs);
    } catch (IllegalArgumentException e) {
        // Expected
    }

    // Then
    assertNull(shift);
}

@Test
void testShiftCreationWithNegativeDuration() {
    // Given
    int expectedMaxAmountOfEmployees = 10;
    long expectedLengthInMs = -3600000; // Negative duration

    // When
    Shift shift = null;
    try {
        shift = new Shift(expectedMaxAmountOfEmployees, expectedLengthInMs);
    } catch (IllegalArgumentException e) {
        // Expected
    }

    // Then
    assertNull(shift);
}

@Test
void testAddEmployeeUntilMaxCapacity() {
    OrderManager orderManager = new OrderManager();
    // Given
    Shift shift = new Shift(3, 3600000); // 3 employees, 1 hour shift
    Employee chef = new Employee("John", "Doe", 1000, orderManager, 1000);
    Employee bartender = new Employee("Jane", "Doe", 1000, orderManager, 2000);
    Employee waiter = new Employee("Mike", "Doe", 1000, orderManager, 3000);

    // When
    String chefResult = shift.addEmployee(chef);
    String bartenderResult = shift.addEmployee(bartender);
    String waiterResult = shift.addEmployee(waiter);
    String fourthEmployeeResult = shift.addEmployee(new Employee("Sarah", "Doe", 1000, orderManager, 6000));

    // Then
    assertEquals("Employee successfully added to the shift.", chefResult);
    assertEquals("Employee successfully added to the shift.", bartenderResult);
    assertEquals("Employee successfully added to the shift.", waiterResult);
    assertEquals("Employee is over the max amount of employees.", fourthEmployeeResult);
}

@Test
void testAddEmployeeBeyondMaxCapacity() {
    OrderManager orderManager = new OrderManager();
    // Given
    Shift shift = new Shift(3, 3600000); // 2 employees, 1 hour shift
    Employee chef = new Employee("John", "Doe", 1000, orderManager, 1000);
    Employee bartender = new Employee("Jane", "Doe", 1000, orderManager, 2000);
    Employee waiter = new Employee("Mike", "Doe", 1000, orderManager, 3000);
    Employee extraEmployee = new Employee("Emma", "Doe", 1000, orderManager, 1500);

    // When
    String chefAdditionResult = shift.addEmployee(chef);
    String bartenderAdditionResult = shift.addEmployee(bartender);
    String waiterAdditionResult = shift.addEmployee(waiter);
    String extraEmployeeAdditionResult = shift.addEmployee(extraEmployee);

    // Then
    assertEquals("Employee successfully added to the shift.", chefAdditionResult);
    assertEquals("Employee successfully added to the shift.", bartenderAdditionResult);
    assertEquals("Employee successfully added to the shift.", waiterAdditionResult);
    assertEquals("Employee is over the max amount of employees.", extraEmployeeAdditionResult);
}

@Test
void testStartShiftWithAllTypesOfEmployees() {
    OrderManager orderManager = new OrderManager();
    // Given
    Shift shift = new Shift(3, 3600000); // 3 employees, 1 hour shift
    Employee chef = new Chef("John", "Doe", 1000, orderManager, 1000);
    Employee bartender = new Bartender("Jane", "Doe", 1000, orderManager, 2000);
    Employee waiter = new Waiter("Mike", "Doe", 1000, orderManager, 3000, 3);

    // When
    String startShiftResult = null;
    try {
        shift.addEmployee(chef);
        shift.addEmployee(bartender);
        shift.addEmployee(waiter);
        shift.start();
        startShiftResult = "Shift started. Employees are now working...";
    } catch (Exception e) {
        startShiftResult = e.getMessage();
    }

    // Then
    assertEquals("Shift started. Employees are now working...", startShiftResult);
    assertTrue(shift.containsAllTypesOfEmployees());
}

}
