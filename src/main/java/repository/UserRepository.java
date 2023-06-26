package repository;

import config.MysqlConfig;
import dto.UserDTO;
import model.RoleModel;
import model.UserModel;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<UserModel>findByEmailAndPassword(String email, String password){
        Connection connection = null;

        List<UserModel> usersModelList = new ArrayList<>();
        try {
            String sql = "select * from users u where u.email = ? and u.password = ?";
            PreparedStatement statement =  MysqlConfig.getConnection().prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu
                UserModel userModel = new UserModel();
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setRoleId(resultSet.getInt("role_id"));

                usersModelList.add(userModel);
            }
        }catch (Exception e) {
            System.out.println("Error findByEmailAndPassword:  " + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối login " + e.getMessage());
                }
            }
        }
        return usersModelList;
    }

    public List<UserDTO> findAll(){
        Connection connection = null;
        List<UserDTO> usersModelList = new ArrayList<>();

        try {
            String sql = "select users.id, users.email, users.fullname, roles.description as  role_description from users inner join roles on   users.role_id = roles.id";
            PreparedStatement statement =  MysqlConfig.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu
                UserDTO userDTO = new UserDTO();
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                userDTO.setId(resultSet.getInt("id"));
                userDTO.setEmail(resultSet.getString("email"));
                userDTO.setFullname(resultSet.getString("fullname"));
                userDTO.setRoleName(resultSet.getString("role_description"));

                usersModelList.add(userDTO);
            }
        }catch (Exception e) {
            System.out.println("Error findAll:  " + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối login " + e.getMessage());
                }
            }
        }
        return usersModelList;
    }

    // Lấy thông tin user bằng email
    public UserModel getUserByEmail(String email) {
        UserModel userModel = new UserModel();
        Connection connection = MysqlConfig.getConnection();
        String query = "select * from users u where u.email=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));

                RoleModel roleModel = new RoleModel();
                roleModel.setId(resultSet.getInt("role_id"));
                userModel.setRoleModel(roleModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (Exception e) {

            }
        }

        return userModel;
    }

    public boolean insertUser(String fullname, String email, String password, int roleId){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "INSERT INTO users(email, password, fullname, role_id) values(?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setInt(4, roleId);

            isSucess = statement.executeUpdate() > 0;
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi insertUser" + e.getMessage());
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
    /*
        Hiển thị danh sách role từ database ra màn hình
        Thế role id cứng thành role động người dùng chọn ở giao diện

     */

    public boolean deleteUser(int id){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "DELETE FROM users as u WHERE u.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            isSucess = statement.executeUpdate() > 0;
            System.out.println(isSucess);
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi deleteUser" + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối deleteById " + e.getMessage());
                }
            }
        }
        return isSucess;
    }
}
