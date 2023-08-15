package repository;

import config.MysqlConfig;
import model.RoleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepository {
    public List<RoleModel> findAllRoles() {
        Connection connection = null;
        List<RoleModel> roleModelList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT * FROM roles r";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));
                roleModelList.add(roleModel);
            }
        } catch (Exception e) {
            System.out.println("Error findAllRoles: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findAllRoles");
        }
        return roleModelList;
    }

    public boolean deleteById(int id) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String delete = "DELETE FROM roles r WHERE r.id = ?";

            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at deleteById(RoleRepository): " + e.getMessage());;
        } finally {
            MysqlConfig.closeConnection(connection, "deleteById(RoleRepository)");
        }
        return isSuccess;
    }

    public boolean insertRole(String name, String desc) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String insert = "INSERT INTO roles(name, description) VALUES(?, ?)";

            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, name);
            statement.setString(2, desc);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at InsertRole: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "insertRole");
        }
        return isSuccess;
    }

    public Optional<RoleModel> findById(int id) {
        Connection connection = null;
        Optional<RoleModel> roleModelOptional = Optional.empty();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT * FROM roles r WHERE r.id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(id);
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));
                roleModelOptional = Optional.of(roleModel);
            }
        } catch (Exception e) {
            System.out.println("Error findById(RoleRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findById(RoleRepository)");
        }
        return roleModelOptional;
    }

    public boolean update(int id, String name, String description) {
        Connection connection = null;
        boolean isSuccess = false;

        try {
            connection = MysqlConfig.getConnection();
            String update = "UPDATE roles r SET name = ?, description = ? WHERE r.id = ?";

            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, id);

            isSuccess = statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error update(RoleRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "update(RoleRepository)");
        }
        return isSuccess;
    }
}
