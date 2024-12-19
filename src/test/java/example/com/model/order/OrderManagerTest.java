package example.com.model.order;

import static org.junit.jupiter.api.Assertions.*;
import example.com.model.person.Customer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

class OrderManagerTest {
    @Test
void forwardToChefQueueWithOrderContainingFood() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    Order orderWithFood = new Order(new Customer("John", "Doe"), "Pizza", "");

    // When
    orderManager.forwardToChefQueue(orderWithFood);

    // Then
    Order actualOrder = orderManager.getChefsDeque().take();
    assertEquals(orderWithFood, actualOrder);
}

@Test
void shouldNotBlockWhenForwardingAnOrderToAQueueIfTheQueueIsNotFull() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    Order orderWithFood = new Order(new Customer("John", "Doe"), "Pizza", "");
    Order orderWithDrink = new Order(new Customer("Jane", "Doe"), "", "Coke");

    // When
    orderManager.forwardToChefQueue(orderWithFood);
    orderManager.forwardToBartenderQueue(orderWithDrink);

    // Then
    Order actualOrderFromChefsQueue = orderManager.getChefsDeque().poll(1, TimeUnit.SECONDS);
    Order actualOrderFromBartendersQueue = orderManager.getBartendersDeque().poll(1, TimeUnit.SECONDS);

    assertEquals(orderWithFood, actualOrderFromChefsQueue);
    assertEquals(orderWithDrink, actualOrderFromBartendersQueue);
}

@Test
void shouldHandleConcurrentAccessToQueuesWithoutDataCorruption() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    int numberOfThreads = 10;
    int numberOfOrdersPerThread = 100;
    CountDownLatch startSignal = new CountDownLatch(1);
    CountDownLatch doneSignal = new CountDownLatch(numberOfThreads);

    // When
    for (int i = 0; i < numberOfThreads; i++) {
        new Thread(() -> {
            try {
                startSignal.await();
                for (int j = 0; j < numberOfOrdersPerThread; j++) {
                    Order orderWithFood = new Order(new Customer("John", "Doe"), "Pizza", "");
                    Order orderWithDrink = new Order(new Customer("Jane", "Doe"), "", "Coke");
                    orderManager.forwardToChefQueue(orderWithFood);
                    orderManager.forwardToBartenderQueue(orderWithDrink);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                doneSignal.countDown();
            }
        }).start();
    }

    startSignal.countDown();
    doneSignal.await();

    // Then
    for (int i = 0; i < numberOfThreads; i++) {
        for (int j = 0; j < numberOfOrdersPerThread; j++) {
            Order actualOrderFromChefsQueue = orderManager.getChefsDeque().poll(1, TimeUnit.SECONDS);
            Order actualOrderFromBartendersQueue = orderManager.getBartendersDeque().poll(1, TimeUnit.SECONDS);
            assertNotNull(actualOrderFromChefsQueue);
            assertNotNull(actualOrderFromBartendersQueue);
        }
    }
}

@Test
void shouldHandleOrderWithBothFoodAndDrink() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    Order orderWithBothFoodAndDrink = new Order(new Customer("John", "Doe"), "Pizza", "Coke");

    // When
    orderManager.forwardToChefQueue(orderWithBothFoodAndDrink);
    orderManager.forwardToBartenderQueue(orderWithBothFoodAndDrink);

    // Then
    Order actualOrderFromChefsQueue = orderManager.getChefsDeque().poll(1, TimeUnit.SECONDS);
    Order actualOrderFromBartendersQueue = orderManager.getBartendersDeque().poll(1, TimeUnit.SECONDS);
    assertEquals(orderWithBothFoodAndDrink, actualOrderFromChefsQueue);
    assertEquals(orderWithBothFoodAndDrink, actualOrderFromBartendersQueue);
}
@Test
void shouldHandleOrderWithNeitherFoodNorDrink() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    Order orderWithNeitherFoodNorDrink = new Order(new Customer("John", "Doe"), "", "");

    // When
    orderManager.forwardToChefQueue(orderWithNeitherFoodNorDrink);
    orderManager.forwardToBartenderQueue(orderWithNeitherFoodNorDrink);

    // Then
    Order actualOrderFromChefsQueue = orderManager.getChefsDeque().poll(1, TimeUnit.SECONDS);
    Order actualOrderFromBartendersQueue = orderManager.getBartendersDeque().poll(1, TimeUnit.SECONDS);
    assertNull(actualOrderFromChefsQueue);
    assertNull(actualOrderFromBartendersQueue);
}

@Test
void shouldHandleOrderWithMixOfUppercaseAndLowercaseLettersInFoodOrDrinkName() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    Order orderWithMixedCaseFood = new Order(new Customer("John", "Doe"), "PIZZA", "");
    Order orderWithMixedCaseDrink = new Order(new Customer("Jane", "Doe"), "", "coke");

    // When
    orderManager.forwardToChefQueue(orderWithMixedCaseFood);
    orderManager.forwardToBartenderQueue(orderWithMixedCaseDrink);

    // Then
    Order actualOrderFromChefsQueue = orderManager.getChefsDeque().poll(1, TimeUnit.SECONDS);
    Order actualOrderFromBartendersQueue = orderManager.getBartendersDeque().poll(1, TimeUnit.SECONDS);
    assertEquals("PIZZA", actualOrderFromChefsQueue.getFood());
    assertEquals("coke", actualOrderFromBartendersQueue.getDrink());
}

@Test
void shouldHandleOrderWithSpecialCharactersInFoodOrDrinkName() throws InterruptedException {
    // Given
    OrderManager orderManager = new OrderManager();
    Order orderWithSpecialCharactersInFood = new Order(new Customer("John", "Doe"), "Pizza!", "");
    Order orderWithSpecialCharactersInDrink = new Order(new Customer("Jane", "Doe"), "", "Coke#");

    // When
    orderManager.forwardToChefQueue(orderWithSpecialCharactersInFood);
    orderManager.forwardToBartenderQueue(orderWithSpecialCharactersInDrink);

    // Then
    Order actualOrderFromChefsQueue = orderManager.getChefsDeque().poll(1, TimeUnit.SECONDS);
    Order actualOrderFromBartendersQueue = orderManager.getBartendersDeque().poll(1, TimeUnit.SECONDS);
    assertEquals("Pizza!", actualOrderFromChefsQueue.getFood());
    assertEquals("Coke#", actualOrderFromBartendersQueue.getDrink());
}

}