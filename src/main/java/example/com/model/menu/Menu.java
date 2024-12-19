package example.com.model.menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private String caption;                          // Menu.Menu header/caption
    private ArrayList<MenuItem> menuItems;           // List of model.menu items
    private Scanner scanner = new Scanner(System.in); // Scanner for user input

    public Menu(String caption) {
        this.caption = caption;
        this.menuItems = new ArrayList<>();
    }

    public void show() {
        System.out.println("\n" + caption);
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ". " + menuItems.get(i).getDescription()); // Calls toString() on Menu.MenuItem
        }
    }

    public MenuItem selection(int userInput) {
        int index = userInput - 1;
        if (index < 0 || index >= menuItems.size()) {
            System.err.println("Index " + userInput + " is not a valid input.");
            return null;
        }
        return menuItems.get(index);
    }

    public MenuItem selection(String userInput) {
        try {
            int index = Integer.parseInt(userInput);
            return selection(index);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input '" + userInput + "'");
            return null;
        }
    }

    public MenuItem selection() {
        System.out.print("Enter your selection: ");
        String input = scanner.nextLine();
        return selection(input);
    }

    public void execute() {
        boolean running = true;
        while (running) {
            show(); // Show the menu
            MenuItem selectedItem = null;

            do {
                selectedItem = selection();
            } while (selectedItem == null);

            if (selectedItem.getDescription().toLowerCase().contains("back")) {
                System.out.println("Returning to the previous menu...");
                running = false;
            } else {
                selectedItem.execute();
            }
        }
    }

    public void add(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }
}
