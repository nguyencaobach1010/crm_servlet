package controller;

import dto.UserDTO;
import model.RoleModel;
import service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "roleController", urlPatterns = {"/role", "/role/add","/role/edit", "/role/delete"})
public class RoleController extends HttpServlet {

    private RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/role":
                getAllRole(req,resp);
                break;
            case "/role/add":
                addRole(req, resp);
                break;
            case "/role/edit":
                updateRoleById(req, resp);
                break;
            case "/role/delete":
                deleteRole(req, resp);

                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/role":

                break;
            case "/role/add":
                addRole(req, resp);
                break;
            case "/role/edit":
                updateRoleById(req, resp);
                break;
            case "/role/delete":

                break;
            default:

                break;
        }
    }
    private void getAllRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleModel> listRole = roleService.getAllRoles();
        req.setAttribute("listRoles", listRole);
        req.getRequestDispatcher("role-table.jsp").forward(req, resp);
    }

    private void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getMethod();
        List<RoleModel> listRoles = roleService.getAllRoles();
        if (method.equalsIgnoreCase("post")) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            roleService.insertRole(name, description);

        }
        req.setAttribute("listRoles", listRoles);
        req.getRequestDispatcher("/role-add.jsp").forward(req, resp);
    }

    private void deleteRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id =  Integer.parseInt(req.getParameter("id"));
        boolean isSucess = roleService.deleteRoleById(id);
    }

    private void updateRoleById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        RoleModel role = roleService.getRoleById(id);
        String method = req.getMethod();
        if (method.equalsIgnoreCase("post")) {

            String name = req.getParameter("name");
            String description = req.getParameter("description");
            roleService.updateRole(id, name, description);

        }
        req.setAttribute("role", role);
        req.getRequestDispatcher("/role-edit.jsp").forward(req, resp);

    }
}
