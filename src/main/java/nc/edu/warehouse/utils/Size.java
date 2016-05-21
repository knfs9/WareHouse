package nc.edu.warehouse.utils;

public enum Size {
    SMALL(2), MID(3), BIG(4);
    private int size;
    Size(int size){
        this.size = size;
    }
    public int getSize(){
        return size;
    }
}
