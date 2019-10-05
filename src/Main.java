public class Main {
    public static String solve(String grid, String strategy, boolean visualize) {
        SearchStrategy searchStrategy = SearchStrategy.valueOf(strategy);
        int m = Integer.parseInt(grid.charAt(0) + "");
        int n = Integer.parseInt(grid.charAt(2) + "");
        int ix = Integer.parseInt(grid.charAt(4) + "");
        int iy = Integer.parseInt(grid.charAt(6) + "");
        Endgame endgame = new Endgame(m, n, ix, iy, grid);
        // call fill grid method with grid attribute
        SearchTreeNode solution = endgame.generic_search(searchStrategy);
//        // call game method with strategy attribute -> returns the solution
//        System.out.println(solution.state.toString());
        return solution.cost + "";

    }

    public static void main(String[] args) {
        System.out.println(solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3", "BF", false));
//        char [] c = new char[2];
//        System.out.println(c[0]);
    }
}
