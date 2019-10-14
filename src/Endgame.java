import java.util.*;

public class Endgame extends SearchProblem {
    int m;
    int n;
    int tx;
    int ty;
    ArrayList<EndgameState> expandedNodes;
    int IDCutOff;

    public Endgame(String grid) {
        this.operators = new ArrayList<>();
        operators.add("up");
        operators.add("down");
        operators.add("left");
        operators.add("right");
        operators.add("kill");
        operators.add("collect");
        operators.add("snap");
        String[] gridSplit = grid.split(";");
        m = Integer.parseInt(gridSplit[0].charAt(0) + "");
        n = Integer.parseInt(gridSplit[0].charAt(2) + "");
        Position ironmanPos = new Position(Integer.parseInt(gridSplit[1].charAt(0) + ""), Integer.parseInt(gridSplit[1].charAt(2) + ""));
        tx = Integer.parseInt(gridSplit[2].charAt(0) + "");
        ty = Integer.parseInt(gridSplit[2].charAt(2) + "");
        String[] stonesIndices = gridSplit[3].split(",");
        String[] warriorsIndices = gridSplit[4].split(",");
        ArrayList<Position> warriors = new ArrayList<>();
        ArrayList<Position> stones = new ArrayList<>();
        for (int i = 0; i < stonesIndices.length; i = i + 2) {
            int sx = Integer.parseInt(stonesIndices[i]);
            int sy = Integer.parseInt(stonesIndices[i + 1]);
            stones.add(new Position(sx, sy));
        }
        for (int i = 0; i < warriorsIndices.length; i = i + 2) {
            int wx = Integer.parseInt(warriorsIndices[i]);
            int wy = Integer.parseInt(warriorsIndices[i + 1]);
            warriors.add(new Position(wx, wy));
        }
//        System.out.println(tx + ", " + ty);
//        System.out.println("Iron man " + ironmanPos.row + ", " + ironmanPos.column);
        this.initialState = (new EndgameState(ironmanPos, stones, warriors, 0, false)).toString();
        this.expandedNodes = new ArrayList<>();
        this.IDCutOff = 0;

    }

    public boolean adjWarriors(Position position, ArrayList<Position> warriors) {
        Position upWarriors = new Position(position.row - 1, position.column);
        Position downWarriors = new Position(position.row + 1, position.column);
        Position leftWarriors = new Position(position.row, position.column - 1);
        Position rightWarriors = new Position(position.row, position.column + 1);
        for (int i = 0; i < warriors.size(); i++) {
            int x = warriors.get(i).row;
            int y = warriors.get(i).column;
            if (upWarriors.equals(x, y))
                return true;
            if (leftWarriors.equals(x, y))
                return true;
            if (rightWarriors.equals(x, y))
                return true;
            if (downWarriors.equals(x, y))
                return true;
        }
        return false;

    }

    public boolean adjThanos(Position position) {
        if (tx == position.row - 1 && ty == position.column)
            return true;
        if (tx == position.row + 1 && ty == position.column)
            return true;
        if (tx == position.row && ty == position.column - 1)
            return true;
        if (tx == position.row && ty == position.column + 1)
            return true;
        return false;

    }

    public boolean stoneCell(Position position, ArrayList<Position> stones) {
        for (int i = 0; i < stones.size(); i++) {
            int x = stones.get(i).row;
            int y = stones.get(i).column;
            if (position.equals(x, y))
                return true;
        }
        return false;
    }

