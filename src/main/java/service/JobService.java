package service;

import model.JobModel;
import model.RoleModel;
import repository.JobRepository;

import java.sql.Date;
import java.util.List;

public class JobService {
    private final JobRepository jobRepository = new JobRepository();

    public List<JobModel> getALlJobs(){
        return jobRepository.getAllJobs();

    }
    public JobModel getJobById(int id){
        JobModel result = jobRepository.getJobById(id);
        return  result;
    }

    public boolean deleteJobById(int id) {
        boolean result = jobRepository.deleteJobById(id);
        return result;
    }

    public boolean insertJobs(String name, Date start_date, Date end_date) {
        boolean result = jobRepository.insertJob(name, start_date, end_date);
        return result;
    }

    public boolean updateJobs(int id, String name, Date start_date, Date end_date) {
        boolean result = jobRepository.updateJob(id, name, start_date, end_date);
        return result;
    }
}
