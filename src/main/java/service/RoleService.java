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

//    public boolean deleteRoleById(int id) {
//        int result = rolesRepository.deleteRoleById(id);
//        return result > 0;
//    }
//
//    public boolean insertRole(String name, String description) {
//        int result = rolesRepository.insertRole(name, description);
//        return result > 0;
//    }
//
//    public boolean updateRole(RoleModel role) {
//        int result = rolesRepository.updateRole(role);
//        return result > 0;
//    }
}
