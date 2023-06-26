package controller;

import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private  LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }else {
            // Nếu session chưa tồn tại hoặc người dùng chưa đăng nhập,
            // hiển thị trang login page.
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
//        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isSuccess = loginService.checkLogin(email, password);

        if (isSuccess) {
            HttpSession session = req.getSession();
            session.setAttribute("LOGIN_USER", isSuccess);
            System.out.println("Kiểm tra: " + email);
            System.out.println("Kiểm tra: " + password);

            session.setMaxInactiveInterval(1800);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.setAttribute("message", "sai tên đăng nhập hoặc mật khẩu!");
            req.setAttribute("login", isSuccess);
            resp.sendRedirect(req.getContextPath() + "/login");
            req.getRequestDispatcher("login.jsp").forward(req,resp);

        }

    }
}
