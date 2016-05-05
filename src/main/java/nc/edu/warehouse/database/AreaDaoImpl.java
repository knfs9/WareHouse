package nc.edu.warehouse.database;

import nc.edu.warehouse.Constants;
import nc.edu.warehouse.database.daos.AreaDao;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AreaDaoImpl implements AreaDao {

    public static final Logger log = Logger.getLogger(AreaDaoImpl.class);


    @Override
    public List<Box> getBoxes(Area area) {
        String query = "SELECT * FROM box;";
        List<Box> boxes = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
        ){
            while (resultSet.next()){
                Box box = new Box(resultSet.getInt("box_size"));
                box.setId(resultSet.getInt("id"));
                box.setAreaId(resultSet.getInt("area_id"));
                box.setX(resultSet.getInt("x"));
                box.setY(resultSet.getInt("y"));
                boxes.add(box);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boxes;
    }

    @Override
    public void update(Area area) {
        String query = "update area set rem_space=" + area.getRemSpace() + "where area_id=" + area.getAreaId();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Area area) {

    }

    @Override
    public void create(Area area) {

    }

    @Override
    public List<Area> getAll() {
        return null;
    }

    @Override
    public Area getArea(int id) {
        return null;
    }


    public boolean canPlace(Area area,Box box){
        String query1 = "select * from  area a where a.area_id=" + area.getAreaId();
        int[][] arr = new int[6][7];
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[0].length; j++){
                arr[i][j] = -1;
            }
        }
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement areaStatement = connection.prepareStatement(query1);
            ResultSet areaResult = areaStatement.executeQuery();
        ) {
            while(areaResult.next()) {
                int rem_space = areaResult.getInt("rem_space");
                if(rem_space < Constants.SMALL_BOX * Constants.SMALL_BOX) {
                    log.info("area is full");
                    return false;
                }
            }
            String query2 = "select * from box b where b.area_id=" + area.getAreaId();
            try(
                PreparedStatement boxStatement = connection.prepareStatement(query2);
                ResultSet boxResult = boxStatement.executeQuery();
            ){
                while(boxResult.next()) {
                    int x = boxResult.getInt("x");
                    int y = boxResult.getInt("y");
                    int size = boxResult.getInt("box_size");
                    int id = boxResult.getInt("id");
                    for (int i = x; i < size; i++) {
                        for (int j = y; j < size; j++) {
                            arr[i][j] = id;
                        }
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            if(arr[box.getX()][box.getY()] != -1) {
                log.info(area.getAreaName() + ": Can't place box at x:" + box.getX() + ", y:" + box.getY());
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Area getNext(int id){
        Area area = new Area();
        String query = "select * from area where area_id=" + id;
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
        ){
            while(resultSet.next()) {
                area.setAreaId(id);
                area.setAreaName(resultSet.getString("area_name"));
                area.setRemSpace(resultSet.getInt("rem_space"));
            }
            return area;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
