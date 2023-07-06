package service;

import dto.UserDTO;
import model.RoleModel;
import model.UserModel;
import repository.RoleRepository;
import repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final RoleRepository roleRepository = new RoleRepository();

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll();

    }

    public boolean insertUser(String email, String password, String fullname, int roleId) {
        return userRepository.insertUser(fullname, email, password, roleId);
    }

    public boolean updateUser(int id, String email, String password, String fullname, int roleId){
        return userRepository.updateUser(id, email, password, fullname, roleId);
    }

    // get user by id service o day nhe a bach
    public UserDTO getUserById(int Id){
        return userRepository.getUserById(Id);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public List<RoleModel> getAllRole() {
        return roleRepository.getAllRole();
    }
}
