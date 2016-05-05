package nc.edu.warehouse.database.tables;

import org.apache.log4j.Logger;

public class Area {
    private static final int SIZE_X = 6;
    private static final int SIZE_Y = 7;
    private int arr[][] = new int[SIZE_X][SIZE_Y];
    private boolean isFull;
    private boolean isEmpty;
    private int area_id;
    private String area_name;
    private int rem_space = SIZE_X * SIZE_Y;

    private static final Logger log = Logger.getLogger(Area.class);

    public Area(){
    }
    public Area(int area_id, String area_name, int rem_space ){
        this.area_id   = area_id;
        this.area_name = area_name;
        this.rem_space = rem_space;
    }

    public int getAreaId() {
        return area_id;
    }

    public void setAreaId(int area_id) {
        this.area_id = area_id;
    }

    public String getAreaName() {
        return area_name;
    }

    public void setAreaName(String area_name) {
        this.area_name = area_name;
    }

    public int getRemSpace() {
        return rem_space;
    }

    public void setRemSpace(int rem_space) {
        this.rem_space = rem_space;
    }

    public void placeBox(Box box){
        if(arr[box.getX()][box.getY()] == -1) {
            log.error("Can't place box at x:" + box.getX() + ", y:" + box.getY());
            return;
        }
        for(int i = box.getX(); i < box.getSize(); i++){
            for(int j = box.getY(); j < box.getSize(); j++){
                arr[i][j] = box.getId();
            }

        }
        rem_space -= box.getSize()*box.getSize();
        log.info("Box placed at x:" + box.getX() + ", y:" + box.getY());
    }

    public boolean canPlace(int x, int y){
        if(arr[x][y] == 1)
            return false;
        return true;
    }

    public boolean isFull(){
        return isFull;
    }

    public boolean isEmpty(){
        return isEmpty;
    }
}
