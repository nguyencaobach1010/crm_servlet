package repository;

import config.MysqlConfig;
import model.JobModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobRepository {
    public List<JobModel> getAllJobs() {
        List<JobModel> listJob = new ArrayList<>();
        Connection connection = MysqlConfig.getConnection();
        String query = "select * from jobs j ";

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();

            while (resultSet.next()) {
                JobModel job = new JobModel();
                job.setId(resultSet.getInt("id"));
                job.setName(resultSet.getString("name"));
                job.setStartDate(resultSet.getDate("start_date"));
                job.setEndDate(resultSet.getDate("end_date"));
                listJob.add(job);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error get jobs " + e.getMessage());
        }
        return listJob;
    }
}
