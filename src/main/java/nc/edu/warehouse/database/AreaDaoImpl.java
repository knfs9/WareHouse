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

    private static final Logger log = Logger.getLogger(AreaDaoImpl.class);
    private int[][] matr = new int[6][7];

    @Override
    public List<Box> getBoxes(String areaName) {
        String query = "SELECT * FROM box where area_id=b;";
        List<Box> boxes = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
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
    public List<Box> getBoxes(Area area) {
        return null;
    }

    @Override
    public List<Box> getBoxes(int areaId) {
        String query = "SELECT * FROM box where area_id=" + areaId + ";";
        List<Box> boxes = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()
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

    public List<Area> getAreas() {
        String query = "select * from area";
        ArrayList<Area> areas = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()
        ) {
            statement.execute();
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
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)

        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteBox(int boxId) {
        String query = "delete from box where id=" + boxId;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public int[][] getLocationMatrix(Area area) {
        String query = "select * from box b where b.area_id=" + area.getAreaId();
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr[i].length; j++) {
                matr[i][j] = -1;
            }
        }
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement boxStatement = connection.prepareStatement(query);
             ResultSet boxResult = boxStatement.executeQuery()
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

    public boolean canPlace(Area area, Box box) {
        String query = "select * from  area a where a.area_id=" + area.getAreaId();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement areaStatement = connection.prepareStatement(query);
             ResultSet areaResult = areaStatement.executeQuery()
        ) {
            while (areaResult.next()) {
                int rem_space = areaResult.getInt("rem_space");
                if (rem_space < Constants.SMALL_BOX * Constants.SMALL_BOX) {
                    log.info("area is full");
                    return false;
                }
            }
            int[][] tempMatr = getLocationMatrix(area);
            if (tempMatr[box.getX()][box.getY()] != -1) {
                log.info(area.getAreaName() + ": Can't place box at x:" + box.getX() + ", y:" + box.getY());
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Area getNext(int id) {
        Area area = new Area();
        String query = "select * from area where area_id=" + id;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
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
