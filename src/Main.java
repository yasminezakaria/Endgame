import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Main {
    public static String solve(String grid, String strategy, boolean visualize) {
        SearchStrategy searchStrategy = SearchStrategy.valueOf(strategy);
        Endgame endgame = new Endgame(grid);
        SearchTreeNode solution = endgame.generic_search(searchStrategy);
        if (strategy.equals("ID")) {
            while (solution == null) {
                endgame.IDCutOff++;
                endgame.expandedNodes = new HashSet<>();
                solution = endgame.generic_search(searchStrategy);
            }
        }
        if (visualize)
            solution.visualize(endgame.m, endgame.n, endgame.tx, endgame.ty);
        if (solution == null)
            return "There is no solution";
        String solutionPath = solution.printPath();
        return solutionPath.substring(0, solutionPath.length() - 1) + ";" + solution.cost + ";" + endgame.expandedNodes.size();

    }
}
