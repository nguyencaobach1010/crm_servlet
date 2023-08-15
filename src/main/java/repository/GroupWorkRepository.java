package repository;

import config.MysqlConfig;

import dto.GroupWorkDTO;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupWorkRepository {
    public List<GroupWorkDTO> findAllGroupWorks() {
        Connection connection = null;
        List<GroupWorkDTO> groupWorkDTOList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT j.id, j.name, j.start_date, j.end_date, j.leader_id, u.fullname AS leader_name " +
                    "FROM jobs j " +
                    "INNER JOIN users u " +
                    "ON u.id = j.leader_id";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groupWorkDTOList.add(groupWorkDTOHelper(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Error findAllGroupWork: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findAllGroupWork");
        }
        return groupWorkDTOList;
    }

    public boolean insertGroupWork(String name, Date startDate, Date endDate, int leaderId) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String insert = "INSERT INTO jobs(name, start_date, end_date, leader_id) VALUES(?, ?, ? ,?)";

            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, name);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setInt(4, leaderId);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at insertGroupWork: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "insertGroupWork");
        }
        return isSuccess;
    }

    public boolean deleteById(int id) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String delete = "DELETE FROM jobs j WHERE j.id = ?";

            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at deleteById(GroupWorkRepository): " + e.getMessage());;
        } finally {
            MysqlConfig.closeConnection(connection, "deleteById(GroupWorkRepository)");
        }
        return isSuccess;
    }

    public boolean update(int id, String name, Date startDate, Date endDate, int leaderId) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String insert = "UPDATE jobs j SET name = ?, start_date = ?, end_date = ?, leader_id = ? WHERE j.id = ?";

            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, name);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setInt(4, leaderId);
            statement.setInt(5, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at update(GroupWorkRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "update(GroupWorkRepository)");
        }
        return isSuccess;
    }

    public Optional<GroupWorkDTO> findById(int id) {
        Connection connection = null;
        Optional<GroupWorkDTO> optionalGroupWorkDTO = Optional.empty();
        try {
            String query = "SELECT j.id, j.name, j.start_date, j.end_date, j.leader_id, u.fullname AS leader_name " +
                    "FROM jobs j " +
                    "INNER JOIN users u " +
                    "ON u.id = j.leader_id " +
                    "WHERE j.id = ?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalGroupWorkDTO = Optional.of(groupWorkDTOHelper(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Error findById(GroupWorkRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findById(GroupWorkRepository)");
        }
        return optionalGroupWorkDTO;
    }

    public List<GroupWorkDTO> findByLeaderId(int leaderId) {
        Connection connection = null;
        List<GroupWorkDTO> groupWorkDTOList = new ArrayList<>();

        try {
            String query = "SELECT j.id, j.name, j.start_date, j.end_date, j.leader_id, u.fullname AS leader_name " +
                    "FROM jobs j " +
                    "INNER JOIN users u " +
                    "ON u.id = j.leader_id " +
                    "WHERE j.leader_id = ?";

            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, leaderId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groupWorkDTOList.add(groupWorkDTOHelper(resultSet));
            }
        } catch (Exception e) {
            System.out.println("Error findByLeaderId(GroupWorkRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findByLeaderId(GroupWorkRepository)");
        }
        return groupWorkDTOList;
    }

    private GroupWorkDTO groupWorkDTOHelper(ResultSet resultSet) throws SQLException {
        GroupWorkDTO groupWorkDTO = new GroupWorkDTO();

        groupWorkDTO.setId(resultSet.getInt("id"));
        groupWorkDTO.setName(resultSet.getString("name"));
        groupWorkDTO.setStartDay(resultSet.getDate("start_date"));
        groupWorkDTO.setEndDay(resultSet.getDate("end_date"));
        groupWorkDTO.setLeaderId(resultSet.getInt("leader_id"));
        groupWorkDTO.setLeaderName(resultSet.getString("leader_name"));
        return groupWorkDTO;
    }
}
