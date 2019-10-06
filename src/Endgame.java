import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Endgame extends SearchProblem {
    int m;
    int n;
    int tx;
    int ty;
//    char[][] planet;
//    IronMan ironMan;

    public Endgame(int m, int n, int ix, int iy, String grid) {
        this.operators = new ArrayList<>();
        operators.add("up");
        operators.add("down");
        operators.add("left");
        operators.add("right");
        operators.add("kill");
        operators.add("collect");
        operators.add("snap");
        Position ironmanPos = new Position(ix, iy);
        String[] gridSplit = grid.split(";");
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
//        intialize inherited initial state attribute
        System.out.println(tx + ", " + ty);
        System.out.println("Iron man " + ironmanPos.row + ", " + ironmanPos.column);

        this.initialState = (new EndgameState(ironmanPos, stones, warriors, 0, false)).toString();
        this.m = m;
        this.n = n;

    }

//    public void fillPlanet(String grid) {
//        String[] gridSplit = grid.split(";");
//        String[] stonesIndices = gridSplit[3].split(",");
//        String[] warriorsIndices = gridSplit[4].split(",");
//        for (char[] row : planet)
//            Arrays.fill(row, 'E');
//        planet[ironMan.row][ironMan.column] = 'I';
//        for (int i = 0; i < stonesIndices.length; i += 2) {
//            int sx = Integer.parseInt(stonesIndices[i]);
//            int sy = Integer.parseInt(stonesIndices[i + 1]);
//            planet[sx][sy] = 'S';
//        }
//        for (int i = 0; i < warriorsIndices.length; i += 2) {
//            int wx = Integer.parseInt(warriorsIndices[i]);
//            int wy = Integer.parseInt(warriorsIndices[i + 1]);
//            planet[wx][wy] = 'W';
//        }
////        for (int i = 0; i<planet.length;i++){
////            for (int j = 0; j < planet[i].length; j++){
////                System.out.print(planet[i][j] + " ");
////            }
////            System.out.println();
////        }
//    }

    public boolean adjWarriors(Position position, ArrayList<Position> warriors) {
        Position upWarriors = new Position(position.row - 1, position.column);
        Position downWarriors = new Position(position.row + 1, position.column);
        Position leftWarriors = new Position(position.row, position.column - 1);
        Position rightWarriors = new Position(position.row, position.column + 1);
        for (int i = 0; i< warriors.size(); i++){
            int x = warriors.get(i).row;
            int y = warriors.get(i).column;
            if(upWarriors.equals(x,y))
                return true;
            if(leftWarriors.equals(x,y))
                return true;
            if(rightWarriors.equals(x,y))
                return true;
            if(downWarriors.equals(x,y))
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
        for (int i = 0; i<stones.size(); i++){
            int x = stones.get(i).row;
            int y = stones.get(i).column;
            if(position.equals(x,y))
                return true;
        }
        return false;
    }

    public boolean validOperator(String operator, EndgameState state) {
        ArrayList<Position> warriors = state.warriors;
        ArrayList<Position> stones = state.stones;
        Position ironmanPosition = state.ironMan;

        switch (operator) {
            case "up":
                if (ironmanPosition.row - 1 >= 0) return true;
                else return false;
            case "down":
                if (ironmanPosition.row + 1 < m) return true;
                else return false;
            case "left":
                if (ironmanPosition.column - 1 >= 0) return false;
                else return true;
            case "right":
                if (ironmanPosition.column + 1 < n) return false;
                else return true;
            case "kill":
                if (adjWarriors(ironmanPosition, warriors)) return true;
                else return false;
            case "collect":
                if (stoneCell(ironmanPosition, stones)) return true;
                else return false;
            case "snap":
                return (state.damage < 100 && stones.isEmpty() && ironmanPosition.equals(tx, ty));
            default:
                return false;
        }
    }

    public ArrayList<Position> killWarriors(Position ironMan, ArrayList<Position> warriors) {
        ArrayList<Position> adjWarriors = new ArrayList<>();
        adjWarriors.add(new Position(ironMan.row - 1, ironMan.column));
        adjWarriors.add(new Position(ironMan.row + 1, ironMan.column));
        adjWarriors.add(new Position(ironMan.row, ironMan.column - 1));
        adjWarriors.add(new Position(ironMan.row, ironMan.column + 1));
        for (int i = 0; i < adjWarriors.size(); i++) {
            int index = findObject(adjWarriors.get(i), warriors);
            if(index != -1)
                warriors.remove(index);
        }
        return warriors;
    }

    public int findObject(Position element, ArrayList<Position> objects){
        for (int i = 0; i<objects.size(); i++){
            int x = objects.get(i).row;
            int y = objects.get(i).column;
            if(element.equals(x,y))
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {
//        Endgame endgame = new Endgame(5, 5, 1, 2);
//        endgame.fillPlanet("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3");
    }


    @Override
    SearchTreeNode transition(SearchTreeNode currentNode, String operator) {
        Position ironMan;
        SearchTreeNode parent = currentNode;
        int cost = currentNode.cost;
        int depth = currentNode.depth + 1;
        EndgameState currentState = currentNode.state;
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
                // TODO: remove all adjacent warriors
                ArrayList<Position> updatedWarriors = killWarriors(currentState.ironMan, currentState.warriors);
                pathCost = pathCost + (currentState.warriors.size() - updatedWarriors.size()) * 2;
                return new SearchTreeNode(new EndgameState(currentState.ironMan, currentState.stones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "collect":
                currentState.stones.remove(findObject(currentState.ironMan, currentState.stones));
                return new SearchTreeNode(new EndgameState(currentState.ironMan, currentState.stones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
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
        switch (operator) {
            case "up":
                if (adjWarriors(state.ironMan, state.warriors)) cost += 1;
                if (adjThanos(state.ironMan)) cost += 5;
                break;
            case "down":
                if (adjWarriors(state.ironMan, state.warriors)) cost += 1;
                if (adjThanos(state.ironMan)) cost += 5;
                break;
            case "left":
                if (adjWarriors(state.ironMan, state.warriors)) cost += 1;
                if (adjThanos(state.ironMan)) cost += 5;
                break;
            case "right":
                if (adjWarriors(state.ironMan, state.warriors)) cost += 1;
                if (adjThanos(state.ironMan)) cost += 5;
                break;
            //TODO: remove kill cost
            case "kill":
                cost += 0;
            case "collect":
                cost += 3;
            case "snap":
                cost += 0;
        }

        return currentNode.cost + cost;
    }

    @Override
    Queue<SearchTreeNode> BF(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        // TODO: prioritize the choice of the operators
        if (validOperator("snap", currentNode.state)){
            ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, "snap"));
//            System.out.println("Snap");
        }
        else if (validOperator("collect", currentNode.state)) {
            ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, "collect"));
//            System.out.println("Collect");
        } else if (validOperator("kill", currentNode.state)) {
            ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, "kill"));
//            System.out.println("Kill");
        } else {
            for (int i = 0; i < operators.size() - 3; i++) {
//                System.out.println("Entered Operator Actions " + operators.get(i));
                if (validOperator(operators.get(i), currentNode.state)) {
                    ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, operators.get(i)));
//                    System.out.println(nodes.size());
                }
            }
        }
        return nodes;
    }

    @Override
    Queue<SearchTreeNode> DF(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        return null;
    }

    @Override
    Queue<SearchTreeNode> UC(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        return null;
    }

    @Override
    Queue<SearchTreeNode> ID(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        return null;
    }
}
