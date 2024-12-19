package example.com.model.order;

import example.com.model.person.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {
    @Test
    void orderWithFoodAndDrinkShouldBeReadyWhenBothFoodAndDrinkAreMarkedAsReady() {
        // Given
        Customer customer = new Customer("John", "Doe");
        Order order = new Order(customer, "Pizza", "Coke");

        // When
        order.setFoodReady();
        order.setDrinkReady();

        // Then
        assertTrue(order.isReady());
    }
    @Test
    void orderWithFoodOnlyShouldBeReadyWhenFoodIsMarkedAsReadyAndDrinkIsNotProvided() {
        // Given
        Customer customer = new Customer("John", "Doe");
        Order order = new Order(customer, "Pizza", "");
    
        // When
        order.setFoodReady();

        // Then
        assertTrue(order.isReady());
    }
    @Test
    void orderWithDrinkOnlyShouldBeReadyWhenDrinkIsMarkedAsReadyAndFoodIsNotProvided() {
    // Given
    Customer customer = new Customer("John", "Doe");
    Order order = new Order(customer, "", "Tea");

    // When
    order.setDrinkReady();

    // Then
    assertTrue(order.isReady());
}

@Test
void orderWithFoodAndDrinkShouldNotBeReadyWhenFoodIsNotMarkedAsReady() {
    // Given
    Customer customer = new Customer("John", "Doe");
    Order order = new Order(customer, "Pizza", "Coke");

    // When
    // No action is performed on the food status

    // Then
    assertFalse(order.isReady());
}
@Test
void orderWithFoodAndDrinkShouldNotBeReadyWhenDrinkIsNotMarkedAsReady() {
    // Given
    Customer customer = new Customer("John", "Doe");
    Order order = new Order(customer, "Pizza", "Coke");

    // When
    order.setFoodReady();
    // No action is performed on the drink status

    // Then
    assertFalse(order.isReady());
}

@Test
void orderWithFoodOnlyShouldNotBeReadyWhenFoodIsNotProvided() {
    // Given
    Customer customer = new Customer("John", "Doe");
    Order order = new Order(customer, "", "Tea");

    // When
    // No action is performed on the food status

    // Then
    assertFalse(order.isReady());
}
@Test
void orderWithDrinkOnlyShouldNotBeReadyWhenDrinkIsNotProvided() {
    // Given
    Customer customer = new Customer("John", "Doe");
    Order order = new Order(customer, "", "Tea");

    // When
    // No action is performed on the drink status

    // Then
    assertFalse(order.isReady());
}
}