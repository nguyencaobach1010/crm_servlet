package repository;

import config.MysqlConfig;
import model.RoleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    public List<RoleModel> getAllRole() {
        List<RoleModel> listRoles = new ArrayList<>();
        Connection connection = MysqlConfig.getConnection();
        String query = "select * from roles r";

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));

                listRoles.add(roleModel);
            }
        } catch (Exception e) {
            System.out.println("Lỗi câu query getAllRoles " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception e) {

            }
        }
        return listRoles;
    }

    // Lấy role bằng id
    public RoleModel getRoleModelById(int id) {
        RoleModel roleModel = new RoleModel();
        Connection connection = MysqlConfig.getConnection();
        String query = "SELECT * FROM roles AS r where r.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));
            }

        } catch (Exception e) {
            System.out.println("Loi cau truy van getRoleModelById " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {

                }
            }
        }

        return roleModel;
    }

    public boolean deleteRoleById(int id){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "DELETE FROM roles as r WHERE r.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            isSucess = statement.executeUpdate() > 0;
            System.out.println(isSucess);
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi deleteRole" + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối deleteRoleById " + e.getMessage());
                }
            }
        }
        return isSucess;
    }

    public boolean insertRole(String name, String description){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "INSERT INTO roles(name, description) values(?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2, description);

            isSucess = statement.executeUpdate() > 0;
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi insertRole" + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối login " + e.getMessage());
                }
            }
        }
        return isSucess;
    }
}
