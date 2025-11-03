import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Galaxy {
    public static String[][] table;
    public static String[] planets;
    public static Random rnd = new Random();

    public static void createPlanet() {
        planets = new String[5];
        String usedLetters = "";

        for (int i = 0; i < 5; i++) {
            char ch;
            do {
                ch = RandomCh('A', 'Z');
            } while (usedLetters.contains(String.valueOf(ch)));
            usedLetters += ch;
            planets[i] = String.format("[%c] -> Planet%c", ch, ch);
        }

        int row = 5, col = 5;
        table = new String[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                table[i][j] = ".";
            }
        }

        for (String planet : planets) {
            int r, c;
            do {
                r = rnd.nextInt(row);
                c = rnd.nextInt(col);
            } while (!table[r][c].equals("."));
            table[r][c] = planet;
        }

        int vr, vc;
        do {
            vr = rnd.nextInt(row);
            vc = rnd.nextInt(col);
        } while (!table[vr][vc].equals("."));
        table[vr][vc] = "?";

        int blackHoleCount = rnd.nextInt(1, 4);
        for (int i = 0; i < blackHoleCount; i++) {
            int r, c;
            do {
                r = rnd.nextInt(row);
                c = rnd.nextInt(col);
            } while (!table[r][c].equals("."));
            table[r][c] = "◎";
        }
    }

    public static void showGalaxy() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                String cell = table[i][j];
                if (cell.equals(".")) {
                    System.out.print(". ");
                } else if (cell.equals("?")) {
                    System.out.print("? ");
                } else if (cell.equals("◎")) {
                    System.out.print("◎ ");
                } else if (cell.startsWith("[")) {
                    System.out.print(cell.charAt(1) + " ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    public static char RandomCh(char ch1, char ch2) {
        return (char) (ch1 + Math.random() * (ch2 - ch1));
    }

    public static void updateVehiclePosition(int newRow, int newCol) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j].equals("?")) {
                    table[i][j] = ".";
                }
            }
        }
        table[newRow][newCol] = "?";
    }

    public static Set<String> getAllMapPlanets() {
        Set<String> mapPlanets = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                String cell = table[i][j];
                if (cell.startsWith("[")) {
                    mapPlanets.add(cell);
                }
            }
        }
        return mapPlanets;
    }
}
