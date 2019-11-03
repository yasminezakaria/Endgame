import java.util.*;

public abstract class SearchProblem {
    String initialState;
    ArrayList<String> operators;

    // should be used in the expand function
    abstract SearchTreeNode transition(SearchTreeNode currentNode, String operator);

    abstract boolean goalTest(SearchTreeNode searchTreeNode);

    abstract int pathCost(SearchTreeNode currentNode, String operator);

    // next function should call expansion function
    abstract PriorityQueue<SearchTreeNode> InformedSearch(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode, SearchStrategy strategy);
    abstract PriorityQueue<SearchTreeNode> UninformedSearch(PriorityQueue<SearchTreeNode> nodes, SearchTreeNode currentNode, SearchStrategy strategy);
    SearchTreeNode generic_search(SearchStrategy strategy) {
        SearchTreeNode root = new SearchTreeNode(new EndgameState(initialState), null, "", 0, 0);
        PriorityQueue<SearchTreeNode> nodes = null;
        switch (strategy) {
            case BF:
                nodes = new PriorityQueue<>(Comparator.comparingInt(o -> o.state.ID));
                break;
            case DF:
                nodes = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingInt(a -> a.state.ID)));
                break;
            case ID:
                nodes = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingInt(a -> a.state.ID)));
                break;
            case UC:
                nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
                break;
            case AS1:
                nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.state.heuristicCost + a.cost));
                break;
            case AS2:
                nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.state.heuristicCost + a.cost));
                break;
            case GR1:
                nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.state.heuristicCost));
                break;
            case GR2:
                nodes = new PriorityQueue<>(Comparator.comparingInt(a -> a.state.heuristicCost));
                break;
        }
        nodes.add(root);
        while (!nodes.isEmpty()) {
            SearchTreeNode node = nodes.remove();
            if (this.goalTest(node)) {
                return node;
            }
            if(strategy.equals(SearchStrategy.BF) || strategy.equals(SearchStrategy.DF) || strategy.equals(SearchStrategy.ID) || strategy.equals(SearchStrategy.UC))
                nodes = UninformedSearch(nodes, node, strategy);
            if(strategy.equals(SearchStrategy.AS1) || strategy.equals(SearchStrategy.AS2) || strategy.equals(SearchStrategy.GR1) || strategy.equals(SearchStrategy.GR2))
                nodes = InformedSearch(nodes, node, strategy);
        }

        return null;
    }

}
