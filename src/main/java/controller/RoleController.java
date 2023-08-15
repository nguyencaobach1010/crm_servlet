package controller;

import model.RoleModel;
import model.UserModel;
import service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "roleServlet", urlPatterns = {"/role", "/role/add", "/role/delete", "/role/edit"})
public class RoleController extends HttpServlet {
    private final RoleService roleService = new RoleService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel loginUser = (UserModel) session.getAttribute("LOGIN_USER");
        String path = req.getServletPath();
        switch (path) {
            case "/role":
                displayAllRoles(req, resp, loginUser);
                break;
            case "/role/add":
                addRole(req, resp, loginUser);
                break;
            case "/role/delete":
                deleteRole(req);
                break;
            case "/role/edit":
                editRole(req, resp, loginUser);
                break;
            default:
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel loginUser = (UserModel) session.getAttribute("LOGIN_USER");
        String path = req.getServletPath();
        switch (path) {
            case "/role/add":
                addRole(req, resp, loginUser);
                break;
            case "/role/edit":
                editRole(req, resp, loginUser);
                break;
        }

    }

    private void displayAllRoles(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        List<RoleModel> listAllRoles = roleService.getAllRoles();
        req.setAttribute("listAllRoles", listAllRoles);
        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("role-table.jsp").forward(req, resp);
    }

    private void deleteRole(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("roleID"));
        roleService.deleteRole(id);
    }

    private void addRole(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equalsIgnoreCase("post")) {
            String name = req.getParameter("role-name");
            String desc = req.getParameter("description");

            roleService.addRole(name, desc);
        }
        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("/role-add.jsp").forward(req, resp);
    }

    private void editRole(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws IOException, ServletException {
        String method = req.getMethod();
        boolean isEdited = false;

        Integer roleId = (req.getParameter("roleId") != null)? Integer.parseInt(req.getParameter("roleId")) :
                null;
        req.setAttribute("loginUser", currentUser);

        if (roleId != null) {
            Optional<RoleModel> roleModel = roleService.getRoleById(roleId);
            roleModel.ifPresent(roleModel1 -> req.setAttribute("editedRole", roleModel1));
        }

        if (method.equalsIgnoreCase("post")) {
            int editedRoleId = Integer.parseInt(req.getParameter("role-id"));
            String roleName = req.getParameter("role-name");
            String description = req.getParameter("description");

            roleService.updateRole(editedRoleId, roleName, description);
            isEdited = true;
            resp.sendRedirect(req.getContextPath() + "/role");
        }

        if (!isEdited) {
            req.getRequestDispatcher("/role-edit.jsp").forward(req, resp);
        }
    }
}
