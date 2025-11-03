import java.util.*;

public class Main {
    public static Set<String> visitedPlanet = new HashSet<>();
    public static Map<String, String[]> planetResourceMap = new HashMap<>();
    public static Map<String, String> planetAtmosphereMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("""
                ===============================
                        SPACE DISCOVERY
                ===============================
                """);

        Scanner scanner = new Scanner(System.in);

        Galaxy.createPlanet();
        Planet.showSources();

        System.out.println("Planets: ");
        for (String planet : Galaxy.planets) {
            System.out.println("- " + planet);
        }

        System.out.println("--------Map--------");
        Galaxy.showGalaxy();

        Vehicle.showStatus();
        Source.setSources();

        while (true) {
            System.out.print("Enter the planet letter you want to travel to (or K for black hole): ");
            String letter = scanner.nextLine().toUpperCase();

            if (letter.equals("K")) {
                String randomPlanet = BlackHole.getRandomPlanet();
                System.out.println("The black hole teleports you to a random planet without consuming fuel");
                System.out.println("New location: " + randomPlanet);
                moveVehicleToPlanet(randomPlanet);
                handlePlanetVisit(randomPlanet, 0);
                continue;
            }

            String selectedPlanet = "[%s] -> Planet%s".formatted(letter, letter);
            boolean found = false;

            int vehicleRow = -1, vehicleCol = -1;
            int planetRow = -1, planetCol = -1;

            for (int i = 0; i < Galaxy.table.length; i++) {
                for (int j = 0; j < Galaxy.table[i].length; j++) {
                    if (Galaxy.table[i][j].equals("?")) {
                        vehicleRow = i;
                        vehicleCol = j;
                    }
                    if (Galaxy.table[i][j].equals(selectedPlanet)) {
                        planetRow = i;
                        planetCol = j;
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("Planet not found.");
                continue;
            }

            int distance = Math.abs(vehicleRow - planetRow) + Math.abs(vehicleCol - planetCol);
            int travelFuel = distance * 10;

            Galaxy.updateVehiclePosition(planetRow, planetCol);
            handlePlanetVisit(selectedPlanet, travelFuel);
        }
    }

    public static void handlePlanetVisit(String destination, int travelFuel) {
        visitedPlanet.add(destination);

        String atmosphere;
        String[] resources;

        if (planetResourceMap.containsKey(destination)) {
            resources = planetResourceMap.get(destination);
            atmosphere = planetAtmosphereMap.get(destination);
        } else {
            resources = new String[0];
            atmosphere = "";
            for (int i = 0; i < Planet.planetFeatures.length; i++) {
                if (Planet.planetFeatures[i][0][0].equals(destination)) {
                    atmosphere = Planet.planetFeatures[i][1][0];
                    resources = Planet.planetFeatures[i][2];
                    planetResourceMap.put(destination, resources);
                    planetAtmosphereMap.put(destination, atmosphere);
                    break;
                }
            }
        }

        int landingFuel = Planet.landingCost(atmosphere);
        int totalFuelCost = travelFuel + landingFuel;
        Vehicle.fuelLvl -= totalFuelCost;

        if(Vehicle.fuelLvl <= 0){
            System.out.println("""
                    ===============================
                               GAME OVER
                    ===============================
                    """);
            System.exit(0);
        }

        System.out.println("""
                ===============================
                        SPACE DISCOVERY
                ===============================
                """);

        System.out.println("Arrived at planet " + destination);
        System.out.println("Fuel consumed: " + totalFuelCost);
        System.out.println("Remaining fuel: " + Vehicle.fuelLvl);
        System.out.println("Atmosphere: " + atmosphere);
        System.out.println("Resources on planet:");
        for (String res : resources) {
            System.out.println("- " + res);
        }

        Scanner scanner = new Scanner(System.in);
        boolean onPlanet = true;

        while (onPlanet) {
            System.out.println("""
                    What would you like to do?
                    1. Collect resources
                    2. Refuel (1 resource = 2 fuel)
                    3. Upgrade vehicle (1 resource = 5 capacity)
                    4. Travel to another planet
                    """);

            String choice = scanner.nextLine();
            int used;

            switch (choice) {
                case "1":
                    String[] currentResources = planetResourceMap.get(destination);
                    if (currentResources.length == 0) {
                        System.out.println("No resources left on this planet.");
                    } else {
                        Vehicle.collectResources(currentResources);
                        planetResourceMap.put(destination, new String[0]);
                        System.out.println("Cargo Status: " + Vehicle.storedResources.size() + "/" + Vehicle.resourceCapacity);
                    }
                    break;
                case "2":
                    used = Math.min(10, Vehicle.storedResources.size());
                    Vehicle.refuel(used * 2);
                    for (int i = 0; i < used; i++) {
                        Vehicle.storedResources.remove(0);
                    }
                    System.out.println("Used " + used + " resources to refuel");
                    break;
                case "3":
                    used = Math.min(5, Vehicle.storedResources.size());
                    Vehicle.upgrade("Resource", used);
                    for (int i = 0; i < used; i++) {
                        Vehicle.storedResources.remove(0);
                    }
                    System.out.println("Used " + used + " resources to upgrade");
                    break;
                case "4":
                    onPlanet = false;
                    Galaxy.showGalaxy();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

        if (visitedPlanet.containsAll(Galaxy.getAllMapPlanets())) {
            System.out.println("""
                   ===============================
                             !YOU WIN! 
                       You visited all planets!
                   ===============================
                    """);
            System.exit(0);
        }
    }

    public static void moveVehicleToPlanet(String destination) {
        for (int i = 0; i < Galaxy.table.length; i++) {
            for (int j = 0; j < Galaxy.table[i].length; j++) {
                if (Galaxy.table[i][j].equals(destination)) {
                    Galaxy.updateVehiclePosition(i, j);
                    return;
                }
            }
        }
    }
}
