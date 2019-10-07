public class SearchTreeNode {
    //    State
//    Parent Node
//    Operator occurred
//    Cost from Root
//    Depth
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

    public String printPath() {
        return printPathHelper("", this);
    }

    public String printPathHelper(String str, SearchTreeNode node) {
        if (node.parent == null)
            return str;
        return printPathHelper(node.operator + "," + str, node.parent);
//                return printPathHelper(node.operator + " D = " + node.state.damage + " -> " + str, node.parent);
        //" P= " + node.state.ironMan.row + ", " + node.state.ironMan.column +
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
