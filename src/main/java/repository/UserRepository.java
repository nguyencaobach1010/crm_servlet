package repository;

import config.MysqlConfig;
import model.UserModel;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public List<UserModel> findAll(){
        Connection connection = null;
        List<UserModel> usersModelList = new ArrayList<>();

        try {
            String sql = "select * from users u";
            PreparedStatement statement =  MysqlConfig.getConnection().prepareStatement(sql);
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
}
