package controller;

import dto.GroupWorkDTO;
import dto.StatusDTO;
import dto.TaskDTO;
import model.UserModel;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "taskServlet", urlPatterns = {"/task", "/task/delete", "/task/add", "/task/edit", "/task/update"})
public class TaskController extends HttpServlet {
    private final TaskService taskService = new TaskService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel currentUser = (UserModel) session.getAttribute("LOGIN_USER");
        String path = req.getServletPath();
        switch (path) {
            case "/task":
                displayTasks(req, resp, currentUser);
                break;
            case "/task/edit":
                editTask(req, resp, currentUser);
                break;
            case "/task/delete":
                deleteTask(req, resp);
                break;
            case "/task/add":
                addTask(req, resp, currentUser);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        UserModel currentUser = (UserModel) session.getAttribute("LOGIN_USER");
        switch (path) {
            case "/task/add":
                addTask(req, resp, currentUser);
                break;
            case "/task/edit":
                editTask(req, resp, currentUser);
                break;
            case "/task/update":
                update(req, resp);
                break;
            default:
                break;
        }
    }

    private void displayTasks(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        List<TaskDTO> listAllTasks = taskService.getAllTasks();
        req.setAttribute("loginUser", currentUser);
        req.setAttribute("listAllTasks", listAllTasks);

        List<StatusDTO> statusDTOList = taskService.getAllStatus();
        req.setAttribute("listStatus", statusDTOList);
        req.getRequestDispatcher("task.jsp").forward(req, resp);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        taskService.deleteTaskById(taskId);
        resp.setContentType("text/plain");
        resp.getWriter().write("success");
    }

    private void editTask(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        String method = req.getMethod();

        Integer taskId = (req.getParameter("taskId") != null) ? Integer.parseInt(req.getParameter("taskId")) : null;
        req.setAttribute("loginUser", currentUser);
        boolean isEdited = false;

        if (taskId != null) {
            Optional<TaskDTO> optionalTaskDTO = taskService.getTaskById(taskId);
            optionalTaskDTO.ifPresent(taskDTO -> req.setAttribute("editedTask", taskDTO));
        }
        if (currentUser.getRoleId() == 1) {
            req.setAttribute("listGroupWork", taskService.getAllGroupWork());
        }
        List<StatusDTO> statusDTOList = taskService.getAllStatus();
        req.setAttribute("listStatus", statusDTOList);

        if (method.equalsIgnoreCase("post")) {
            int editedTaskId = Integer.parseInt(req.getParameter("task-id"));
            String taskName = req.getParameter("task-name");
            Date startDate = Date.valueOf(req.getParameter("start-date"));
            Date endDate = Date.valueOf(req.getParameter("end-date"));
            int userId = Integer.parseInt(req.getParameter("user-id"));
            int groupWorkId = Integer.parseInt(req.getParameter("select-groupWork"));
            int statusId = Integer.parseInt(req.getParameter("select-status"));
            taskService.updateTask(editedTaskId, taskName, startDate, endDate, userId, groupWorkId, statusId);
            isEdited = true;
            resp.sendRedirect(req.getContextPath() + "/task");
        }

        if (!isEdited) {
            req.getRequestDispatcher("/task-edit.jsp").forward(req, resp);
        }
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        String method = req.getMethod();
        List<GroupWorkDTO> listGroupWork;
        List<UserModel> leaderAndMemberMergedList = new ArrayList<>();

        if (currentUser.getRoleId() == 1) {
            listGroupWork = taskService.getAllGroupWork();
            List<UserModel> listLeader = taskService.getUserByRoleId(2);
            leaderAndMemberMergedList.addAll(listLeader);
        } else {
            listGroupWork = taskService.getGroupWorkByLeaderId(currentUser.getId());
        }
        List<UserModel> listMembers = taskService.getUserByRoleId(3);
        leaderAndMemberMergedList.addAll(listMembers);

        req.setAttribute("listMember", leaderAndMemberMergedList);

        req.setAttribute("listGroupWork", listGroupWork);

        List<StatusDTO> statusDTOList = taskService.getAllStatus();
        req.setAttribute("listStatus", statusDTOList);



        if (method.equalsIgnoreCase("post")) {
            String taskName = req.getParameter("task-name");
            Date startDate = Date.valueOf(req.getParameter("start-date"));
            Date endDate = Date.valueOf(req.getParameter("end-date"));
            int groupWorkId = Integer.parseInt(req.getParameter("select-groupWork"));
            int userId = Integer.parseInt(req.getParameter("select-member"));
            int statusId = Integer.parseInt(req.getParameter("select-status"));

            taskService.insertTask(taskName, startDate, endDate, userId, groupWorkId, statusId);
        }
        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("/task-add.jsp").forward(req, resp);
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        Date startDate = Date.valueOf(req.getParameter("startDate"));
        Date endDate = Date.valueOf(req.getParameter("endDate"));
        int statusId = Integer.parseInt(req.getParameter("selectStatus"));

        if (taskService.updateTaskProgress(taskId, startDate, endDate, statusId)) {
            resp.setContentType("text/plain");
            resp.getWriter().write("success");
        }
    }
}
