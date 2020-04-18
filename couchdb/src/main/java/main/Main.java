package main;

public class Main {
    public static void main(String[] args) {
        loadConfiguration();
        UX giffyInstance = new UX();
    }

    private static void loadConfiguration() {
        ConfigParams.loadConfig();
    }
}
