package controller;

import model.JobModel;
import service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "groupWorkController", urlPatterns = {"/groupwork", "/groupwork/add", "/groupwork/update", "/groupwork/delete"})

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
            case "/groupwork/delete":
                deleteJob(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/groupwork":

                break;
            case "/groupwork/add":
                addJobs(req, resp);
                break;
            case "/groupwork/edit":

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


}
