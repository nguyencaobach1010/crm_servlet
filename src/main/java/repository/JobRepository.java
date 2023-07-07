package repository;

import config.MysqlConfig;
import model.JobModel;
import model.RoleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class JobRepository {
    public List<JobModel> getAllJobs() {
        List<JobModel> listJobs = new ArrayList<>();
        Connection connection = MysqlConfig.getConnection();
        String query = "select * from jobs j ";

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();

            while (resultSet.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStartDate(resultSet.getDate("start_date"));
                jobModel.setEndDate(resultSet.getDate("end_date"));
                listJobs.add(jobModel);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Lỗi câu query getAllJobs " + e.getMessage());
        }
        return listJobs;
    }

    public JobModel getJobById(int id) {
        JobModel jobModel = new JobModel();
        Connection connection = MysqlConfig.getConnection();
        String query = "SELECT * FROM jobs AS j where j.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStartDate(resultSet.getDate("start_date"));
                jobModel.setEndDate(resultSet.getDate("end_date"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi câu truy vấn getRoleModelById " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {

                }
            }
        }

        return jobModel;
    }

    public boolean deleteJobById(int id){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "DELETE FROM jobs as j WHERE j.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            isSucess = statement.executeUpdate() > 0;
            System.out.println(isSucess);
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi deleteJobById" + e.getMessage());
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

    public boolean insertJob(String name, Date start_date, Date end_date){
        Connection connection = null;
        boolean isSucess = false;
        try{
            connection = MysqlConfig.getConnection();
            String sql = "INSERT INTO jobs(name, start_date, end_date) values(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setDate(2, start_date);
            statement.setDate(3, end_date);

            isSucess = statement.executeUpdate() > 0;
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi insertJob" + e.getMessage());
        }finally {
            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    System.out.println("Lỗi đóng kết nối insertJob " + e.getMessage());
                }
            }
        }
        return isSucess;
    }

    public boolean updateJob(int id, String name, Date start_date, Date end_date){
        Connection connection = null;
        boolean isSucess = false;

        try{
            connection = MysqlConfig.getConnection();
            String sql = "UPDATE jobs SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setDate(2, start_date);
            statement.setDate(3, end_date);
            statement.setInt(4, id);

            isSucess = statement.executeUpdate() > 0;
            connection.close();
        }catch (Exception e){
            System.out.println("Lỗi đóng thực thi updateJob" + e.getMessage());
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
