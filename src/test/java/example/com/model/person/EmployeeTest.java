package example.com.model.person;

import example.com.model.order.Order;
import example.com.model.order.OrderManager;
import example.com.util.EmployeeRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
@Test
void validateSalaryField() {
    int expectedSalary = 1000;
    Employee employee = new Employee("John", "Doe", expectedSalary, new OrderManager(), 1000);
    int actualSalary = employee.salary;
    assertEquals(expectedSalary, actualSalary);

    assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", 0, new OrderManager(), 1000));
    assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", -1, new OrderManager(), 1000));
}
@Test
void validateNameAndSurnameFields() {
    String expectedName = "John";
    String expectedSurname = "Doe";
    Employee employee = new Employee(expectedName, expectedSurname, 1000, new OrderManager(), 1000);
    String actualName = employee.getName();
    String actualSurname = employee.getSurname();
    assertEquals(expectedName, actualName);
    assertEquals(expectedSurname, actualSurname);

    assertThrows(IllegalArgumentException.class, () -> new Employee(null, expectedSurname, 1000, new OrderManager(), 1000));
    assertThrows(IllegalArgumentException.class, () -> new Employee(expectedName, null, 1000, new OrderManager(), 1000));

    assertThrows(IllegalArgumentException.class, () -> new Employee("", expectedSurname, 1000, new OrderManager(), 1000));
    assertThrows(IllegalArgumentException.class, () -> new Employee(expectedName, "", 1000, new OrderManager(), 1000));
}
@Test
void validateOrderManagerField() {
    // Given
    OrderManager expectedOrderManager = new OrderManager();
    Employee employee = new Employee("John", "Doe", 1000, expectedOrderManager, 1000);

    // When
    OrderManager actualOrderManager = employee.orderManager;

    // Then
    assertEquals(expectedOrderManager, actualOrderManager);

    // Test with null OrderManager
    assertThrows(NullPointerException.class, () -> new Employee("John", "Doe", 1000, null, 1000));
}

@Test
void testEmployeeOrderPreparationTime() {
    // Given
    long expectedOrderPreparationTime = 1000; // 1 second
    OrderManager orderManager = new OrderManager();
    Employee employee = new Employee("John", "Doe", 1000, orderManager, expectedOrderPreparationTime);

    // When
    long actualOrderPreparationTime = employee.orderPreparationTime;

    // Then
    assertEquals(expectedOrderPreparationTime, actualOrderPreparationTime);

    // Test with zero order preparation time
    assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", 1000, orderManager, 0));

    // Test with negative order preparation time
    assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", 1000, orderManager, -1));
}

@Test
void testGetRole() {
    // Given
    String name = "John";
    String surname = "Doe";
    int salary = 1000;
    OrderManager orderManager = new OrderManager();
    long orderPreparationTime = 1000;
    EmployeeRole expectedRole = EmployeeRole.Chef;
    Employee employee = new Employee(name, surname, salary, orderManager, orderPreparationTime);
    employee.role = expectedRole;

    // When
    EmployeeRole actualRole = employee.getRole();

    // Then
    assertEquals(expectedRole, actualRole);
}

@Test
void testEmployeeSleepMethod() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    Employee employee = new Employee("John", "Doe", 1000, orderManager, 1000);
    Thread employeeThread = new Thread(employee);
    employeeThread.start();

    // When
    long startTime = System.currentTimeMillis();
    employee.sleep();
    long endTime = System.currentTimeMillis();

    // Then
    long expectedSleepTime = employee.orderPreparationTime;
    long actualSleepTime = endTime - startTime;
    assertTrue(Math.abs(actualSleepTime - expectedSleepTime) <= 100); // Allowing a 100ms deviation due to thread scheduling
}

@Test
void testCheckForPoisonPillMethodWithPoisonPillOrder() {
    // Given
    OrderManager orderManager = new OrderManager();
    Order poisonPill = OrderManager.POISON_PILL;
    Employee employee = new Employee("John", "Doe", 1000, orderManager, 1000);

    // When
    boolean isPoisonPill = employee.checkForPoisonPill(poisonPill);

    // Then
    assertTrue(isPoisonPill);
}

}