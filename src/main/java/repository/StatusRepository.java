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
        List<StatusModel> statuses = new ArrayList<>();
        try {
            Connection connection = MysqlConfig.getConnection();
            String query = "select * from status";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                StatusModel status = new StatusModel();
                status.setId(rs.getInt("id"));
                status.setName(rs.getString("name"));
                statuses.add(status);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error get statuses " + e.getMessage());
        }
        return statuses;
    }
}
