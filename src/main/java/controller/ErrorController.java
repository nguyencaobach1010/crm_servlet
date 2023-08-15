package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "errorServlet", urlPatterns = {"/error-forbidden", "/error-bad_request"})
public class ErrorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/error-forbidden")) {
            req.getRequestDispatcher("403.jsp").forward(req, resp);
        } else if(path.equals("/error-bad_request")) {
            req.getRequestDispatcher("404.jsp").forward(req, resp);
        }
    }
}
