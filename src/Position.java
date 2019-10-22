public class Position {
    int row;
    int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Position) {
            return (row == ((Position) object).row && column == ((Position) object).column);
        }
        return false;
    }
    public boolean equals(int x, int y) {
        return (row == x && column == y);
    }
}
