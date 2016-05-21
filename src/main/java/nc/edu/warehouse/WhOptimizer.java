package nc.edu.warehouse;

import nc.edu.warehouse.database.AreaDaoImpl;
import nc.edu.warehouse.database.BoxDaoImpl;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class WhOptimizer {

    private BoxDaoImpl boxDao = new BoxDaoImpl();
    private AreaDaoImpl areaDao = new AreaDaoImpl();
    private String actionInfo = "Box placed in....";
    private static final Logger log = Logger.getLogger(WhOptimizer.class);

    public void placeBox(int size) {
        Box box = new Box(size);
        int id = 0;
        int[][] locationMatrix;
        Area area;
        //массив для координат начала пустоты
        int[] starts = new int[6];
        //массив для количества пустот в строке
        int[] allOccurs = new int[6];
        boolean checkNextArea = false;
        boolean stopFinding = false;
        boolean checkNextPos = false;

        while ((area = areaDao.getNext(id)) != null) {

            locationMatrix = areaDao.getLocationMatrix(area);
            Arrays.fill(starts, -1);
            Arrays.fill(allOccurs, 0);
            for (int i = 0; i < locationMatrix.length; i++) {
                for (int j = 0; j < locationMatrix[0].length; j++) {
                    if (locationMatrix[i][j] == -1) {
                        starts[i] = j;
                        break;
                    }
                }
            }

            for (int i = 0; i < locationMatrix.length; i++) {
                for (int j = 0; j < locationMatrix[0].length; j++) {
                    if (locationMatrix[i][j] == -1)
                        allOccurs[i]++;
                }
            }

            int boxSize = box.getSize();
            for (int i = 0; i < locationMatrix.length; i++) {
                for (int j = 0; j < locationMatrix[i].length; j++) {
                    if (locationMatrix[i][j] == -1) {
                        if (allOccurs[i] >= boxSize) {
                            if (!checkOverlap(locationMatrix, i, j, boxSize) // проверка нахлеста и занятости клетки
                                    && ((i + boxSize) <= locationMatrix.length)   // не вышли за границы по y
                                    && ((j + boxSize) <= locationMatrix[0].length)) { // не вышли за границы по x
                                box.setX(i);
                                box.setY(j);
                                if ((allOccurs[i] - boxSize) != 1) {// нет пустого места)
                                    checkAndPlace(area, box);
                                    stopFinding = true;
                                    checkNextPos = false;
                                    break;
                                } else if (area.getRemSpace() <= 14) {
                                    checkAndPlace(area, box);
                                    stopFinding = true;
                                    checkNextPos = false;
                                    break;
                                }
                            } else {
                                stopFinding = false;
                                checkNextPos = true;
                                break;
                            }
                        }
                    }
                }
                if (checkNextPos)
                    continue;
                if (checkNextArea || stopFinding)
                    break;
            }
            if (stopFinding) {
                break;
            }
            id++;
            actionInfo = "Can't place box";
            log.info(actionInfo);
        }
    }

    /*
       return true if have overlap or cell is occupied
     */
    private boolean checkOverlap(int[][] locationsMatrix, int x, int y, int size) {

        if (locationsMatrix[x][y] != -1)
            return true;
        for (int i = x; i < locationsMatrix.length; i++) {
            for (int j = y; j < locationsMatrix[i].length; j++) {
                if (i >= size * size || j >= size * size)
                    break;
                if (locationsMatrix[i][j] != -1)
                    return true;
            }
        }
        return false;
    }

    public void deleteAndUpdate(int boxId){
        Box box = boxDao.getBox(boxId);
        Area area = boxDao.getAreaByBoxId(boxId);
        boxDao.deleteBox(boxId);
        area.setRemSpace(area.getRemSpace() + (box.getSize() * box.getSize()));
        areaDao.update(area);
    }

    private void checkAndPlace(Area area, Box box) {
        box.setAreaId(area.getAreaId());
        boxDao.create(box);
        area.setRemSpace(area.getRemSpace() - (box.getSize() * box.getSize()));
        areaDao.update(area);
        actionInfo = "Box placed in " + area.getAreaName();
        log.info("Box placed at x:" + box.getX() + ", y:" + box.getY() + " in " + area.getAreaName());
    }

    public  String getActionInfo(){
        return actionInfo;
    }
}
