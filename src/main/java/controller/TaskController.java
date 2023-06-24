package controller;

import model.StatusModel;
import model.TaskModel;
import service.StatusService;
import service.TaskSevice;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "taskController", urlPatterns = {"/task", "/task/add", "/task-update"})
public class TaskController extends HttpServlet {
    private final TaskSevice taskSevice = new TaskSevice();
    private final UserService userService = new UserService();
    private final StatusService statusService = new StatusService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/task":
                getAllTask(req, resp);
                break;
            case "/task/add":

                break;
//            case "/user/edit":
//                addUser(req, resp);
//                break;
//            case "/user/delete":
//                deleteUser(req, resp);
//                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/task":

                break;
            case "/task/add":
                break;
            case "/task/edit":

                break;
            case "/task/delete":

                break;
            default:

                break;
        }
    }
    private void getAllTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskModel> listTask = taskSevice.getAllTasks();
        List<StatusModel> listStatus = statusService.getStatus();
        req.setAttribute("listTasks", listTask);
        req.setAttribute("listStatus", listStatus);

        req.getRequestDispatcher("task.jsp").forward(req, resp);
    }

//    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String method = req.getMethod();
//        List<TaskModel> listTasks = taskSevice.getAllTasks();
//        if (method.equalsIgnoreCase("post")) {
//            String name = req.getParameter("name");
//            String startDate = req.getParameter("start_date");
//            String endDate = req.getParameter("end_date");
//            String password = req.getParameter("password");
//            int roleId = Integer.parseInt(req.getParameter("role"));
//            taskSevice.insertTask(name, startDate, fullname, roleId);
//
//        }
//        req.setAttribute("listRoles", listRoles);
//        req.getRequestDispatcher("/user-add.jsp").forward(req, resp);
//    }
}
