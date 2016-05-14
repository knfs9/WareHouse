package nc.edu.warehouse.database.daos;

import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.util.List;

public interface BoxDao {
    List<Box> getAll();
    void create(Box box);
    void deleteBox(int boxId);
}
