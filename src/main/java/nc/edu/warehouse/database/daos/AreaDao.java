package nc.edu.warehouse.database.daos;

import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.util.List;

public interface AreaDao {
    List<Box> getBoxes(Area area);
    void update(Area area);
    void delete(Area area);
    void create(Area area);
    List<Area> getAll();
    Area getArea(int id);

}
