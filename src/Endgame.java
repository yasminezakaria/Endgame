import java.util.ArrayList;
import java.util.Arrays;

public class Endgame extends SearchProblem {
    int m;
    int n;
    char [][] planet;
    IronMan ironMan;

    public Endgame(int m, int n, int ix, int iy) {
        this.m = m;
        this.n = n;
        this.planet = new char[m][n];
        this.ironMan = new IronMan(ix, iy);
    }
    public void fillPlanet(String grid){
        String [] gridSplit = grid.split(";");
        String [] stonesIndices = gridSplit[3].split(",");
        String [] warriorsIndices = gridSplit[4].split(",");
        for (char[] row: planet)
            Arrays.fill(row, 'E');
        planet[ironMan.row][ironMan.column] = 'I';
        for (int i = 0; i<stonesIndices.length; i+=2){
            int sx = Integer.parseInt(stonesIndices[i]);
            int sy = Integer.parseInt(stonesIndices[i+1]);
            planet[sx][sy] = 'S';
        }
        for (int i = 0; i<warriorsIndices.length; i+=2){
            int wx = Integer.parseInt(warriorsIndices[i]);
            int wy = Integer.parseInt(warriorsIndices[i+1]);
            planet[wx][wy] = 'W';
        }
//        for (int i = 0; i<planet.length;i++){
//            for (int j = 0; j < planet[i].length; j++){
//                System.out.print(planet[i][j] + " ");
//            }
//            System.out.println();
//        }
    }
    public String game(SearchStrategy searchStrategy){
        switch (searchStrategy){
            case BF: return BF();
            case DF: return DF();
            case ID: return ID();
            case UC: return UC();
            default: return "";
        }
    }
    public String BF(){
        String solution = "";
        return solution;
    }
    public String DF(){
        String solution = "";
        return solution;
    }
    public String UC(){
        String solution = "";
        return solution;
    }
    public String ID(){
        String solution = "";
        return solution;
    }

    /*
* Class should have:
* 1- grid variable String[][]
* 2- m and n variables
* 3- IronMan object
* 4- fill grid method
* 5- Expand node method
* */
    public static void main(String[] args) {
        Endgame endgame = new Endgame(5,5, 1, 2);
        endgame.fillPlanet("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3");
    }

    @Override
    ArrayList<String> transition() {
        return null;
    }

    @Override
    boolean goalTest() {
        return false;
    }

    @Override
    int pathCost() {
        return 0;
    }
}
