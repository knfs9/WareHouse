package nc.edu.warehouse;

import com.sun.org.apache.xpath.internal.SourceTree;
import nc.edu.warehouse.database.AreaDaoImpl;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;
import org.apache.commons.lang.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestWarehouse {
    private static Area area1 = new Area();
    private static Area area2 = new Area();
    private static Area area3 = new Area();
    private static WhOptimizer whOptimizer = new WhOptimizer(new Area[]{area1, area2, area3});
    private static AreaDaoImpl areaDao = new AreaDaoImpl();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.print(">");
        while (!(str = reader.readLine()).equalsIgnoreCase("q")) {
            str = str.trim().toLowerCase();
            if (str.contains("box")) {
                String temp[] = str.split(" ");
                whOptimizer.placeBox(Integer.valueOf(temp[1]));
            } else if (str.contains("get")) {
                String temp[] = str.split(" ");
                if (temp.length == 1) {
                    printAreas();
                } else if (temp.length == 2) {
                    if (NumberUtils.isDigits(temp[1])) {
                        printBoxes(Integer.valueOf(temp[1]));
                    }
                }
            } else if (str.contains("rem")) {
                String temp[] = str.split(" ");
                areaDao.deleteBox(Integer.valueOf(temp[1]));
                System.out.println("");
            } else {
                System.out.println("Unknown command " + "\"" + str + "\"");
            }
            System.out.print(">");
        }
    }

    private static void addBox() {

    }

    private static void printAreas() {
        List<Area> areas = areaDao.getAreas();
        for (Area area : areas) {
            System.out.println("Area Name: \"" + area.getAreaName() + "\" Area id: " + area.getAreaId()
                    + " Rem space: " + area.getRemSpace());
        }
    }

    private static void printBoxes(int areaId) {
        List<Box> boxes = areaDao.getBoxes(areaId);
        System.out.println("Area id: " + areaId);
        System.out.println("-------------------------------");
        System.out.println("Box id\tBox size\tx\ty");
        for (Box box : boxes) {
            System.out.println(box.getId() + "\t\t\t" + box.getSize() + "\t\t"
                    + box.getX() + "\t" + box.getY());
        }
    }
}
