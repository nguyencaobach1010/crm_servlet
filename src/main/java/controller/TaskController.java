package controller;

import dto.TaskDTO;
import dto.UserDTO;
import model.JobModel;
import model.StatusModel;
import service.JobService;
import service.StatusService;
import service.TaskSevice;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "taskController", urlPatterns = {"/task", "/task/add", "/task-update"})
public class TaskController extends HttpServlet {
    private final TaskSevice taskSevice = new TaskSevice();

    private final JobService jobService = new JobService();
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
                addTask(req, resp);
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
                addTask(req, resp);
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
        List<TaskDTO> listTask = taskSevice.getAllTasks();
        req.setAttribute("listTasks", listTask);
        req.getRequestDispatcher("task.jsp").forward(req, resp);
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getMethod();
        List<TaskDTO> listTask = taskSevice.getAllTasks();
        List<JobModel> listJob = jobService.getALlJobs();
        List<UserDTO> listUser = userService.getAllUsers();
        List<StatusModel> listStatus = statusService.getStatus();
        if (method.equalsIgnoreCase("post")) {
            String nameProject = req.getParameter("nameProject");
            Date startDate = Date.valueOf(req.getParameter("startDate"));
            Date endDate = Date.valueOf(req.getParameter("endDate"));
            int nameJob = Integer.parseInt(req.getParameter("nameJob"));
            int namePerformer = Integer.parseInt(req.getParameter("namePerformer"));
            int statusId = Integer.parseInt(req.getParameter("statusId"));
            taskSevice.insertTask(nameProject, startDate, endDate, namePerformer, nameJob, statusId);

        }
        req.setAttribute("listTasks", listTask);
        req.setAttribute("listJobs", listJob);
        req.setAttribute("listUsers", listUser);
        req.setAttribute("listStatus", listStatus);
        req.getRequestDispatcher("/task-add.jsp").forward(req, resp);
    }
}
