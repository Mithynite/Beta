import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private String caption;                          // Menu header/caption
    private ArrayList<MenuItem> menuItems;           // List of menu items
    private Scanner scanner = new Scanner(System.in); // Scanner for user input

    public Menu(String caption) {
        this.caption = caption;
        this.menuItems = new ArrayList<>();
    }

    // Display the menu to the console
    public void show() {
        System.out.println("\n" + caption);
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ". " + menuItems.get(i)); // Calls toString() on MenuItem
        }
    }

    // Get a MenuItem based on user input (index selection)
    public MenuItem selection(int userInput) {
        int index = userInput - 1;
        if (index < 0 || index >= menuItems.size()) {
            System.err.println("Index " + userInput + " is not a valid input.");
            return null;
        }
        return menuItems.get(index);
    }

    // Get a MenuItem based on a string user input
    public MenuItem selection(String userInput) {
        try {
            int index = Integer.parseInt(userInput); // Convert input to integer
            return selection(index);                // Reuse selection(int) logic
        } catch (NumberFormatException e) {
            System.err.println("Invalid input '" + userInput + "'");
            return null;
        }
    }

    // Read user input from console and fetch the selected menu item
    public MenuItem selection() {
        System.out.print("Enter your selection: ");
        String input = scanner.nextLine();
        return selection(input);
    }

    // Execute menu: show repeatedly until user selects a valid option
    public MenuItem execute() {
        MenuItem selectedItem = null;
        do {
            show();                     // Show the menu options
            selectedItem = selection(); // Process the user's choice
        } while (selectedItem == null); // Repeat until valid selection

        selectedItem.execute(); // Call execute() on the selected menu item
        return selectedItem;
    }

    // Add a new MenuItem to the menu
    public void add(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }
}
