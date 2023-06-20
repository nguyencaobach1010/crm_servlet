package controller;

import service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "roleController", urlPatterns = {"/role", "/role-add", "/role-update"})
public class RoleController extends HttpServlet {

    private RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        switch (action) {
            case "/role":
                req.setAttribute("roles",roleService.getAllRoles());
                req.getRequestDispatcher("/role-table.jsp").forward(req,resp);
                break;
            case "/role-add":
                req.getRequestDispatcher("/role-add.jsp").forward(req,resp);
                break;
            case "/role-update":
                req.getRequestDispatcher("/role-update.jsp").forward(req,resp);
                break;
        }
    }
}
