public class Position {
    int row;
    int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean equals(int x, int y) {
        return (row == x && column == y);
    }
}
