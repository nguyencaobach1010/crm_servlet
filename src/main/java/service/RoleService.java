package service;

import model.RoleModel;
import repository.RoleRepository;

import java.util.List;

public class RoleService {

    private RoleRepository rolesRepository = new RoleRepository();

    public List<RoleModel> getAllRoles(){
        RoleRepository roleRepository = new RoleRepository();
        return roleRepository.getAllRole();
    }

    public boolean deleteRoleById(int id) {
        boolean result = rolesRepository.deleteRoleById(id);
        return result;
    }

    public boolean insertRole(String name, String description) {
        boolean result = rolesRepository.insertRole(name, description);
        return result;
    }
//
//    public boolean updateRole(RoleModel role) {
//        int result = rolesRepository.updateRole(role);
//        return result > 0;
//    }
}
