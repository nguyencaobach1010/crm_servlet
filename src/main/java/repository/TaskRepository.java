package repository;

import config.MysqlConfig;
import model.TaskModel;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepository {

    public boolean insertTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "INSERT INTO tasks(name , startDate, endDate, userId, jobId, statusId) values(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setDate(2, (java.sql.Date) startDate);
            statement.setDate(3, (java.sql.Date) endDate);
            statement.setInt(4, userId);
            statement.setInt(5, jobId);
            statement.setInt(6, statusId);

            isSucess = statement.executeUpdate() > 0;
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi insertTask" + e.getMessage());
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

    public List<TaskModel> findAllTask(){
        Connection connection = null;
        List<TaskModel> taskList = new ArrayList<>();

        try {
            String sql = "select * from tasks t";
            PreparedStatement statement =  MysqlConfig.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu
                TaskModel taskModel = new TaskModel();
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setStartDate(resultSet.getDate("start_date"));
                taskModel.setEndDate(resultSet.getDate("end_date"));
                taskModel.setUserId(resultSet.getInt("user_id"));
                taskModel.setJobId(resultSet.getInt("job_id"));
                taskModel.setStatusId(resultSet.getInt("status_id"));

                taskList.add(taskModel);
            }
        }catch (Exception e) {
            System.out.println("Error findAllTask:  " + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối login " + e.getMessage());
                }
            }
        }
        return taskList;
    }
}
