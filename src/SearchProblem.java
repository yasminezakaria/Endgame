import java.util.*;

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
//    TODO: Unify all method in one
    abstract PriorityQueue<SearchTreeNode> BF(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract PriorityQueue<SearchTreeNode> DF(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract PriorityQueue<SearchTreeNode> UC(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract PriorityQueue<SearchTreeNode> ID(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract PriorityQueue<SearchTreeNode> GR1(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract PriorityQueue<SearchTreeNode> GR2(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract PriorityQueue<SearchTreeNode> AS1(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    abstract PriorityQueue<SearchTreeNode> AS2(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode);

    SearchTreeNode generic_search(SearchStrategy strategy) {
        System.out.println("Entered Generic Search Method");
        SearchTreeNode root = new SearchTreeNode(new EndgameState(initialState), null, "", 0, 0);
        PriorityQueue<SearchTreeNode> nodes = null;
        switch (strategy) {
            case BF:
                nodes = new PriorityQueue<>(Comparator.comparingInt(o -> o.state.count));
                break;
            case DF:
                nodes = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingInt(a -> a.state.count)));
                break;
            case ID:
                nodes = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingInt(a -> a.state.count)));
                break;
            case UC:
                nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
                break;
            case AS1:
                nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.state.heuristicCost + a.cost));
                break;
            case AS2:
                break;
            case GR1:
                break;
            case GR2:
                break;
        }
        nodes.add(root);
        while (!nodes.isEmpty()) {
            SearchTreeNode node = nodes.remove();
//            System.out.println(node.state.ID);
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
                case AS1:
                    nodes = AS1(nodes, node);
                case AS2:
                    break;
                case GR1:
                    break;
                case GR2:
                    break;
            }
        }

        return null;
    }

}
