package controller;

import dto.GroupWorkDTO;
import dto.TaskDTO;
import model.UserModel;
import service.GroupWorkService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "groupWorkServlet", urlPatterns = {"/group-work", "/group-work/add", "/group-work/delete", "/group-work/details", "/group-work/edit"})
public class GroupWorkController extends HttpServlet {
    private final GroupWorkService groupWorkService = new GroupWorkService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel loginUser = (UserModel) session.getAttribute("LOGIN_USER");
        String path = req.getServletPath();
        switch (path) {
            case "/group-work":
                displayAllGroupWorks(req, resp, loginUser);
                break;
            case "/group-work/add":
                addGroupWork(req, resp, loginUser);
                break;
            case "/group-work/details":
                groupWorkDetails(req, resp, loginUser);
                break;
            case "/group-work/delete":
                deleteGroupWork(req, resp);
                break;
            case "/group-work/edit":
                editGroupWork(req, resp, loginUser);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel loginUser = (UserModel) session.getAttribute("LOGIN_USER");
        String path = req.getServletPath();
        switch (path) {
            case "/group-work/add":
                addGroupWork(req, resp, loginUser);
                break;
            case "/group-work/edit":
                editGroupWork(req, resp, loginUser);
                break;
            default:
                break;
        }

    }

    private void displayAllGroupWorks(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        List<GroupWorkDTO> listAllGroupWorks = groupWorkService.getAllGroupWork();
        req.setAttribute("listAllGroupWorks", listAllGroupWorks);
        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
    }

    private void addGroupWork(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        String method = req.getMethod();
        req.setAttribute("loginUser", currentUser);
        if (currentUser.getRoleId() == 1) {
            req.setAttribute("leaderList", groupWorkService.getAllUserByRoleId(2));
        }
        if (method.equalsIgnoreCase("post")) {
            String startDate = req.getParameter("start-date");
            String endDate = req.getParameter("end-date");
            String name = req.getParameter("group-work-name");
            int leaderId = (currentUser.getRoleId() == 1)? Integer.parseInt(req.getParameter("select-leader"))
                    : currentUser.getId();

            groupWorkService.insertGroupWork(name, Date.valueOf(startDate), Date.valueOf(endDate), leaderId);
        }

        req.getRequestDispatcher("/groupwork-add.jsp").forward(req, resp);
    }

    private void deleteGroupWork(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int groupWorkId = Integer.parseInt(req.getParameter("groupWorkId"));

        groupWorkService.deleteGroupWorkById(groupWorkId);
        resp.setContentType("text/plain");
        resp.getWriter().write("success");
    }

    private void groupWorkDetails(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        int leaderId = Integer.parseInt(req.getParameter("leaderId"));
        int groupWorkId = Integer.parseInt(req.getParameter("groupWorkId"));
        Optional<UserModel> userModelOptional = groupWorkService.getUserById(leaderId);
        userModelOptional.ifPresent(userModel -> req.setAttribute("leaderDetails", userModel));

        Optional<GroupWorkDTO> groupWorkOptional = groupWorkService.getGroupWorkById(groupWorkId);
        groupWorkOptional.ifPresent(groupWorkDTO -> req.setAttribute("groupWorkDetail", groupWorkDTO));

        List<TaskDTO> listNotStartedTasks = groupWorkService.getTasksByGroupWorkIdAndStatusId(groupWorkId, 1);
        List<TaskDTO> listInProgressTasks = groupWorkService.getTasksByGroupWorkIdAndStatusId(groupWorkId, 2);
        List<TaskDTO> listCompletedTasks = groupWorkService.getTasksByGroupWorkIdAndStatusId(groupWorkId, 3);

        req.setAttribute("listNotStartedTasks", listNotStartedTasks);
        req.setAttribute("listInProgressTasks", listInProgressTasks);
        req.setAttribute("listCompletedTasks", listCompletedTasks);

        Map<String , Integer> averageMap = getAverageRate(listNotStartedTasks.size(), listInProgressTasks.size(), listCompletedTasks.size());
        req.setAttribute("notStartedTasksRate", averageMap.get("firstRate"));
        req.setAttribute("inProgressTasksRate", averageMap.get("secondRate"));
        req.setAttribute("completedTasksRate", averageMap.get("thirdRate"));

        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("/groupwork-details.jsp").forward(req, resp);
    }

    private void editGroupWork(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        String method = req.getMethod();
        Integer groupWorkId = (req.getParameter("groupWorkId") != null)? Integer.parseInt(req.getParameter("groupWorkId")) :
                null;

        boolean isEdited = false;

        if (groupWorkId != null) {
            Optional<GroupWorkDTO> groupWorkOptional = groupWorkService.getGroupWorkById(groupWorkId);
            groupWorkOptional.ifPresent(groupWorkDTO -> req.setAttribute("editedGroupWork", groupWorkDTO));
        }
        if (currentUser.getRoleId() == 1) {
            req.setAttribute("leaderList", groupWorkService.getAllUserByRoleId(2));
        }
        req.setAttribute("loginUser", currentUser);

        if (method.equalsIgnoreCase("post")) {
            int editedGroupWork = Integer.parseInt(req.getParameter("editedGroupWork"));
            String startDate = req.getParameter("start-date");
            String endDate = req.getParameter("end-date");
            String name = req.getParameter("group-work-name");
            int leaderId = (currentUser.getRoleId() == 1)? Integer.parseInt(req.getParameter("select-leader"))
                    : currentUser.getId();

            groupWorkService.updateGroupWork(editedGroupWork, name, Date.valueOf(startDate), Date.valueOf(endDate), leaderId);
            isEdited = true;
            resp.sendRedirect(req.getContextPath() + "/group-work");
        }

        if (!isEdited) {
            req.getRequestDispatcher("/groupwork-edit.jsp").forward(req, resp);
        }
    }

    private Map<String, Integer> getAverageRate(int firstNumber, int secondNumber, int thirdNumber) {
        Map<String, Integer> map = new HashMap<>(3);

        int total = firstNumber + secondNumber + thirdNumber;
        int firstRate = Math.round((float)100* firstNumber/ total);
        int secondRate = Math.round((float)100* secondNumber/ total);
        int thirdRate = Math.round((float)100* thirdNumber/ total);

        map.put("firstRate", firstRate);
        map.put("secondRate", secondRate);
        map.put("thirdRate", thirdRate);
        return map;
    }
}
