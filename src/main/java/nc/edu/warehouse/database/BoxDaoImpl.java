package nc.edu.warehouse.database;

import nc.edu.warehouse.database.daos.BoxDao;

import nc.edu.warehouse.database.tables.Box;
import nc.edu.warehouse.database.utils.ConnectionFactory;

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
        try (Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


}
