package repository;

import config.MysqlConfig;
import dto.TaskDTO;
import model.TaskModel;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class TaskRepository {

    public boolean insertTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "INSERT INTO tasks(name , start_date, end_date, user_id, job_id, status_id) values(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
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

    public List<TaskDTO> findAllTask(){
        Connection connection = null;
        List<TaskDTO> taskList = new ArrayList<>();

        try {
            String sql = "SELECT tasks.id,tasks.name, tasks.start_date, tasks.end_date, users.fullname as user_id, jobs.name as job_id, status.name as status_id FROM (((tasks INNER JOIN users ON tasks.user_id  = users.id) INNER JOIN jobs  ON tasks.job_id  = jobs.id) INNER JOIN status ON tasks.status_id  = status.id)";
            PreparedStatement statement =  MysqlConfig.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                //Duyệt từng dòng dữ liệu
                TaskDTO taskDTO = new TaskDTO();
                //Lấy giá trị của cột chỉ định và lưu vào đối tượng
                taskDTO.setId(resultSet.getInt("id"));
                taskDTO.setName(resultSet.getString("name"));
                taskDTO.setStartDate(resultSet.getDate("start_date"));
                taskDTO.setEndDate(resultSet.getDate("end_date"));
                taskDTO.setUserName(resultSet.getString("user_id"));
                taskDTO.setJobName(resultSet.getString("job_id"));
                taskDTO.setStatusName(resultSet.getString("status_id"));

                taskList.add(taskDTO);
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
