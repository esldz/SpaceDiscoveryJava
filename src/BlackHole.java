import java.util.Random;

public class BlackHole {
    public static Random rdn  = new Random();

    public static void enterBlackHole(){
        String destination = getRandomPlanet();
        System.out.println("The black hole teleports you to a random planet without consuming fuel");
        System.out.println("New Location: " + destination);

        Main.handlePlanetVisit(destination, 0);
    }

    public static String getRandomPlanet(){
        if(Galaxy.planets == null || Galaxy.planets.length == 0) return "no planet";
        return Galaxy.planets[rdn.nextInt(Galaxy.planets.length)];
    }
}
