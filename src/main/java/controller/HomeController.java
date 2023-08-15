package controller;

import dto.StatusDTO;
import model.UserModel;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "homeServlet", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
    private final TaskService taskService = new TaskService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel user = (UserModel) session.getAttribute("LOGIN_USER");

        req.setAttribute("loginUser", user);

        List<StatusDTO> listStatus = taskService.getAllStatus();
       if (listStatus.size() > 0) {
           req.setAttribute("notStarted", listStatus.get(0).getTaskCount());
           req.setAttribute("inProgress", listStatus.get(1).getTaskCount());
           req.setAttribute("completed", listStatus.get(2).getTaskCount());

           int totalTaskCount = listStatus.get(0).getTaskCount() + listStatus.get(1).getTaskCount() +
                   listStatus.get(2).getTaskCount();
           req.setAttribute("notStartedRate", 100*(float)(listStatus.get(0).getTaskCount())/totalTaskCount);
           req.setAttribute("inProgressRate", 100*(float)(listStatus.get(1).getTaskCount())/totalTaskCount);
           req.setAttribute("completedRate", 100*(float)(listStatus.get(2).getTaskCount())/totalTaskCount);
       }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

}
