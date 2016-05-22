package nc.edu.warehouse.UI;


import nc.edu.warehouse.database.AreaDaoImpl;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.util.List;

public class AreasDrawer {
    private static AreaDaoImpl areaDao = new AreaDaoImpl();
    private static boolean isFirstTime = true;
    private static String smallBoxColor;
    private static String middleBoxColor;
    private static String bigBoxColor;

    private static String smallBox(int leftPos, int topPos) {
        return "<div class=\"smallBox\" style=\"position: absolute; left: "
                + leftPos + "px; top: "
                + topPos + "px; background-color:" + smallBoxColor + "\">2x2</div>";
    }

    private static String middleBox(int leftPos, int topPos) {
        return "<div class=\"middleBox\" style=\"position: absolute; left: "
                + leftPos + "px; top: "
                + topPos + "px; background-color:" + middleBoxColor +  "\">3x3</div>";
    }

    private static String bigBox(int leftPos, int topPos) {
        return " <div class=\"bigBox\" style=\"position: absolute; left: "
                + leftPos + "px; top: "
                + topPos + "px; background-color:" + bigBoxColor + "\">4x4</div>";
    }

    public static String removeZone(String html) {

        return html;
    }

    private static String getRandomColorInHEX() {
        String[] letters;
        letters = "0123456789ABCDEF".split("");
        String code = "#";
        for (int i = 0; i < 6; i++) {
            double ind = Math.random() * 15;
            int index = (int) Math.round(ind);
            code += letters[index];
        }
        return code;
    }
    public static void generateColors(){
        smallBoxColor = getRandomColorInHEX();
        middleBoxColor = getRandomColorInHEX();
        bigBoxColor = getRandomColorInHEX();
    }

    public static String drawAreas() {
        StringBuilder areaString = new StringBuilder();
        List<Area> areas = areaDao.getAreas();

        int counter = 1;
        int areaBottomPos = 250;
        int areaLeftPos = 20;
        int multiplier = 50;

        // we wont change colors, after refreshing the page

        if(isFirstTime){
            generateColors();
            isFirstTime = false;
        }

        for (Area area : areas) {
            areaString.append("<div class=\"boxDiv\" style=\"position: absolute; bottom: " + areaBottomPos + "px; " +
                    "left: " + areaLeftPos + "px\">");
            if (counter >= 2) {
                counter = 0;
                areaLeftPos = 20;
                areaBottomPos -= 350;
            } else {
                areaLeftPos += 380;
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
