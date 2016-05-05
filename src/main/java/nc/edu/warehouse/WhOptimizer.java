package nc.edu.warehouse;

import nc.edu.warehouse.database.AreaDaoImpl;
import nc.edu.warehouse.database.BoxDaoImpl;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

public class WhOptimizer {
    private Area[] areas;
    BoxDaoImpl boxDao = new BoxDaoImpl();
    AreaDaoImpl areaDao = new AreaDaoImpl();

    public WhOptimizer(Area[] areas){
        this.areas = areas;
    }

    public boolean placeBox(int size){
        Box box = new Box(size);
        int id = 0;
        Area area = null;
        while((area = areaDao.getNext(id)) != null){
            box.setX(0);
            box.setY(0);
            if(areaDao.canPlace(area,box)) {
                box.setAreaId(area.getAreaId());
                boxDao.create(box);
                areaDao.update(area);
                break;
            }
            id++;
        }


        return true;
    }
}
