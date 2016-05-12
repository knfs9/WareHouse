package nc.edu.warehouse.database.tables;

public class Box {

    private int id;
    private int area_id;
    private int size;
    private int x;
    private int y;
    public Box(int size){
        this.size = size;
    }

    public Box(int id, int area_id, int size, int x, int y) {
        this.id = id;
        this.area_id = area_id;
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public int getSquare(){
        return size*size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAreaId(int area_id) {
        this.area_id = area_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize(){
        return size;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getAreaId() {
        return area_id;
    }

    public int getX() {
        return x;
    }
}
