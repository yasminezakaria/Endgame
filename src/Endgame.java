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
        tx = gridSplit[2].charAt(0);
        ty = gridSplit[2].charAt(2);
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
        this.initialState = (new EndgameState(ironmanPos, stones, warriors, 0, false)).toString();
        System.out.println("1- " + initialState);
        this.m = m;
        this.n = n;
//        this.planet = new char[m][n];
//        this.ironMan = new IronMan(ix, iy);
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
        if (warriors.contains(new Position(position.row - 1, position.column)))
            return true;
        if (warriors.contains(new Position(position.row + 1, position.column)))
            return true;
        if (warriors.contains(new Position(position.row, position.column - 1)))
            return true;
        if (warriors.contains(new Position(position.row, position.column + 1)))
            return true;
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
        if (stones.contains(position))
            return true;
        else
            return false;
    }

    public boolean validOperator(String operator, EndgameState state) {
        ArrayList<Position> warriors = state.warriors;
        ArrayList<Position> stones = state.stones;
        Position ironmanPosition = state.ironMan;

        switch (operator) {
            case "up":
                if (!(ironmanPosition.row < 0)) return false;
                else return true;
            case "down":
                if (!(ironmanPosition.row > m - 1)) return false;
                else return true;
            case "left":
                if (!(ironmanPosition.column < 0)) return false;
                else return true;
            case "right":
                if (!(ironmanPosition.column > n - 1)) return false;
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
//    public String game(SearchStrategy searchStrategy){
//        switch (searchStrategy){
//            case BF: return BF();
//            case DF: return DF();
//            case ID: return ID();
//            case UC: return UC();
//            default: return "";
//        }
//    }

    /*
     * Class should have:
     * 1- grid variable String[][]
     * 2- m and n variables
     * 3- IronMan object
     * 4- fill grid method
     * 5- Expand node method
     * */
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
                currentState.warriors.remove(currentState.ironMan);
                return new SearchTreeNode(new EndgameState(currentState.ironMan, currentState.stones, currentState.warriors, pathCost - cost, false), parent, operator, pathCost, depth);
            case "collect":
                currentState.stones.remove(currentState.ironMan);
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
            case "kill":
                cost += 2;
            case "collect":
                cost += 3;
            case "snap":
                cost += 0;
        }

        return state.damage + cost;
    }

    @Override
    Queue<SearchTreeNode> BF(Queue<SearchTreeNode> nodes, SearchTreeNode currentNode) {
        System.out.println("Entered BF");
        for (int i = 0; i < operators.size(); i++) {
            if (validOperator(operators.get(i), currentNode.state)) {
                ((LinkedList<SearchTreeNode>) nodes).addLast(transition(currentNode, operators.get(i)));
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
