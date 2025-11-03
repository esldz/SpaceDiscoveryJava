import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Planet extends Galaxy{
    public static String[][][] planetFeatures;
    public static String[] atmosphereType = {"Toxic", "Liveable"};

    public static Random rdn = new Random();

    public static void showSources(){
        planetFeatures = new String[planets.length][3][];

        for( int i = 0; i < planetFeatures.length; i++ ){
            planetFeatures[i][0] = new String[]{ planets[i] };

            int atmosphere = rdn.nextInt(atmosphereType.length);
            planetFeatures[i][1] = new String[]{ atmosphereType[atmosphere] };
            planetFeatures[i][2] = new String[6];
        }
    }

    public static int landingCost(String atmosphere){
        if(atmosphere.equals("Toxic")){
            return rdn.nextInt(10,16);
        }else{
            return rdn.nextInt(5,10);
        }
    }

    public static Map<String, String[]> planetResourceMap = new HashMap<>();
}
