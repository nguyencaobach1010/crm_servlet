package service;

import dto.GroupWorkDTO;
import dto.StatusDTO;
import dto.TaskDTO;
import model.UserModel;
import repository.GroupWorkRepository;
import repository.TaskRepository;
import repository.UserRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository taskRepository = new TaskRepository();
    private final GroupWorkRepository groupWorkRepository = new GroupWorkRepository();
    private final UserRepository userRepository = new UserRepository();

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAllTasks();
    }

    public boolean deleteTaskById(int id) {
        return taskRepository.deleteById(id);
    }

    public List<StatusDTO> getAllStatus() {
        return taskRepository.findAllStatus();
    }

    public Optional<TaskDTO> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public List<GroupWorkDTO> getAllGroupWork() {
        return groupWorkRepository.findAllGroupWorks();
    }

    public boolean updateTask(int id, String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
        return taskRepository.update(id, name, startDate, endDate, userId, jobId, statusId);
    }

    public List<GroupWorkDTO> getGroupWorkByLeaderId(int leaderId) {
        return groupWorkRepository.findByLeaderId(leaderId);
    }

    public boolean insertTask(String taskName, Date startDate, Date endDate, int userId ,int groupWorkId, int statusId) {
        return taskRepository.insert(taskName, startDate, endDate, userId, groupWorkId, statusId);
    }

    public List<UserModel> getUserByRoleId(int roleId) {
        return userRepository.findAllByRoleId(roleId);
    }

    public boolean updateTaskProgress(int taskId, Date startDate, Date endDate, int statusId) {
        return taskRepository.updateProgress(taskId, startDate, endDate, statusId);
    }
}
