package controller;

import dto.UserDTO;
import model.RoleModel;
import model.UserModel;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "userController", urlPatterns = {"/user", "/user/add", "/user/edit", "/user/delete", "/user/detail"})
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/user":
                getAllUser(req, resp);
                break;
            case "/user/add":
                addUser(req, resp);
                break;
            case "/user/edit":
                updateUserById(req, resp);
                break;
            case "/user/delete":
                deleteUser(req, resp);
                break;
            case "/user/detail":

                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/user/add":
                addUser(req, resp);
                break;
            case "/user/edit":
                updateUserById(req, resp);
                break;
            default:

                break;
        }
    }

    private void getAllUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> list = userService.getAllUsers();
        req.setAttribute("listUsers", list);
        req.getRequestDispatcher("user-table.jsp").forward(req, resp);
    }


    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getMethod();
        List<RoleModel> listRoles = userService.getAllRole();
        if (method.equalsIgnoreCase("post")) {
            String email = req.getParameter("email");
            String fullname = req.getParameter("fullname");
            String password = req.getParameter("password");
            int roleId = Integer.parseInt(req.getParameter("role"));
            userService.insertUser(email, password, fullname, roleId);

        }
        req.setAttribute("listRoles", listRoles);
        req.getRequestDispatcher("/user-add.jsp").forward(req, resp);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id =  Integer.parseInt(req.getParameter("id"));
        boolean isSucess = userService.deleteUser(id);
    }

    private void updateUserById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        UserDTO user = userService.getUserById(id);
        List<RoleModel> listRoles = userService.getAllRole();
        String method = req.getMethod();
        if (method.equalsIgnoreCase("post")) {

            String email = req.getParameter("email");
            String fullname = req.getParameter("fullname");
            String password = req.getParameter("password");
            int roleId = Integer.parseInt(req.getParameter("role"));
            userService.updateUser(id, email, password, fullname, roleId);

        }
        req.setAttribute("user", user);
        req.setAttribute("rolelist", listRoles);
        req.getRequestDispatcher("/user-edit.jsp").forward(req, resp);
    }
}
