package repository;

import config.MysqlConfig;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    public Optional<UserModel> findByEmailAndPassword(String email, String password) {
        Connection connection = null;
        Optional<UserModel> userModelOptional = Optional.empty();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT * FROM users u WHERE u.email = ? AND u.password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserModel userModel = new UserModel();
                //Lấy giá trị cột id
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(resultSet.getInt("role_id"));
                userModelOptional = Optional.of(userModel);
            }
        } catch (Exception e) {
            System.out.println("Error findByEmailAndPassword: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findByEmailAndPassword");
        }
        return userModelOptional;
    }

    public List<UserModel> findAllUsers() {
        Connection connection = null;
        List<UserModel> userModelList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT * FROM users u ORDER BY u.role_id ASC";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(resultSet.getInt("role_id"));
                userModelList.add(userModel);
            }
        } catch (Exception e) {
            System.out.println("Error findAllUsers: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findAllUsers");
        }
        return userModelList;
    }

    public boolean insertUser(String fullName, String email, String password, String avatar, int roleId) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String insert = "INSERT INTO users(email, password, fullname, avatar, role_id) " +
                    "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, fullName);
            statement.setString(4, avatar);
            statement.setInt(5, roleId);

            isSuccess = statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error at insertUser: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "insertUser");
        }
        return isSuccess;
    }

    public boolean deleteById(int id) {
        boolean isSuccess = false;
        Connection connection = null;

        try {
            connection = MysqlConfig.getConnection();
            String delete = "DELETE FROM users u WHERE u.id = ?";

            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at deleteById(UserRepository): " + e.getMessage());;
        } finally {
            MysqlConfig.closeConnection(connection, "deleteById(UserRepository)");
        }
        return isSuccess;
    }

    public Optional<UserModel> findById(int id) {
        Connection connection = null;
        Optional<UserModel> userModelOptional = Optional.empty();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT * FROM users u WHERE u.id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(resultSet.getInt("role_id"));
                userModelOptional = Optional.of(userModel);
            }
        } catch (Exception e) {
            System.out.println("Error findById: " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findById");
        }
        return userModelOptional;
    }

    public boolean update(int id, String fullName, String email, String password, String avatar, int roleId) {
        boolean isSuccess = false;
        Connection connection = null;
        try {
            String update = "UPDATE users " +
                    "SET email = ?, password = ?, fullname = ?, avatar = ?, role_id = ? " +
                    "WHERE id = ?";
            connection = MysqlConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, fullName);
            statement.setString(4, avatar);
            statement.setInt(5, roleId);
            statement.setInt(6, id);

            isSuccess = statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error update(UserRepository): " + e.getMessage());;
        } finally {
            MysqlConfig.closeConnection(connection, "update(UserRepository)");
        }
        return isSuccess;
    }

    public List<UserModel> findAllByRoleId(int roleId) {
        Connection connection = null;
        List<UserModel> listUser = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String query = "SELECT * FROM users u WHERE u.role_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roleId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(roleId);
                listUser.add(userModel);
            }
        } catch (Exception e) {
            System.out.println("Error findAllByRoleId(UserRepository): " + e.getMessage());
        } finally {
            MysqlConfig.closeConnection(connection, "findAllByRoleId(UserRepository)");
        }
        return listUser;
    }
}
