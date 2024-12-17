public class MenuItem {
    private String description;   // Popis položky menu
    private Runnable action;      // Akce, která se provede při spuštění

    // Konstruktor
    public MenuItem(String description, Runnable action) {
        this.description = description;
        this.action = action;
    }

    @Override
    public String toString() {
        return description;
    }

    // Metoda pro spuštění zadané akce
    public void execute() {
        action.run();
    }
}

