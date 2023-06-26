package service;

import dto.TaskDTO;
import repository.TaskRepository;

import java.sql.Date;
import java.util.List;

public class TaskSevice {

    private final TaskRepository taskRepository = new TaskRepository();

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAllTask();
    }

    public boolean insertTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
        return taskRepository.insertTask(name, startDate, endDate ,userId, jobId, statusId );
    }
}
