package example.com.app;

import example.com.model.menu.Menu;
import example.com.model.menu.MenuItem;
import example.com.model.order.*;
import example.com.model.person.*;
import example.com.model.shift.Shift;
import example.com.util.EmployeeRole;

import java.util.Scanner;

public class RestaurantConsole {
    private final Scanner scanner = new Scanner(System.in);
    private OrderManager orderManager = new OrderManager();
    private Shift currentShift = null;
    private Customer currentCustomer = null;

    // Method to handle new employee creation
    private void addNewEmployee(EmployeeRole employeeRole) {
        Employee employee = null;
        while (true) {
            try {
                System.out.println("Enter the Employee's name: ");
                String name = scanner.nextLine();
                System.out.println("Enter the Employee's surname: ");
                String surname = scanner.nextLine();
                System.out.println("Enter the Employee's salary: ");
                int salary = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter the time it takes for the employee to complete an order (in milliseconds): ");
                long orderPreparationTime = Long.parseLong(scanner.nextLine());

                switch (employeeRole) {
                    case Chef:
                        employee = new Chef(name, surname, salary, orderManager, orderPreparationTime);
                        break;
                    case Bartender:
                        employee = new Bartender(name, surname, salary, orderManager, orderPreparationTime);
                        break;
                    case Waiter:
                        System.out.println("Enter the max number of orders the Waiter can handle: ");
                        int maxOrders = Integer.parseInt(scanner.nextLine());
                        employee = new Waiter(name, surname, salary, orderManager, orderPreparationTime, maxOrders);
                        break;
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        if (currentShift != null) {
            System.out.println(currentShift.addEmployee(employee));
        } else {
            System.out.println("Couldn't add new employee since no shift is currently created!");
        }
    }


    // Method to register a new customer
    private void registerCustomer() {
        if (currentCustomer != null) {
            System.out.println("A customer is already registered: " + currentCustomer);
            return;
        }
        while (true) {
            try {
                System.out.println("Enter your name: ");
                String customerName = scanner.nextLine();
                System.out.println("Enter your surname: ");
                String customerSurname = scanner.nextLine();
                currentCustomer = new Customer(customerName, customerSurname);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
                System.out.println("Do you want to retry? (y/n)");
                String retry = scanner.nextLine().trim().toLowerCase();
                if (!retry.equals("y")) {
                    return;
                }
            }
        }
    }

    // Method to place a new order
    private void placeOrder() {
        if (currentCustomer == null) {
            System.out.println("No customer is registered. Please register a customer first.");
            return;
        }
        while (true) {
            try {
                System.out.println("Enter the food you want (leave blank for none): ");
                String food = scanner.nextLine();
                System.out.println("Enter the drink you want (leave blank for none): ");
                String drink = scanner.nextLine();

                Order newOrder = new Order(currentCustomer, food, drink);
                orderManager.forwardToWaiterQueue(newOrder);
                System.out.println("Order placed successfully: " + newOrder);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Would you like to retry? (y/n)");
                String retry = scanner.nextLine().trim().toLowerCase();
                if (!retry.equals("y")) {
                    return; // Exit gracefully
                }
            } catch (InterruptedException e) {
                System.err.println("Failed to place the order.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void createShift() {
        if (currentShift != null) {
            System.out.println("Shift is already registered. You need to stop the current shift before creating a new one.");
            return;
        }

        while (true) {
            try {
                System.out.println("Enter the maximum number of employees for this shift: ");
                int maxEmployees = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter the shift duration in milliseconds: ");
                long durationInMs = Long.parseLong(scanner.nextLine());

                // Create and assign the shift
                currentShift = new Shift(maxEmployees, durationInMs);
                System.out.println("Shift created successfully with a maximum of " + maxEmployees +
                        " employees and a duration of " + durationInMs + " ms.");
                return;
            }catch (IllegalArgumentException e) {
                System.out.println("Error creating shift: " + e.getMessage());
            }
        }
    }


    private void startCurrentShift() {
        if(currentShift == null) {
            System.out.println("No shift is registered. Please register a customer first.");
        }
        if (!currentShift.containsAllTypesOfEmployees()) {
            System.out.println("Error: All required employee roles are not filled. Cannot start the shift!");
            return;
        }
        if(currentCustomer == null){
            System.out.println("Error: No customer selected. You need to order first!");
            return;
        }

        System.out.println("Starting the shift... Please wait until it finishes, approximately " + currentShift.getLengthInMs() + " miliseconds.");
        currentShift.start();

        try {
            // Blocking operation: Simulate the shift duration
            Thread.sleep(currentShift.getLengthInMs());
        } catch (InterruptedException e) {
            System.err.println("The shift was interrupted unexpectedly!");
            Thread.currentThread().interrupt(); // Restore interrupt flag
        } finally {
            // Clean up after shift ends
            currentShift.stop();
            System.out.println("Shift has ended. Employees were sent home.");
        }
    }

    // Manager menu
    private Menu showManagerMenu() {
        Menu managerMenu = new Menu("Manager Mode: What do you want to do?");
        managerMenu.add(new MenuItem("Add new Chef", () -> addNewEmployee(EmployeeRole.Chef)));
        managerMenu.add(new MenuItem("Add new Bartender", () -> addNewEmployee(EmployeeRole.Bartender)));
        managerMenu.add(new MenuItem("Add new Waiter", () -> addNewEmployee(EmployeeRole.Waiter)));
        managerMenu.add(new MenuItem("Create shift", this::createShift));
        managerMenu.add(new MenuItem("Start current Shift", this::startCurrentShift));
        managerMenu.add(new MenuItem("Back to Main Menu", () -> System.out.println("Returning to Main Menu...")));
        return managerMenu;
    }

    // Customer menu
    private Menu showCustomerMenu() {
        Menu clientMenu = new Menu("Customer Mode: What do you want to do?");
        clientMenu.add(new MenuItem("Order food or drink", this::placeOrder));
        clientMenu.add(new MenuItem("Register as customer", this::registerCustomer));
        clientMenu.add(new MenuItem(
                "Back to Main Menu", () -> System.out.println("Returning to Main Menu...")));
        return clientMenu;
    }

    public void start() {
        Menu mainMenu = new Menu("Welcome! Select what you want to do:");
        mainMenu.add(new MenuItem("Manage the restaurant", () -> {
            Menu managerMenu = showManagerMenu();
            managerMenu.execute();
        }));
        mainMenu.add(new MenuItem("Enter the restaurant as a Customer (order)", () -> {
            Menu clientMenu = showCustomerMenu();
            clientMenu.execute();
        }));
        mainMenu.add(new MenuItem("Exit the program", () -> {
            System.out.println("Shutting down the program... Goodbye!");
            if (currentShift != null) currentShift.stop();
            System.exit(0);
        }));

        // Run the main menu loop
        while (true) {
            mainMenu.execute();
        }
    }
}
