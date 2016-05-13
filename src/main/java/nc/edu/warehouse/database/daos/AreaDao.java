package nc.edu.warehouse.database.daos;

import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.util.List;

public interface AreaDao {
    List<Box> getBoxes(int areaId);
    void update(Area area);
    void deleteBox(int boxId);
    List<Area> getAreas();
}
