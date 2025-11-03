import java.util.Random;

public class Source extends Planet{
    public static String[] sources = {"Water" , "Energy", "Minerals"};

    Random rnd = new Random();

    public static void setSources(){
        for(int i = 0; i < planetFeatures.length; i++){
            String planetName = Planet.planetFeatures[i][0][0];

            boolean isPlanetOnMap = false;
            outer:
            for(int t = 0; t < Galaxy.table.length; t++){
                for(int j = 0; j < Galaxy.table[t].length; j++){
                    if(Galaxy.table[t][j].equals(planetName)){
                        isPlanetOnMap = true;
                        break outer;
                    }
                }
            }

            if(isPlanetOnMap){
                int sourcesNum = rdn.nextInt(1,7);
                String[] SourcesSl = new String[sourcesNum];

                for(int v =  0; v < sourcesNum; v++){
                    SourcesSl[v] = sources[rdn.nextInt(sources.length)];
                }

                planetFeatures[i][2] = SourcesSl;
            }
        }
    }
}
