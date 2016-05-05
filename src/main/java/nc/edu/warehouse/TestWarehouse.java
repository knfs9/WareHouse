package nc.edu.warehouse;

import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestWarehouse {
    private static Area area1 = new Area();
    private static Area area2 = new Area();
    private static Area area3 = new Area();
    private static WhOptimizer whOptimizer = new WhOptimizer(new Area[]{area1, area2, area3});
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        System.out.print(">");
        while(!(str = reader.readLine()).equalsIgnoreCase("q")){
            if(str.trim().toLowerCase().contains("box")){
                String temp[] = str.split(" ");
                whOptimizer.placeBox(Integer.valueOf(temp[1]));
            }else{
                System.out.println("Unknown command " + "\"" + str + "\"");
            }
            System.out.print(">");
        }
    }

    private static void addBox(){

    }

    private void parseArgs(String arg){

    }
}
