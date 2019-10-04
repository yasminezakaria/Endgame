public class IronMan {
    int row;
    int column;
    int damage;

    public IronMan(int row, int column) {
        this.row = row;
        this.column = column;
        this.damage = 0;
    }

    public void up(){
        this.row--;

    }
    public void down(){
        this.row++;
    }
    public void left(){
        this.column--;

    }
    public void right(){
        this.column++;

    }
    public void collect(){

    }
    public void kill(){

    }
    public void snap(){

    }
}
