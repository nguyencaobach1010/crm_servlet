package controller;

import dto.StatusDTO;
import dto.TaskDTO;
import model.RoleModel;
import model.UserModel;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@WebServlet(name = "userServlet", urlPatterns = {"/user", "/user/add", "/user/delete", "/user/details", "/user/edit", "/user/profile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel currentUser = (UserModel) session.getAttribute("LOGIN_USER");

        String path = req.getServletPath();
        switch (path) {
            case "/user":
                displayAllUser(req, resp, currentUser);
                break;
            case "/user/add":
                addUser(req, resp, currentUser);
                break;
            case "/user/delete":
                deleteUser(req);
                break;
            case "/user/details":
                userDetails(req, resp, currentUser);
                break;
            case "/user/edit":
                editUser(req, resp, currentUser);
                break;
            case "/user/profile":
                userProfile(req, resp, currentUser);
                break;
            default:
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel currentUser = (UserModel) session.getAttribute("LOGIN_USER");

        String path = req.getServletPath();
        switch (path) {
            case "/user":
                displayAllUser(req, resp, currentUser);
                break;
            case "/user/add":
                addUser(req, resp, currentUser);
                break;
            case "/user/profile":
                userProfile(req, resp, currentUser);
                break;
            case "/user/edit":
                editUser(req, resp, currentUser);
                break;
            default:
                break;
        }
    }

    private void displayAllUser(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        List<UserModel> listAllUsers = userService.getAllUsers();
        List<RoleModel> listAllRoles = userService.getAllRoles();
        req.setAttribute("listAllUsers", listAllUsers);
        req.setAttribute("listAllRoles", listAllRoles);
        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("user-table.jsp").forward(req, resp);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        List<RoleModel> listAllRoles = userService.getAllRoles();
        req.setAttribute("listAllRoles", listAllRoles);
        String method = req.getMethod();

        if (method.equalsIgnoreCase("post")) {
            String fullName = req.getParameter("full-name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            int roleId = Integer.parseInt(req.getParameter("select-role"));

            String fileName = getUploadedAvatar(req);

            userService.insertUser(fullName, email, password, fileName, roleId);
        }
        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("/user-add.jsp").forward(req, resp);
    }

    private void deleteUser(HttpServletRequest req) {
        int userId = Integer.parseInt(req.getParameter("userID"));
        userService.getUserById(userId).ifPresent(userModel -> deleteAvatar(userModel.getAvatar()));
        userService.deleteUser(userId);
    }

    private void userDetails(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userID"));

        List<TaskDTO> notStartedTasks = userService.getTasksByUserIdAndStatusId(userId, 1);
        List<TaskDTO> inProgressTasks = userService.getTasksByUserIdAndStatusId(userId, 2);
        List<TaskDTO> completedTasks = userService.getTasksByUserIdAndStatusId(userId, 3);

        Optional<UserModel> userModelOptional = userService.getUserById(userId);
        if (userModelOptional.isPresent()) {
            req.setAttribute("email", userModelOptional.get().getEmail());
            req.setAttribute("username", userModelOptional.get().getFullName());
            req.setAttribute("avatar", userModelOptional.get().getAvatar());
        }
        Map<String, Integer> rateMap = getAverageRate(notStartedTasks.size(), inProgressTasks.size(), completedTasks.size());
        req.setAttribute("notStartedRate", rateMap.get("firstRate"));
        req.setAttribute("inProgressRate", rateMap.get("secondRate"));
        req.setAttribute("completedRate", rateMap.get("thirdRate"));

        req.setAttribute("notStartedTasks", notStartedTasks);
        req.setAttribute("inProgressTasks", inProgressTasks);
        req.setAttribute("completedTasks", completedTasks);

        req.setAttribute("loginUser", currentUser);
        req.getRequestDispatcher("/user-details.jsp").forward(req, resp);
    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        String method = req.getMethod();
        boolean isEdited = false;

        List<RoleModel> listAllRoles = userService.getAllRoles();
        req.setAttribute("listAllRoles", listAllRoles);
        if (method.equalsIgnoreCase("get") && req.getParameter("userId") != null) {
            Optional<UserModel> userModelOptional =  userService.getUserById(Integer.parseInt(req.getParameter("userId")));
            if (userModelOptional.isPresent()) {
                UserModel userModel = userModelOptional.get();
                req.setAttribute("editedUser", userModel);
            }
        }
        if (method.equalsIgnoreCase("post")) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            String fullName = req.getParameter("full-name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            int roleId = Integer.parseInt(req.getParameter("select-role"));
            String avatar = getUploadedAvatar(req);

            isEdited =  userService.updateUser(userId, fullName, email, password, avatar, roleId);
            resp.sendRedirect(req.getContextPath() + "/user");
        }
        req.setAttribute("loginUser", currentUser);
        if (!isEdited) {
            req.getRequestDispatcher("/user-edit.jsp").forward(req, resp);
        }
    }

    private void userProfile(HttpServletRequest req, HttpServletResponse resp, UserModel currentUser) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equalsIgnoreCase("post")) {
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            Date startDate = Date.valueOf(req.getParameter("startDate"));
            Date endDate = Date.valueOf(req.getParameter("endDate"));
            int statusId = Integer.parseInt(req.getParameter("selectStatus"));

            userService.updateTaskProgress(taskId, startDate, endDate, statusId);
        }

        List<TaskDTO> notStartedTasks = userService.getTasksByUserIdAndStatusId(currentUser.getId(), 1);
        List<TaskDTO> inProgressTasks = userService.getTasksByUserIdAndStatusId(currentUser.getId(), 2);
        List<TaskDTO> completedTasks = userService.getTasksByUserIdAndStatusId(currentUser.getId(), 3);

        Map<String, Integer> rateMap = getAverageRate(notStartedTasks.size(), inProgressTasks.size(), completedTasks.size());
        req.setAttribute("notStartedRate", rateMap.get("firstRate"));
        req.setAttribute("inProgressRate", rateMap.get("secondRate"));
        req.setAttribute("completedRate", rateMap.get("thirdRate"));

        List<TaskDTO> listTasksOfLoginUser = userService.getTasksByUserId(currentUser.getId());
        req.setAttribute("listTasksOfLoginUser", listTasksOfLoginUser);
        req.setAttribute("loginUser", currentUser);

        List<StatusDTO> statusList = userService.getAllStatus();
        req.setAttribute("listStatus", statusList);

        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    private Map<String, Integer> getAverageRate(int firstNumber, int secondNumber, int thirdNumber) {
        Map<String, Integer> rateMap = new HashMap<>(3);
        int total = firstNumber + secondNumber + thirdNumber;
        int firstRate = Math.round((float)100* firstNumber/ total);
        int secondRate = Math.round((float)100* secondNumber/ total);
        int thirdRate = Math.round((float)100* thirdNumber/ total);
        rateMap.put("firstRate", firstRate);
        rateMap.put("secondRate", secondRate);
        rateMap.put("thirdRate", thirdRate);
        return rateMap;
    }

    private String getUploadedAvatar(HttpServletRequest req) throws ServletException, IOException {
        String fileName = "default.jpg";

        Part imagePart = req.getPart("imageFile");
        if (imagePart != null && imagePart.getSize() > 0) {
            String appRootDir = getServletContext().getRealPath("/");
            String uploadDir = appRootDir + "plugins/images/users";
            String absoluteUploadDir = new File(uploadDir).getAbsolutePath();
            fileName = imagePart.getSubmittedFileName();
            String filePath = absoluteUploadDir + File.separator + fileName;
            imagePart.write(filePath);
        }
        return fileName;
    }

    private void deleteAvatar(String fileName) {
        if (!fileName.equals("default.jpg")) {
            String appRootDir = getServletContext().getRealPath("/");
            String uploadDir = appRootDir + "plugins/images/users";
            String absoluteDir = new File(uploadDir).getAbsolutePath();
            String filePath = absoluteDir + File.separator + fileName;

            File file = new File(filePath);
            file.delete();
        }
    }
}
