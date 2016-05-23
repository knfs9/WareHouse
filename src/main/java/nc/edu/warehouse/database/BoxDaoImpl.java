package nc.edu.warehouse.database;

import nc.edu.warehouse.database.daos.BoxDao;

import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;
import nc.edu.warehouse.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoxDaoImpl implements BoxDao {
    private Connection connection = ConnectionFactory.getConnection();

    @Override
    public List<Box> getAll() {
        String query = "SELECT * FROM box;";
        List<Box> boxes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                boxes.add(new Box(resultSet.getInt("id"),
                        resultSet.getInt("area_id"),
                        resultSet.getInt("box_size"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boxes;
    }

    @Override
    public void deleteBox(int boxId) {
        String query = "delete from box where id=" + boxId;
        //Continue from previous id
        String updateAuto = "alter table box AUTO_INCREMENT=" + getMinId();
        try (Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
            statement.executeUpdate(updateAuto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getMinId(){
        String query = "SELECT MIN(id) as minID from box";
        String minId = "";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                minId = resultSet.getString("minID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return minId;
    }

    public Area getAreaByBoxId(int boxId) {
        String query = "select * from area a, box b where b.id=" + boxId +
                " and a.area_id=b.area_id";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)
        ) {
            while (rs.next()) {
                return new Area(rs.getInt("area_id"), rs.getString("area_name"),
                        rs.getInt("rem_space"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Box box) {
        String query = "insert into box(area_id, box_size, x, y) VALUES(" + box.getAreaId() + ","
                + box.getSize() + ","
                + box.getX() + ","
                + box.getY() + ")";
        try (PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Box getBox(int boxId) {
        String query = "select * from box where id=" + boxId;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)
        ) {
            while(rs.next()) {
                return new Box(rs.getInt("id"), rs.getInt("area_id"),
                        rs.getInt("box_size"), rs.getInt("x"),
                        rs.getInt("y"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
