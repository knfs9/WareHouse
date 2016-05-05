package nc.edu.warehouse.database.daos;

import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.util.List;

public interface BoxDao {
    List<Box> getAll();
    Area getArea(Box box);
    void create(Box box);
}
