package service;

import model.StatusModel;
import repository.StatusRepository;

import java.util.List;

public class StatusService {

    private StatusRepository statusRepository = new StatusRepository();

    public List<StatusModel> getStatus(){
        return statusRepository.getStatus();

    }
}
