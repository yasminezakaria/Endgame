import java.util.ArrayList;
import java.util.Arrays;

public class EndgameState {
    Position ironMan;
    ArrayList<Position> stones;
    ArrayList<Position> warriors;
    boolean snap;
    int heuristicCost = 0;
//    Count of all class instances
    static int c;
//    ID of the instance which the number of the instance
    int ID;

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public EndgameState(Position ironMan, ArrayList<Position> stones, ArrayList<Position> warriors, boolean snap) {
        this.ironMan = ironMan;
        this.stones = stones;
        this.warriors = warriors;
        this.snap = snap;
        this.ID = c;
        c++;
    }

    public EndgameState(String state) {
        // fill EndgameState from String given
        // needed in the Search_generic method
        String[] gridSplit = state.split(";");
        String[] iSplit = gridSplit[0].split(",");
        this.ironMan  = new Position(Integer.parseInt(iSplit[0]+""), Integer.parseInt(iSplit[1]+ ""));
        String[] stonesIndices = gridSplit[1].split(",");
        String[] warriorsIndices = gridSplit[2].split(",");
        ArrayList<Position> warriors = new ArrayList<>();
        ArrayList<Position> stones = new ArrayList<>();
        for (int i = 0; i < stonesIndices.length; i += 2) {
            int sx = Integer.parseInt(stonesIndices[i]);
            int sy = Integer.parseInt(stonesIndices[i + 1]);
            stones.add(new Position(sx, sy));
        }
        for (int i = 0; i < warriorsIndices.length; i += 2) {
            int wx = Integer.parseInt(warriorsIndices[i]);
            int wy = Integer.parseInt(warriorsIndices[i + 1]);
            warriors.add(new Position(wx, wy));
        }
        this.snap = false;
        this.warriors = warriors;
        this.stones = stones;
    }

    @Override
    public String toString() {
        String stonesString = "";
        String warriorsString = "";
        for (int i = 0; i < stones.size(); i++) {
            stonesString += stones.get(i).row + "," + stones.get(i).column;
            if (i != stones.size() - 1)
                stonesString += ",";
        }
        for (int i = 0; i < warriors.size(); i++) {
            warriorsString += warriors.get(i).row + "," + warriors.get(i).column;
            if (i != warriors.size() - 1)
                warriorsString += ",";
        }
        return ironMan.row + "," + ironMan.column + ";" + stonesString + ";" + warriorsString;

    }

    public boolean isEqual(EndgameState state) {
        if (!state.ironMan.equals(this.ironMan.row, this.ironMan.column)) return false;
        if (state.snap != this.snap) return false;
        if (state.warriors.size() != this.warriors.size()) return false;
        else {
            for (int i = 0; i < state.warriors.size(); i++) {
                boolean found = false;
                for (int j = 0; j < this.warriors.size(); j++) {
                    if (state.warriors.get(i).equals(this.warriors.get(j).row, this.warriors.get(j).column))
                        found = true;

                }
                if (!found)
                    return false;
            }
        }
        if (state.stones.size() != this.stones.size()) return false;
        else {
            for (int i = 0; i < state.stones.size(); i++) {
                boolean found = false;
                for (int j = 0; j < this.stones.size(); j++) {
                    if (state.stones.get(i).equals(this.stones.get(j).row, this.stones.get(j).column))
                        found = true;

                }
                if (!found)
                    return false;
            }
        }
        return true;
    }

    public void visualize(int m, int n, int tx, int ty) {
        char[][] planet = new char[m][n];
        if (planet == null) {
            planet = new char[m][n];
        }
        for (char[] row : planet)
            Arrays.fill(row, '-');
        planet[ironMan.row][ironMan.column] = 'I';
        planet[tx][ty] = 'T';
        for (int i = 0; i < stones.size(); i++) {
            Position stone = stones.get(i);
            planet[stone.row][stone.column] = 'S';
        }
        for (int i = 0; i < warriors.size(); i++) {
            Position warrior = warriors.get(i);
            planet[warrior.row][warrior.column] = 'W';
        }
        for (int i = 0; i < planet.length; i++) {
            for (int j = 0; j < planet[i].length; j++) {
                System.out.print(planet[i][j] + " ");
            }
            System.out.println();
        }
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;
        for (Position e : warriors)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        for (Position e : stones)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        hashCode = prime * hashCode + ironMan.row;
        hashCode = prime * hashCode + ironMan.column;
        hashCode = prime * hashCode + heuristicCost;
        hashCode = prime * hashCode + (snap ? 1 : 0);
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EndgameState other = (EndgameState) obj;
        return this.isEqual(other);


    }
}
