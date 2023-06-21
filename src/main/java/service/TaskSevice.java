package service;

import model.TaskModel;
import repository.TaskRepository;

import java.util.Date;
import java.util.List;

public class TaskSevice {

    private final TaskRepository taskRepository = new TaskRepository();

    public List<TaskModel> getAllTasks() {
        return taskRepository.findAllTask();
    }

    public boolean insertTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
        return taskRepository.insertTask(name, startDate, endDate, userId, jobId, statusId );
    }
}
