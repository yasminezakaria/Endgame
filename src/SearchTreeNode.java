public class SearchTreeNode {
    EndgameState state;
    SearchTreeNode parent;
    String operator;
    int cost;
    int depth;

    public SearchTreeNode(EndgameState state, SearchTreeNode parent, String operator, int cost, int depth) {
        this.state = state;
        this.parent = parent;
        this.operator = operator;
        this.cost = cost;
        this.depth = depth;
    }

    /*
    Prints the sequence of operators done from the root
    */
    public String printPath() {
        return printPathHelper("", this);
    }

    public String printPathHelper(String str, SearchTreeNode node) {
        if (node.parent == null)
            return str;
        return printPathHelper(node.operator + "," + str, node.parent);
    }

    public void visualize(int m, int n, int tx, int ty) {
        visualizeHelper(m, n, tx, ty, this);
    }

    public void visualizeHelper(int m, int n, int tx, int ty, SearchTreeNode node) {
        if (node == null)
            return;
        node.state.visualize(m, n, tx, ty);
        System.out.println();
        visualizeHelper(m, n, tx, ty, node.parent);
    }
}
