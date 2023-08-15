package filter;

import model.UserModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    private Set<String> registeredURLPatterns;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        registeredURLPatterns = new HashSet<>();
        ServletContext servletContext = filterConfig.getServletContext();
        for (ServletRegistration registration : servletContext.getServletRegistrations().values()) {
            registeredURLPatterns.addAll(registration.getMappings());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getServletPath();

        /*Check URI is required for asset at first time deployment*/
        if (isResourcesURI(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            /* User access other controller which differ "/login" */
            if (!req.getServletPath().startsWith("/login")) {
                /*Check user is login successfully*/
                UserModel loginUser = (UserModel) req.getSession().getAttribute("LOGIN_USER");
                if (loginUser != null) {
                    if (registeredURLPatterns.contains(path)) {
                        if (loginUser.getRoleId() == 1) {
                            /*Current user is admin */
                            handleAdmin(path, servletRequest, servletResponse, filterChain);
                        } else if (loginUser.getRoleId() == 2) {
                            /*Current user is leader */
                            handleLeader(path, servletRequest, servletResponse, filterChain, loginUser);
                        } else {
                            /*Current user is just member*/
                            handleMember(path, servletRequest, servletResponse, filterChain, loginUser);
                        }
                    } else {
                        sendErrorBadRequest(path, servletRequest, servletResponse, filterChain);
                    }
                } else {
                    /* If user is not login we push them to login page */
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            } else {
                /*User want to access login page so let them in */
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isResourcesURI(String URI) {
        return URI.startsWith("/plugins") || URI.startsWith("/bootstrap") ||
                URI.startsWith("/css") || URI.startsWith("/js") || URI.startsWith("/less");
    }

    private void handleAdmin(String path, ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        boolean isValid;
        switch (path) {
            case "/user/edit":
                isValid = isValidParameter(req, "userId") || req.getMethod().equalsIgnoreCase("post");
                break;
            case "/user/details":
                isValid = isValidParameter(req, "userID");
                break;
            case "/group-work/edit":
                isValid = isValidParameter(req, "groupWorkId") || req.getMethod().equalsIgnoreCase("post");
                break;
            case "/group-work/details":
                isValid = isValidParameter(req, "leaderId") && isValidParameter(req, "groupWorkId");
                break;
            case "/task/edit":
                isValid = isValidParameter(req, "taskId") || req.getMethod().equalsIgnoreCase("post");
                break;
            case "/role/edit":
                isValid = isValidParameter(req, "roleId") || req.getMethod().equalsIgnoreCase("post");
                break;
            default:
                isValid = true;
                break;
        }
        if (isValid) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            sendErrorBadRequest(path, servletRequest, servletResponse, filterChain);
        }
    }

    private void handleLeader(String path, ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain, UserModel leaderLogin) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (isAllowedPathOfLeader(path)) {
            switch (path) {
                case "/group-work/details":
                case "/group-work/edit":
                    handleFeatureGroupWorkDetailsOrEditOfLeader(path, req, servletRequest, servletResponse, filterChain, leaderLogin);
                    break;
                case "/group-work/delete":
                    handleFeatureGroupWorkDeleteOfLeader(req, resp, servletRequest, servletResponse, filterChain, leaderLogin);
                    break;
                case "/task/edit":
                    handleFeatureTaskEditOfLeader(path, req, servletRequest, servletResponse, filterChain, leaderLogin);
                    break;
                case "/task/delete":
                    handleFeatureTaskDeleteOfLeader(req, resp, servletRequest, servletResponse, filterChain, leaderLogin);
                    break;
                case "/user/details":
                    handleFeatureUserDetailsOfLeader(path, req, servletRequest, servletResponse, filterChain, leaderLogin);
                    break;
                case "/task/update":
                    handleFeatureTaskUpdateOfLeader(req, resp, servletRequest, servletResponse, filterChain, leaderLogin);
                    break;
                default:
                    filterChain.doFilter(servletRequest, servletResponse);
                    break;
            }
        } else {
            sendErrorForbidden(path, servletRequest, servletResponse, filterChain);
        }
    }

    private void handleFeatureTaskUpdateOfLeader(HttpServletRequest req, HttpServletResponse resp, ServletRequest servletRequest,
                                                 ServletResponse servletResponse, FilterChain filterChain,
                                                 UserModel leaderLogin) throws IOException, ServletException {

        if (isValidParameter(req, "leaderId")) {
            int leaderId = Integer.parseInt(req.getParameter("leaderId"));
            if (leaderId != leaderLogin.getId()) {
                resp.setContentType("text/plain");
                resp.getWriter().write("errorForbidden");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("errorBadRequest");
        }
    }

    private void handleFeatureGroupWorkDetailsOrEditOfLeader(String path, HttpServletRequest req, ServletRequest servletRequest,
                                                             ServletResponse servletResponse, FilterChain filterChain,
                                                             UserModel leaderLogin) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("get")) {
            if (isValidParameter(req, "leaderId") && isValidParameter(req, "groupWorkId")) {
                int leaderId = Integer.parseInt(req.getParameter("leaderId"));
                if (leaderId != leaderLogin.getId()) {
                    sendErrorForbidden(path, servletRequest, servletResponse, filterChain);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else {
                sendErrorBadRequest(path, servletRequest, servletResponse, filterChain);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void handleFeatureGroupWorkDeleteOfLeader(HttpServletRequest req, HttpServletResponse resp,
                                                      ServletRequest servletRequest, ServletResponse servletResponse,
                                                      FilterChain filterChain, UserModel leaderLogin) throws IOException, ServletException {
        if (isValidParameter(req, "leaderId") && isValidParameter(req, "groupWorkId")) {
            int leaderId = Integer.parseInt(req.getParameter("leaderId"));
            if (leaderId != leaderLogin.getId()) {
                resp.setContentType("text/plain");
                resp.getWriter().write("errorForbidden");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("errorBadRequest");
        }
    }

    private void handleFeatureTaskEditOfLeader(String path, HttpServletRequest req,
                                               ServletRequest servletRequest, ServletResponse servletResponse,
                                               FilterChain filterChain, UserModel leaderLogin) throws ServletException, IOException {

        if (req.getMethod().equalsIgnoreCase("get")) {
            if (isValidParameter(req, "leaderId") && isValidParameter(req, "taskId")) {
                int leaderId = Integer.parseInt(req.getParameter("leaderId"));
                if (leaderId != leaderLogin.getId()) {
                    sendErrorForbidden(path, servletRequest, servletResponse, filterChain);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else {
                sendErrorBadRequest(path, servletRequest, servletResponse, filterChain);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    private void handleFeatureTaskDeleteOfLeader(HttpServletRequest req,
                                                 HttpServletResponse resp, ServletRequest servletRequest,
                                                 ServletResponse servletResponse, FilterChain filterChain,
                                                 UserModel leaderLogin) throws IOException, ServletException {

        if (isValidParameter(req, "leaderId")) {
            int leaderId = Integer.parseInt(req.getParameter("leaderId"));
            if (leaderId != leaderLogin.getId()) {
                resp.setContentType("text/plain");
                resp.getWriter().write("errorForbidden");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("errorBadRequest");
        }
    }

    private void handleFeatureUserDetailsOfLeader(String path, HttpServletRequest req, ServletRequest servletRequest,
                                                  ServletResponse servletResponse, FilterChain filterChain,
                                                  UserModel leaderLogin) throws ServletException, IOException {

        handleFeatureUserDetailsHelper(path, req, servletRequest, servletResponse, filterChain, leaderLogin);
    }

    private void handleMember(String path, ServletRequest servletRequest, ServletResponse servletResponse,
                              FilterChain filterChain, UserModel memberLogin) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (isAllowedPathOfMember(path)) {
            switch (path) {
                case "/user/details":
                    handleFeatureUserDetailsOfMember(path, req, servletRequest, servletResponse, filterChain, memberLogin);
                    break;
                case "/task/update":
                    handleFeatureTaskUpdateOfMember(req, resp, servletRequest, servletResponse, filterChain, memberLogin);
                    break;
                case "/task/delete":
                    resp.setContentType("text/plain");
                    resp.getWriter().write("errorForbidden");
                    break;
                default:
                    filterChain.doFilter(servletRequest, servletResponse);
                    break;
            }
        } else {
            sendErrorForbidden(path, servletRequest, servletResponse, filterChain);
        }
    }

    private void handleFeatureTaskUpdateOfMember(HttpServletRequest req, HttpServletResponse resp,
                                                 ServletRequest servletRequest, ServletResponse servletResponse,
                                                 FilterChain filterChain, UserModel memberLogin) throws IOException, ServletException {
        if (isValidParameter(req, "userId")) {
            int memberId = Integer.parseInt(req.getParameter("userId"));
            if (memberId != memberLogin.getId()) {
                resp.setContentType("text/plain");
                resp.getWriter().write("errorForbidden");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("errorBadRequest");
        }
    }

    private void handleFeatureUserDetailsOfMember(String path, HttpServletRequest req,
                                                  ServletRequest servletRequest, ServletResponse servletResponse,
                                                  FilterChain filterChain, UserModel memberLogin) throws ServletException, IOException {
        handleFeatureUserDetailsHelper(path, req, servletRequest, servletResponse, filterChain, memberLogin);
    }

    private void handleFeatureUserDetailsHelper(String path, HttpServletRequest req,
                                     ServletRequest servletRequest, ServletResponse servletResponse,
                                     FilterChain filterChain, UserModel userLogin) throws ServletException, IOException {
        if (isValidParameter(req, "userID")) {
            int userId = Integer.parseInt(req.getParameter("userID"));
            if (userId != userLogin.getId()) {
                sendErrorForbidden(path, servletRequest, servletResponse, filterChain);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            sendErrorBadRequest(path, servletRequest, servletResponse, filterChain);
        }
    }

    boolean isValidParameter(HttpServletRequest req, String parameterName) {
        String parameter = req.getParameter(parameterName);
        return parameter != null && !parameter.trim().isEmpty() && parameter.matches("^[1-9][0-9]*$");
    }

    private boolean isAllowedPathOfLeader(String requestURI) {
        return requestURI.startsWith("/group-work") || requestURI.startsWith("/task") ||
                requestURI.startsWith("/home") || requestURI.startsWith("/login") ||
                requestURI.startsWith("/logout") || requestURI.startsWith("/user/profile") ||
                requestURI.startsWith("/user/details") || requestURI.startsWith("/error");
    }

    private boolean isAllowedPathOfMember(String requestURI) {
        return requestURI.equals("/task") || requestURI.equals("/task/update") ||
                requestURI.startsWith("/home") || requestURI.equals("/task/delete") ||
                requestURI.startsWith("/login") || requestURI.startsWith("/logout") ||
                requestURI.startsWith("/user/profile") || requestURI.startsWith("/user/details") ||
                requestURI.startsWith("/error");
    }

    private void sendErrorForbidden(String path, ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (path.equals("/error-forbidden")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect(req.getContextPath() + "/error-forbidden");
        }
    }

    private void sendErrorBadRequest(String path, ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (path.equals("/error-bad_request")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect(req.getContextPath() + "/error-bad_request");
        }
    }
}


