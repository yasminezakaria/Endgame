import java.util.ArrayList;

public class EndgameState {
    Position ironMan;
    ArrayList<Position> stones;
    ArrayList<Position> warriors;
    int damage;
    boolean snap;

    public EndgameState(Position ironMan, ArrayList<Position> stones, ArrayList<Position> warriors, int damage, boolean snap) {
        this.ironMan = ironMan;
        this.stones = stones;
        this.warriors = warriors;
        this.damage = damage;
        this.snap = snap;
    }

    public EndgameState(String state) {
        // fill EndgameState from String given
        // needed in the Search_generic method
        System.out.println(state);
        String[] gridSplit = state.split(";");
        this.ironMan = new Position(gridSplit[0].charAt(0), gridSplit[0].charAt(1));
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
        this.damage = 0;
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
        System.out.println(stonesString);
        return ironMan.row + "," + ironMan.column + ";" + stonesString + ";" + warriorsString;


    }
}