    public boolean validOperator(String operator, SearchTreeNode node) {
        EndgameState state = node.state;
        ArrayList<Position> warriors = state.warriors;
        ArrayList<Position> stones = state.stones;
        Position ironmanPosition = state.ironMan;
        Position ironmanUp = new Position(ironmanPosition.row - 1, ironmanPosition.column);
        Position ironmanDown = new Position(ironmanPosition.row + 1, ironmanPosition.column);
        Position ironmanLeft = new Position(ironmanPosition.row, ironmanPosition.column - 1);
        Position ironmanRight = new Position(ironmanPosition.row, ironmanPosition.column + 1);
//        System.out.println("Current Cost so far: "+node.cost);
        switch (operator) {
            case "up":
                if (ironmanUp.row >= 0 && (!(ironmanUp.equals(tx, ty) && stones.size() != 0)) && (findObject(ironmanUp, warriors) == -1))
                    return true;
                else return false;
            case "down":
                if (ironmanDown.row < m && (!(ironmanDown.equals(tx, ty) && stones.size() != 0)) && (findObject(ironmanDown, warriors) == -1))
                    return true;
                else return false;
            case "left":
                if (ironmanLeft.column >= 0 && (!(ironmanLeft.equals(tx, ty) && stones.size() != 0)) && (findObject(ironmanLeft, warriors) == -1))
                    return true;
                else return false;
            case "right":
                if (ironmanRight.column < n && (!(ironmanRight.equals(tx, ty) && stones.size() != 0)) && (findObject(ironmanRight, warriors) == -1))
                    return true;
                else return false;
            case "kill":
                if (adjWarriors(ironmanPosition, warriors)) return true;
                else return false;
            case "collect":
                if (stoneCell(ironmanPosition, stones)) return true;
                else return false;
            case "snap":
                return (node.cost < 100 && stones.isEmpty() && ironmanPosition.equals(tx, ty));
            default:
                return false;
        }

    }

    public ArrayList<Position> killWarriors(Position ironMan, ArrayList<Position> warriors) {
        ArrayList<Position> adjWarriors = new ArrayList<>();
        ArrayList<Position> updatedWarriors = new ArrayList<>();
        // Make copy o warriors in actual warriors to avoid pass by reference
        for (int i = 0; i < warriors.size(); i++) {
            updatedWarriors.add(warriors.get(i));
        }
        adjWarriors.add(new Position(ironMan.row - 1, ironMan.column));
        adjWarriors.add(new Position(ironMan.row + 1, ironMan.column));
        adjWarriors.add(new Position(ironMan.row, ironMan.column - 1));
        adjWarriors.add(new Position(ironMan.row, ironMan.column + 1));
        for (int i = 0; i < adjWarriors.size(); i++) {
            int index = findObject(adjWarriors.get(i), updatedWarriors);
            if (index != -1)
                updatedWarriors.remove(index);
        }
        return updatedWarriors;
    }

    public int findObject(Position element, ArrayList<Position> objects) {
        for (int i = 0; i < objects.size(); i++) {
            int x = objects.get(i).row;
            int y = objects.get(i).column;
            if (element.equals(x, y))
                return i;
        }
        return -1;
    }


