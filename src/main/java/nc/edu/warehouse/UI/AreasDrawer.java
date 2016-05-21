package nc.edu.warehouse.UI;

import nc.edu.warehouse.WhOptimizer;
import nc.edu.warehouse.database.AreaDaoImpl;
import nc.edu.warehouse.database.BoxDaoImpl;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.util.List;

public class AreasDrawer {
    private static BoxDaoImpl boxDao = new BoxDaoImpl();
    private static AreaDaoImpl areaDao = new AreaDaoImpl();

    private static String smallBox(int leftPos, int topPos) {
        return "<div class=\"smallBox\" style=\"position: absolute; left: "
                + leftPos + "px; top: "
                + topPos + "px \">2x2</div>";
    }

    private static String middleBox(int leftPos, int topPos) {
        return "<div class=\"middleBox\" style=\"position: absolute; left: "
                + leftPos + "px; top: "
                + topPos + "px \">3x3</div>";
    }

    private static String bigBox(int leftPos, int topPos) {
        return " <div class=\"bigBox\" style=\"position: absolute; left: "
                + leftPos + "px; top: "
                + topPos + "px \">4x4</div>";
    }

    public static String drawAreas() {
        StringBuilder areaString = new StringBuilder();
        List<Area> areas = areaDao.getAreas();
        int counter = 0;
        int areaBottomPos = 250;
        int areaLeftPos = 20;
        int multiplier = 50;

        for (Area area : areas) {

            if (counter >= 2) {
                areaBottomPos -= 150;
                areaLeftPos = 20;
                areaString.append("<div class=\"boxDiv\" style=\"position: absolute; bottom: -" + areaBottomPos + "px; " +
                        "left: " + areaLeftPos + "px\">");
                areaLeftPos = 400;
            } else {
                areaString.append("<div class=\"boxDiv\" style=\"position: absolute; bottom: " + areaBottomPos + "px; " +
                        "left: " + areaLeftPos + "px\">");
                areaLeftPos = 400;
            }
            //get boxes in one of area with areaId
            List<Box> currentBoxes = areaDao.getBoxes(area.getAreaId());

            for (Box box : currentBoxes) {
                switch (box.getSize()) {
                    case 2:
                        areaString.append(smallBox(box.getY() * multiplier,
                                box.getX() * multiplier));
                        break;
                    case 3:
                        areaString.append(middleBox(box.getY() * multiplier,
                                box.getX() * multiplier));
                        break;
                    case 4:
                        areaString.append(bigBox(box.getY() * multiplier,
                                box.getX() * multiplier));
                        break;
                }
            }
            areaString.append(area.getAreaName() + "</div>");
            counter++;
        }

        return areaString.toString();
    }

}
