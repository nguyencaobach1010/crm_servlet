package controller;

import config.MysqlConfig;
import model.UserModel;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "loginController",urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private  LoginService loginService = new LoginService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        boolean isSuccess = loginService.checkLogin(email,password);

        if (isSuccess) {
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/dashboard");
        } else {
            PrintWriter writer = resp.getWriter();
            writer.println("Login Fail !");
            writer.close();
        }
    }
}
