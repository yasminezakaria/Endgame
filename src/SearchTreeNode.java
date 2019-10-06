import java.util.ArrayList;

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
    public String printPath(){
        return printPathHelper("", this);
    }
    public String printPathHelper(String str, SearchTreeNode node){
        if(node==null)
            return str;
        return printPathHelper(node.operator+" -> "+str, node.parent);
    }
}
