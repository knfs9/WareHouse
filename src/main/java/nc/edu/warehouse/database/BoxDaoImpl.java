package nc.edu.warehouse.database;

import nc.edu.warehouse.database.daos.BoxDao;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoxDaoImpl implements BoxDao {

    @Override
    public List<Box> getAll() {
//        String query = "SELECT * FROM box;";
//        List<Box> boxes = new ArrayList<>();
//        try(Connection connection = ConnectionFactory.getConnection();
//            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet resultSet = statement.executeQuery();
//        ){
//            while (resultSet.next()){
//                Box box = new Box();
//                box.setId(resultSet.getInt("id"));
//                box.setAreaId(resultSet.getInt("area_id"));
//                box.setSize(resultSet.getInt("box_size"));
//                box.setX(resultSet.getInt("x"));
//                box.setY(resultSet.getInt("y"));
//                boxes.add(box);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return boxes;
        return null;
    }

    @Override
    public Area getArea(Box box) {
        String query = "select * from area a where a.area_id = " + box.getAreaId()  ;
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()){
                int id = resultSet.getInt("area_id");
                String area_name = resultSet.getString("area_name");
                int rem_space = resultSet.getInt("rem_space");

                return new Area(id, area_name, rem_space);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Box box) {
        String query = "insert into box(area_id, box_size, x, y) VALUES("   + box.getAreaId()   + ","
                                                                            + box.getSize()     + ","
                                                                            + box.getX()        + ","
                                                                            + box.getY()        + ")" ;

        getArea(box).setRemSpace(getArea(box).getRemSpace() - box.getSize() * box.getSize());
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}
