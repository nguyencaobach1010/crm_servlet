package service;

import dto.GroupWorkDTO;
import dto.TaskDTO;
import model.UserModel;
import repository.GroupWorkRepository;
import repository.TaskRepository;
import repository.UserRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class GroupWorkService {
    private final GroupWorkRepository groupWorkRepository = new GroupWorkRepository();
    private final UserRepository userRepository = new UserRepository();
    private final TaskRepository taskRepository = new TaskRepository();

    public List<GroupWorkDTO> getAllGroupWork() {
        return groupWorkRepository.findAllGroupWorks();
    }

    public boolean insertGroupWork(String name, Date startDate, Date endDate, int leaderId) {
        return groupWorkRepository.insertGroupWork(name, startDate, endDate, leaderId);
    }

    public boolean deleteGroupWorkById(int id) {
        return groupWorkRepository.deleteById(id);
    }

    public List<UserModel> getAllUserByRoleId(int roleId) {
        return userRepository.findAllByRoleId(roleId);
    }

    public boolean updateGroupWork(int id, String name, Date startDate, Date endDate, int leaderId) {
        return groupWorkRepository.update(id, name, startDate, endDate, leaderId);
    }

    public Optional<GroupWorkDTO> getGroupWorkById(int id) {
        return groupWorkRepository.findById(id);
    }

    public Optional<UserModel> getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<TaskDTO> getTasksByGroupWorkIdAndStatusId(int groupWorkId, int statusId) {
        return taskRepository.findByGroupWorkIdAndStatusId(groupWorkId, statusId);
    }
}
