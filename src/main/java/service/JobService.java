package service;

import model.JobModel;
import repository.JobRepository;

import java.util.List;

public class JobService {
    private final JobRepository jobRepository = new JobRepository();

    private List<JobModel> getALlJobs(){
        return jobRepository.getAllJobs();

    }
}
