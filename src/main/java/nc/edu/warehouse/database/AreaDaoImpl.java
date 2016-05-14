package nc.edu.warehouse.database;

import nc.edu.warehouse.database.daos.AreaDao;
import nc.edu.warehouse.database.tables.Area;
import nc.edu.warehouse.database.tables.Box;
import nc.edu.warehouse.database.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaDaoImpl implements AreaDao {

    private int[][] matr = new int[6][7];
    private Connection connection = ConnectionFactory.getConnection();

    @Override
    public List<Box> getBoxes(int areaId) {
        String query = "SELECT * FROM box where area_id=" + areaId + ";";
        List<Box> boxes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)
        ) {
            while (rs.next()) {
                boxes.add(new Box(
                        rs.getInt("id"), rs.getInt("area_id"),
                        rs.getInt("box_size"), rs.getInt("x"),
                        rs.getInt("y"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boxes;
    }

    @Override
    public List<Area> getAreas() {
        String query = "select * from area";
        ArrayList<Area> areas = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)
        ) {
            while (rs.next()) {
                areas.add(new Area(rs.getInt("area_id"), rs.getString("area_name"), rs.getInt("rem_space")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return areas;
    }


    @Override
    public void update(Area area) {
        String query = "update area set rem_space=" + area.getRemSpace() + " where area_id=" + area.getAreaId();
        try (Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int[][] getLocationMatrix(Area area) {
        String query = "select * from box b where b.area_id=" + area.getAreaId();
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr[i].length; j++) {
                matr[i][j] = -1;
            }
        }
        try (Statement boxStatement = connection.createStatement();
             ResultSet boxResult = boxStatement.executeQuery(query)
        ) {
            while (boxResult.next()) {
                int x = boxResult.getInt("x");
                int y = boxResult.getInt("y");
                int size = boxResult.getInt("box_size");
                int id = boxResult.getInt("id");
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        matr[i + x][j + y] = id;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matr;
    }

    public Area getNext(int id) {
        Area area = new Area();
        String query = "select * from area where area_id=" + id;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                area.setAreaId(resultSet.getInt("area_id"));
                area.setAreaName(resultSet.getString("area_name"));
                area.setRemSpace(resultSet.getInt("rem_space"));
                return area;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
}
