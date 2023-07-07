package controller;

import config.MysqlConfig;
import model.JobModel;
import model.RoleModel;
import service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@WebServlet(name = "groupWorkController", urlPatterns = {"/groupwork", "/groupwork/add", "/groupwork/edit", "/groupwork/delete"})

public class GroupWorkController extends HttpServlet {
    private JobService jobService = new JobService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/groupwork":
                getAllJob(req, resp);
                break;
            case "/groupwork/add":
                addJobs(req, resp);
                break;
            case "/groupwork/edit":
                updateJobById(req, resp);
                break;
            case "/groupwork/delete":
                deleteJob(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/groupwork/add":
                addJobs(req, resp);
                break;
            case "/groupwork/edit":
                updateJobById(req, resp);
                break;
            case "/groupwork/delete":

                break;
            default:

                break;
        }
    }
    private void getAllJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobModel> listJob = jobService.getALlJobs();
        req.setAttribute("listJobs", listJob);
        req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
    }

    private void addJobs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getMethod();
        List<JobModel> listJob = jobService.getALlJobs();
        if (method.equalsIgnoreCase("post")) {
            String name = req.getParameter("name");
            Date start_date = Date.valueOf(req.getParameter("start_date"));
            Date end_date = Date.valueOf(req.getParameter("end_date"));
            jobService.insertJobs(name, start_date, end_date );

        }
        req.setAttribute("listJobs", listJob);
        req.getRequestDispatcher("/groupwork-add.jsp").forward(req, resp);
    }

    private void deleteJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id =  Integer.parseInt(req.getParameter("id"));
        boolean isSucess = jobService.deleteJobById(id);
    }

    private void updateJobById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        JobModel job = jobService.getJobById(id);
        String method = req.getMethod();
        if (method.equalsIgnoreCase("post")) {

            String name = req.getParameter("name");
            Date startDate = Date.valueOf(req.getParameter("start_date"));
            Date endDate = Date.valueOf(req.getParameter("end_date"));
            System.out.println(endDate);
            jobService.updateJobs(id, name, startDate, endDate);

        }
        req.setAttribute("job", job);
        req.getRequestDispatcher("/groupwork-edit.jsp").forward(req, resp);
    }


}
