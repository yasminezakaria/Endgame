import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Main {
    public static String solve(String grid, String strategy, boolean visualize) {
        SearchStrategy searchStrategy = SearchStrategy.valueOf(strategy);
        Endgame endgame = new Endgame(grid);
        SearchTreeNode solution = endgame.generic_search(searchStrategy);
        int IDExpandedNodes = 0;
        if (strategy.equals("ID")) {
            while (solution == null) {
                endgame.IDCutOff++;
                IDExpandedNodes += endgame.expandedNodes.size();
                endgame.expandedNodes = new HashSet<>();
                solution = endgame.generic_search(searchStrategy);
                System.out.println("Cut off: " + endgame.IDCutOff);
            }
        }
        // call fill grid method with grid attribute
        if (visualize)
            solution.visualize(endgame.m, endgame.n, endgame.tx, endgame.ty);
        if (solution == null)
            return "There is no solution";
        String solutionPath = solution.printPath();
        return solutionPath.substring(0, solutionPath.length() - 1) + ";" + solution.cost + ";" + endgame.expandedNodes.size() + IDExpandedNodes;

    }

    public static String makeGrid(int m, int n){
        StringBuilder out = new StringBuilder();
        out.append(n + "," + m + ";");
        ArrayList<Position> fullCells = new ArrayList<>();
        Random rand = new Random();
        //0 -> m-1
        int ironManR = rand.nextInt(m);
        int ironManC = rand.nextInt(n);
        Position ironManPos = new Position(ironManR, ironManC);
        fullCells.add(ironManPos);
        out.append(ironManR +","+ironManC + ";");
        int thanosR = rand.nextInt(m);
        int thanosC = rand.nextInt(n);
        while(ironManPos.equals(thanosR, thanosC)){
            thanosR = rand.nextInt(m);
            thanosC = rand.nextInt(n);
        }
        fullCells.add(new Position(thanosR, thanosC));
        out.append(thanosR +","+thanosC + ";");
        for(int i =0; i<6; i++){
            int stoneR = rand.nextInt(m);
            int stoneC = rand.nextInt(n);
            Position stonePos = new Position(stoneR, stoneC);
            while(fullCells.contains(stonePos)){
                stoneR = rand.nextInt(m);
                stoneC = rand.nextInt(n);
                stonePos = new Position(stoneR, stoneC);
            }
            fullCells.add(stonePos);
            out.append(stoneR +","+stoneC + ",");
        }
        out.deleteCharAt(out.lastIndexOf(","));
        out.append( ";");
        for(int i =0; i<5; i++){
            int warriorR = rand.nextInt(m);
            int warriorC = rand.nextInt(n);
            Position warriorPos = new Position(warriorR, warriorC);
                while(fullCells.contains(warriorPos)){
                    warriorR = rand.nextInt(m);
                    warriorC = rand.nextInt(n);
                    warriorPos = new Position(warriorR, warriorC);
            }
            fullCells.add(warriorPos);
            out.append(warriorR +","+warriorC + ",");
        }
        out.deleteCharAt(out.lastIndexOf(","));
        return out.toString();

    }
    public static void main(String[] args) {
//        System.out.println(makeGrid(15,15));
        System.out.println(solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3", "UC", true));
//        15,15;6,7;6,0,2,2,5,0,4,6,3,11,10,9;4,5,9,1,3,12,11,5,8,10
//        System.out.println(solve(makeGrid(10,10), "BF", true));
    }
}
