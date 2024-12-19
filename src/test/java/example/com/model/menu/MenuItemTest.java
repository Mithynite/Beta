package example.com.model.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {
    @Test
    void validateDescriptionField() {
        assertThrows(NullPointerException.class, () -> new MenuItem(null, () -> {}));
        assertThrows(IllegalArgumentException.class, () -> new MenuItem("", () -> {}));
        assertDoesNotThrow(() -> new MenuItem("Test Description", () -> {}));
    }
    
    @Test
    void validateActionField() {
        assertThrows(NullPointerException.class, () -> new MenuItem("Test Description", null));
    }
    
    @Test
    void testGetDescription() {
        String expectedDescription = "Test Description";
        MenuItem menuItem = new MenuItem(expectedDescription, () -> {});
        String actualDescription = menuItem.getDescription();
        assertEquals(expectedDescription, actualDescription);
    }
    
    @Test
    void testExecuteMethod() {
        String expectedMessage = "Test Message";
        MenuItem menuItem = new MenuItem("Test Description", () -> System.out.println(expectedMessage));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        menuItem.execute();
        String actualMessage = outputStream.toString().trim();
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    void testExecuteMethodWithMathematicalOperation() {
        double expectedResult = 4.0;
        MenuItem menuItem = new MenuItem("Test Description", () -> System.out.println(2 + 2));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        menuItem.execute();
        double actualResult = Double.parseDouble(outputStream.toString().trim());
        assertEquals(expectedResult, actualResult, 0.0);
    }
    
    @Test
    void testExecuteMethodWithSharedResourceModification() {
        AtomicInteger sharedResource = new AtomicInteger();
        MenuItem menuItem = new MenuItem("Test Description", () -> {
            sharedResource.getAndIncrement();
        });
        menuItem.execute();
        assertEquals(1, sharedResource.get());
    }
    
    @Test
    void testExecuteMethodWithException() {
        MenuItem menuItem = new MenuItem("Test Description", () -> {
            throw new RuntimeException("Test Exception");
        });
        assertThrows(RuntimeException.class, menuItem::execute);
    }
}