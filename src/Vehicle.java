import java.util.ArrayList;
import java.util.List;

public class Vehicle extends Source{
    public static int fuelLvl = 100;
    public static int resourceCapacity = 50;

    public static List<String> storedResources = new ArrayList<>();

    public static void moveTo(String destination){
        System.out.println("Moving to " + destination);

        int fuelCost = 10;
        if(fuelLvl > fuelCost){
            fuelLvl -= fuelCost;
            System.out.println("Fuel : " + fuelLvl);
        }else{
            System.out.println("insufficient fuel");
            System.out.println("""
                    ==================================
                                GAME OVER
                    ==================================
                    """);
            System.exit(0);
        }
    }

    public static void collectResources(String[] planetResources){
        for(String res : planetResources){
            if(storedResources.size() < resourceCapacity){
                storedResources.add(res);
                System.out.println("Collected: " + res);
            }else{
                System.out.println("Resource capacity full.");
                break;
            }
        }
        System.out.println("Total resources collected: " + storedResources.size());
    }

    public static void refuel(int addedFuel){
        fuelLvl = Math.min(fuelLvl + addedFuel, 150);
        System.out.println("Fuel lvl: " + fuelLvl);
    }

    public static void upgrade(String type,int usedResources){
        if(type.equalsIgnoreCase("Fuel")){
            fuelLvl += usedResources * 2;
        }else if(type.equalsIgnoreCase("Resource")){
            resourceCapacity += usedResources * 5;
        }
    }

    public static void showStatus(){
        System.out.println("-------Vehicle Status-------");
        System.out.println("Fuel lvl: " + fuelLvl);
        System.out.println("Resource capacity: " + resourceCapacity);
    }
}
