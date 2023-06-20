package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
    public static final String url = "jdbc:mysql://localhost:3308/crm_app";
    public static final String username = "root";
    public static final String passsword = "admin123";

    public static Connection getConnection(){
        Connection connection = null;
        try{
            //Chỉ định Driver sử dụng
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Tạo kết nối tới cơ sở dữ liệu
            connection = DriverManager.getConnection(url,username,passsword);
        }catch (Exception e){
            System.out.println("Lỗi kết nối tới cở sở dữ liệu : " + e.getMessage());
        }
        return connection;
    }

}
