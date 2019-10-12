import java.util.ArrayList;

public class Main {
    public static String solve(String grid, String strategy, boolean visualize) {
        SearchStrategy searchStrategy = SearchStrategy.valueOf(strategy);
        Endgame endgame = new Endgame(grid);
        SearchTreeNode solution = endgame.generic_search(searchStrategy);
        int IDExpandedNodes = 0;
        if(strategy.equals("ID")){
            while(solution==null){
                endgame.IDCutOff++;
                IDExpandedNodes+=endgame.expandedNodes.size();
                endgame.expandedNodes = new ArrayList<>();
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

    public static void main(String[] args) {
        System.out.println(solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3", "DF", true));
    }
}
