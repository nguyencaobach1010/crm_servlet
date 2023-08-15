package controller;

import model.UserModel;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private final LoginService loginService = new LoginService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cookUname")) {
                    req.setAttribute("cookieEmail", cookie.getValue());
                }

                if (cookie.getName().equals("cookPass")) {
                    req.setAttribute("cookiePass", cookie.getValue());
                }
            }
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("check-box");

        if (email.trim().isEmpty() || password.trim().isEmpty() || !loginService.checkLogin(email, password)) {
            req.setAttribute("messageLogin", "Email không tồn tại hoặc mật khẩu không chính xác");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            Optional<UserModel> userModelOptional = loginService.getUserByEmailAndPassword(email, password);
            if (userModelOptional.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("LOGIN_USER", userModelOptional.get());

                if (remember != null) {
                    Cookie cookieUsername = new Cookie("cookUname", email);
                    Cookie cookiePass = new Cookie("cookPass", password);
                    cookieUsername.setMaxAge(60*60*24);
                    cookiePass.setMaxAge(60*60*24);
                    resp.addCookie(cookieUsername);
                    resp.addCookie(cookiePass);
                }
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        }

    }
}
