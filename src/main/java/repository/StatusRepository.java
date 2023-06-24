package repository;

import config.MysqlConfig;
import model.StatusModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository {

    public List<StatusModel> getStatus() {
        List<StatusModel> listStatus = new ArrayList<>();
        Connection connection = MysqlConfig.getConnection();
        String query = "select * from status s ";

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while(resultSet.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setName(resultSet.getString("name"));

                listStatus.add(statusModel);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Lỗi câu query getStatus " + e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (Exception e) {

            }
        }
        return listStatus;
    }

    public StatusModel getStatusModelById(int id) {
        StatusModel statusModel = new StatusModel();
        Connection connection = MysqlConfig.getConnection();
        String query = "SELECT * FROM status AS s where s.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setName(resultSet.getString("name"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi câu truy vấn getStatusModelById " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {

                }
            }
        }

        return statusModel;
    }
}
