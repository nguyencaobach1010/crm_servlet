package service;

import model.RoleModel;
import model.UserModel;
import repository.RoleRepository;
import repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final RoleRepository roleRepository = new RoleRepository();

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();

    }

    public boolean insertUser(String email, String password, String fullname, int roleId) {
        return userRepository.insertUser(fullname, email, password, roleId);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public List<RoleModel> getAllRole() {
        return roleRepository.getAllRole();
    }
}
