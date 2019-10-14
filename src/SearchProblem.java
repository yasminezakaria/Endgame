import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SearchProblem {
    /*    Initial State
        Set of Operators/ Actions
        Transition Function
        Goal Test Function
        Path Cost Function
    */
    String initialState;
    ArrayList<String> operators;

    // should be used in the expand function
    abstract SearchTreeNode transition(SearchTreeNode currentNode, String operator);

    abstract boolean goalTest(SearchTreeNode searchTreeNode);

    abstract int pathCost(SearchTreeNode currentNode, String operator);

    // next function should call expansion function
    abstract Queue<SearchTreeNode> BF(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract Queue<SearchTreeNode> DF(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract Queue<SearchTreeNode> UC(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract Queue<SearchTreeNode> ID(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    SearchTreeNode generic_search(SearchStrategy strategy) {
        System.out.println("Entered Generic Search Method");
        SearchTreeNode root = new SearchTreeNode(new EndgameState(initialState), null, "", 0, 0);
        Queue<SearchTreeNode> nodes = new LinkedList<>();
        ((LinkedList<SearchTreeNode>) nodes).push(root);
        while (!nodes.isEmpty()) {
            SearchTreeNode node = ((LinkedList<SearchTreeNode>) nodes).pop();
            if (this.goalTest(node)) {
//                System.out.println(node.cost);
//                System.out.println(node.printPath());
                return node;
            }
            switch (strategy) {
                case BF:
                    nodes = BF(nodes, node);
                    break;
                case DF:
                    nodes = DF(nodes, node);
                    break;
                case ID:
                    nodes = ID(nodes, node);
                    break;
                case UC:
                    nodes = UC(nodes, node);
                    break;
            }
        }

        return null;
    }

}
