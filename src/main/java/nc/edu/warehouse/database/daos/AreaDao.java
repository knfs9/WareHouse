package nc.edu.warehouse.database.daos;

import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.util.List;

public interface AreaDao {
    List<Box> getBoxes(Area area);
    List<Box> getBoxes(String areaName);
    List<Box> getBoxes(int areaId);
    void update(Area area);
    void deleteBox(int boxId);
    void create(Area area);
    List<Area> getAll();
    Area getArea(int id);


}
