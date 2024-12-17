import java.util.Date;
import java.util.Scanner;

public class RestaurantConsole {
    private final Scanner scanner = new Scanner(System.in);
    private OrderManager orderManager = new OrderManager();
    private Shift currentShift;

    public void start() {
        Menu mainMenu = new Menu("Select what you want to do:");
        mainMenu.add(new MenuItem("Manage the restaurant", () -> {
            Menu managerMenu = showManagerMenu();  // Invoke manager menu
            managerMenu.execute();
        }));
        mainMenu.add(new MenuItem("Enter the restaurant (order) as a Client", () -> {
            Menu clientMenu = showClientMenu();  // Invoke client menu
            clientMenu.execute();
        }));
        mainMenu.add(new MenuItem("Exit the program", () -> {
            System.out.println("Shutting down the program... Goodbye!");
            if (currentShift != null) currentShift.stop(); // Stop ongoing shifts
            System.exit(0);
        }));

        // Run the main menu loop
        while (true) {
            mainMenu.execute();
        }
    }

    private Menu showManagerMenu() {
        Menu managerMenu = new Menu("Choose an action:");
        managerMenu.add(new MenuItem("Add new Chef", () -> {
            addNewEmployee(EmployeeRole.Chef);
        }));

        managerMenu.add(new MenuItem("Add new Bartender", () -> {
            addNewEmployee(EmployeeRole.Bartender);
        }));

        managerMenu.add(new MenuItem("Add new Waiter", () -> {
            addNewEmployee(EmployeeRole.Waiter);
        }));

        managerMenu.add(new MenuItem("Back to Main Menu", () -> {
            System.out.println("Returning to Main Menu...");
        }));

        return managerMenu;
    }

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
                switch (employeeRole) {
                    case EmployeeRole.Chef:
                        employee = new Chef(name, surname, salary, orderManager);
                        break;
                    case EmployeeRole.Bartender:
                        employee = new Bartender(name, surname, salary, orderManager);
                        break;
                    case EmployeeRole.Waiter:
                        System.out.println("Enter the max amount of orders, Waiter can handle at once: ");
                        int maxAmountOfOrders = Integer.parseInt(scanner.nextLine());
                        employee = new Waiter(name, surname, salary, orderManager, maxAmountOfOrders);
                        break;
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        if(currentShift != null){
            System.out.println(currentShift.addEmployee(employee));
        }else{
            System.out.println("Couldn't add new employee since current shift is null!");
        }
    }

    private Menu showClientMenu() {
        Menu clientMenu = new Menu("Place an order:");

        clientMenu.add(new MenuItem("Order food and drink", () -> {
            System.out.println("Enter your name: ");
            String customerName = scanner.nextLine();
            System.out.println("Enter your surname: ");
            String customerSurname = scanner.nextLine();

            System.out.println("Enter your food (or leave blank if none): ");
            String food = scanner.nextLine();
            System.out.println("Enter your drink (or leave blank if none): ");
            String drink = scanner.nextLine();

            Customer customer = new Customer(customerName, customerSurname);
            Order newOrder = new Order(customer, food, drink);

            try {
                orderManager.getWaitersDeque().put(newOrder);
                System.out.println("Order placed successfully: " + newOrder);
            } catch (InterruptedException e) {
                System.err.println("Failed to place the order.");
                Thread.currentThread().interrupt();
            }
        }));

        clientMenu.add(new MenuItem("Back to Main Menu", () -> {
            System.out.println("Returning to Main Menu...");
        }));

        return clientMenu;
    }
}
