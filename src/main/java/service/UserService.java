package service;

import dto.StatusDTO;
import dto.TaskDTO;
import model.RoleModel;
import model.UserModel;
import repository.RoleRepository;
import repository.TaskRepository;
import repository.UserRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final RoleRepository roleRepository = new RoleRepository();
    private final TaskRepository taskRepository = new TaskRepository();
    public List<UserModel> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public List<RoleModel> getAllRoles() {
        return roleRepository.findAllRoles();
    }

    public boolean insertUser(String fullName, String email, String password, String avatar, int roleId) {
        return userRepository.insertUser(fullName, email, password, avatar, roleId);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteById(id);
    }

    public Optional<UserModel> getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<TaskDTO> getTasksByUserIdAndStatusId(int userId, int statusId) {
        return taskRepository.findByUserIdAndStatusId(userId, statusId);
    }

    public boolean updateUser(int id, String fullName, String email, String password, String avatar, int roleId) {
        return userRepository.update(id, fullName, email, password, avatar, roleId);
    }

    public List<TaskDTO> getTasksByUserId(int id) {
        return taskRepository.findByUserId(id);
    }

    public List<StatusDTO> getAllStatus() {
        return taskRepository.findAllStatus();
    }

    public boolean updateTaskProgress(int taskId, Date startDate, Date endDate, int statusId) {
        return taskRepository.updateProgress(taskId, startDate, endDate, statusId);
    }
}
