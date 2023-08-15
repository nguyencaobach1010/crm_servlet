package service;

import model.RoleModel;
import repository.RoleRepository;

import java.util.List;
import java.util.Optional;

public class RoleService {
    private final RoleRepository roleRepository = new RoleRepository();

    public List<RoleModel> getAllRoles() {
        return roleRepository.findAllRoles();
    }

    public boolean deleteRole(int id) {
        return roleRepository.deleteById(id);
    }

    public boolean addRole(String name, String desc) {
        return roleRepository.insertRole(name, desc);
    }

    public Optional<RoleModel> getRoleById(int id) {
        return roleRepository.findById(id);
    }

    public boolean updateRole(int id, String name, String desc) {
        return roleRepository.update(id, name, desc);
    }
}