    public boolean repeatedState(EndgameState state) {
        for (int i = 0; i < expandedNodes.size(); i++) {
            if (state.isEqual(expandedNodes.get(i)))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {


    }


    @Override
    SearchTreeNode transition(SearchTreeNode currentNode, String operator) {
        Position ironMan;
        SearchTreeNode parent = currentNode;
        int cost = currentNode.cost;
        int depth = currentNode.depth + 1;
        EndgameState currentState = currentNode.state;//.copy();
        int pathCost = pathCost(currentNode, operator);
        switch (operator) {
            case "up":
                ironMan = new Position(currentState.ironMan.row - 1, currentState.ironMan.column);
                return new SearchTreeNode(new EndgameState(ironMan, currentState.stones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "down":
                ironMan = new Position(currentState.ironMan.row + 1, currentState.ironMan.column);
                return new SearchTreeNode(new EndgameState(ironMan, currentState.stones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "left":
                ironMan = new Position(currentState.ironMan.row, currentState.ironMan.column - 1);
                return new SearchTreeNode(new EndgameState(ironMan, currentState.stones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "right":
                ironMan = new Position(currentState.ironMan.row, currentState.ironMan.column + 1);
                return new SearchTreeNode(new EndgameState(ironMan, currentState.stones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "kill":
                ArrayList<Position> updatedWarriors = killWarriors(currentState.ironMan, currentState.warriors);
                pathCost = (currentState.warriors.size() - updatedWarriors.size()) * 2 + pathCost;
                return new SearchTreeNode(new EndgameState(currentState.ironMan, currentState.stones, updatedWarriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "collect":
                ArrayList<Position> updatedStones = new ArrayList<>();
                for (int i = 0; i < currentState.stones.size(); i++) {
                    updatedStones.add(currentState.stones.get(i));
                }
                updatedStones.remove(findObject(currentState.ironMan, currentState.stones));
                return new SearchTreeNode(new EndgameState(currentState.ironMan, updatedStones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "snap":
                return new SearchTreeNode(new EndgameState(currentState.ironMan, currentState.stones, currentState.warriors, pathCost - cost, true), parent, operator, pathCost, depth);

        }
        return null;
    }

    @Override
    boolean goalTest(SearchTreeNode searchTreeNode) {
        return searchTreeNode.state.snap;
    }

    @Override
    int pathCost(SearchTreeNode currentNode, String operator) {
        EndgameState state = currentNode.state;
        int cost = 0;
        Position ironMan;
        switch (operator) {
            case "up":
                ironMan = new Position(state.ironMan.row - 1, state.ironMan.column);
                if (adjWarriors(ironMan, state.warriors)) cost += 1;
                if (adjThanos(ironMan)) cost += 5;
                if (ironMan.row == tx && ironMan.column == ty) cost += 5;
                break;
            case "down":
                ironMan = new Position(state.ironMan.row + 1, state.ironMan.column);
                if (adjWarriors(ironMan, state.warriors)) cost += 1;
                if (adjThanos(ironMan)) cost += 5;
                if (ironMan.row == tx && ironMan.column == ty) cost += 5;
                break;
            case "left":
                ironMan = new Position(state.ironMan.row, state.ironMan.column - 1);
                if (adjWarriors(ironMan, state.warriors)) cost += 1;
                if (adjThanos(ironMan)) cost += 5;
                if (ironMan.row == tx && ironMan.column == ty) cost += 5;
                break;
            case "right":
                ironMan = new Position(state.ironMan.row, state.ironMan.column + 1);
                if (adjWarriors(ironMan, state.warriors)) cost += 1;
                if (adjThanos(ironMan)) cost += 5;
                if (ironMan.row == tx && ironMan.column == ty) cost += 5;
                break;
            case "kill":
                cost += 0;
                break;
            case "collect":
                if (adjWarriors(state.ironMan, state.warriors)) cost += 1;
                if (adjThanos(state.ironMan)) cost += 5;
                cost += 3;
                break;
            case "snap":
                cost += 0;
                break;
        }

        return currentNode.cost + cost;
    }

    @Override
    Queue<SearchTreeNode> BF(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        if (!repeatedState(currentNode.state)) {
            expandedNodes.add(currentNode.state);
            if (validOperator("snap", currentNode)) {
                ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, "snap"));
            } else if (validOperator("collect", currentNode)) {
                ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, "collect"));
            } else {
                for (int i = 0; i < operators.size() - 2; i++) {
                    if (validOperator(operators.get(i), currentNode)) {
                        ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, operators.get(i)));
                    }
                }
            }
        }
        return nodes;
    }

    @Override
    Queue<SearchTreeNode> DF(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
//        TODO: Check on pushing operators order (kill, movement)
        if (!repeatedState(currentNode.state)) {
            expandedNodes.add(currentNode.state);
//            System.out.println(expandedNodes.size());
            if (validOperator("snap", currentNode)) {
                ((LinkedList<SearchTreeNode>) nodes).addFirst(transition(currentNode, "snap"));
            } else if (validOperator("collect", currentNode)) {
                ((LinkedList<SearchTreeNode>) nodes).addFirst(transition(currentNode, "collect"));
            } else {
                for (int i = 0; i < operators.size() - 2; i++) {
                    if (validOperator(operators.get(i), currentNode)) {
                        ((LinkedList<SearchTreeNode>) nodes).addFirst(transition(currentNode, operators.get(i)));
                    }
                }
            }
        }
        return nodes;
    }

    @Override
    Queue<SearchTreeNode> UC(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        return null;
    }

    @Override
    Queue<SearchTreeNode> ID(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        if (currentNode.depth <= IDCutOff) {
            if (!repeatedState(currentNode.state)) {
                expandedNodes.add(currentNode.state);
//                System.out.println(expandedNodes.size());
                if (validOperator("snap", currentNode)) {
                    ((LinkedList<SearchTreeNode>) nodes).addFirst(transition(currentNode, "snap"));
                } else if (validOperator("collect", currentNode)) {
                    ((LinkedList<SearchTreeNode>) nodes).addFirst(transition(currentNode, "collect"));
                } else {
                    for (int i = 0; i < operators.size() - 2; i++) {
                        if (validOperator(operators.get(i), currentNode)) {
                            ((LinkedList<SearchTreeNode>) nodes).addFirst(transition(currentNode, operators.get(i)));
                        }
                    }
                }
            }
        }
        return nodes;
    }
}
