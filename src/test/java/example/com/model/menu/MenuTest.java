package example.com.model.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    @Test
    public void testSelectionWithInvalidIndex() {
    // Given
    Menu menu = new Menu("Main Menu");
    MenuItem item1 = new MenuItem("Option 1", () -> {});
    MenuItem item2 = new MenuItem("Back", () -> {});
    menu.add(item1);
    menu.add(item2);

    // When
    MenuItem selectedItem = menu.selection(3);

    // Then
    assertNull(selectedItem, "Expected null when selecting an invalid index");
    System.err.println("Received error message: Index 3 is not a valid input.");
}
}