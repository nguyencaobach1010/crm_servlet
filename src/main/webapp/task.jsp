<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="plugins/images/favicon.png">
    <title>CRM</title>
    <!-- Bootstrap Core CSS -->
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
    <!-- animation CSS -->
    <link href="css/animate.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">
    <!-- color CSS -->
    <link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="./css/custom.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<!-- Preloader -->
<div class="preloader">
    <div class="cssload-speeding-wheel"></div>
</div>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top m-b-0">
        <div class="navbar-header">
            <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse">
                <i class="fa fa-bars"></i>
            </a>
            <div class="top-left-part">
                <a class="logo" href="<c:url value="/home"/>">
                    <b>
                        <img src="plugins/images/pixeladmin-logo.png" alt="home" />
                    </b>
                    <span class="hidden-xs">
                                <img src="plugins/images/pixeladmin-text.png" alt="home" />
                            </span>
                </a>
            </div>
            <ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
                <li>
                    <form role="search" class="app-search hidden-xs">
                        <input type="text" placeholder="Search..." class="form-control">
                        <a href="">
                            <i class="fa fa-search"></i>
                        </a>
                    </form>
                </li>
            </ul>
            <ul class="nav navbar-top-links navbar-right pull-right">
                <li>
                    <div class="dropdown">
                        <a class="profile-pic dropdown-toggle" data-toggle="dropdown" href="#">
                            <img src="plugins/images/users/${loginUser.getAvatar()}" alt="user-img" width="36" class="img-circle" />
                            <b class="hidden-xs"><c:out value="${loginUser.getFullName()}"/></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/user/profile"/> ">Thông tin cá nhân</a></li>
                            <li><a href="<c:url value="/user/details?userID=${loginUser.getId()}"/>">Thống kê công việc</a></li>
                            <li class="divider"></li>
                            <li><a href="<c:url value="/logout"/>">Đăng xuất</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
        <!-- /.navbar-header -->
        <!-- /.navbar-top-links -->
        <!-- /.navbar-static-side -->
    </nav>
    <!-- Left navbar-header -->
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse slimscrollsidebar">
            <ul class="nav" id="side-menu">
                <li style="padding: 10px 0 0;">
                    <a href="<c:url value="/home"/>" class="waves-effect"><i class="fa fa-clock-o fa-fw"
                                                                aria-hidden="true"></i><span class="hide-menu">Dashboard</span></a>
                </li>
                <li>
                    <a href="<c:url value="/user"/>" class="waves-effect"><i class="fa fa-user fa-fw"
                                                                     aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a>
                </li>
                <li>
                    <a href="<c:url value="/role"/>" class="waves-effect"><i class="fa fa-modx fa-fw"
                                                                     aria-hidden="true"></i><span class="hide-menu">Quyền</span></a>
                </li>
                <li>
                    <a href="<c:url value="/group-work"/>" class="waves-effect"><i class="fa fa-table fa-fw"
                                                                    aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                </li>
                <li>
                    <a href="<c:url value="/task"/> " class="waves-effect"><i class="fa fa-table fa-fw"
                                                                aria-hidden="true"></i><span class="hide-menu">Công việc</span></a>
                </li>
            </ul>
        </div>
    </div>
    <!-- Left navbar-header end -->
    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">Danh sách công việc</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                    <a href="<c:url value="/task/add"/>" class="btn btn-sm btn-success">Thêm mới</a>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /row -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="white-box">
                        <div class="table-responsive">
                            <table class="table" id="example">
                                <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên Công Việc</th>
                                        <th>Dự Án</th>
                                        <th>Người Thực Hiện</th>
                                        <th>Ngày Bắt Đầu</th>
                                        <th>Ngày Kết Thúc</th>
                                        <th>Trạng Thái</th>
                                        <th>Hành Động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="task" items="${listAllTasks}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>${task.getTaskName()}</td>
                                            <td>${task.getGroupWorkName()}</td>
                                            <td>${task.getUserName()}</td>
                                            <td><fmt:formatDate pattern="dd-MM-yyyy" value="${task.getStartDate()}"/></td>
                                            <td><fmt:formatDate pattern="dd-MM-yyyy" value="${task.getEndDate()}"/></td>
                                            <td>${task.getStatusName()}</td>
                                            <td>
                                                <a href="<c:url value="/task/edit?taskId=${task.getId()}&leaderId=${task.getLeaderId()}&userIdTask=${task.getUserId()}"/> "
                                                   class="btn btn-sm btn-primary">Sửa</a>
                                                <button type="button"
                                                        class="btn btn-sm btn-danger btn-delete-task"
                                                        taskId="${task.getId()}"
                                                        leaderId="${task.getLeaderId()}">Xóa
                                                </button>
                                                <button type="button" class="btn btn-sm btn-warning btn-update-task"
                                                        data-toggle="modal" data-target="#taskModal"
                                                        group-work-name="${task.getGroupWorkName()}"
                                                        task-name="${task.getTaskName()}"
                                                        task-id="${task.getId()}"
                                                        task-start-date="${task.getStartDate()}"
                                                        task-end-date="${task.getEndDate()}"
                                                        style="margin-top: 3.5px;width: 58%"
                                                        leaderId="${task.getLeaderId()}"
                                                        user-id="${task.getUserId()}">
                                                    Cập nhật
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
        <footer class="footer text-center"> 2023 - Cybersoft </footer>
    </div>
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->
<div class="modal fade" id="taskModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLabel">CẬP NHẬT CÔNG VIỆC</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal form-material">
                    <div class="form-group">
                        <label class="col-md-12">Tên dự án</label>
                        <div class="col-md-12">
                            <span id="groupWorkName" class="form-control form-control-line"></span>
                            <input type="hidden" id="leaderID" name="leaderID">
                            <input type="hidden" id="user-id" name="user-id">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-12">Tên công việc</label>
                        <div class="col-md-12">
                            <span id="taskName" class="form-control form-control-line"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-12" for="start-date">Ngày bắt đầu</label>
                        <input type="date" name="start-date" id="start-date"
                        <%--                                       style="width: 100%; height: 30px"--%>
                               class="form-control form-control-line" required >
                    </div>
                    <div class="form-group">
                        <label class="col-md-12" for="end-date">Ngày kết thúc</label>
                        <input type="date" name="end-date" id="end-date"
                        <%--                                       style="width: 100%; height: 30px"--%>
                               class="form-control form-control-line" required>
                    </div>
                    <div class="form-group">
                        <label class="col-md-12" for="select-status">Trạng thái</label>
                        <div class="col-md-12">
                            <select class="form-control form-control-line" name="select-status" id="select-status" required>
                                <c:forEach var="status" items="${listStatus}">
                                    <option value="${status.getId()}">${status.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <button type="submit" class="btn btn-success btn-save-update" data-dismiss="modal">
                                Lưu lại
                            </button>
                            <button class="btn btn-primary" data-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- jQuery -->
<script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="bootstrap/dist/js/bootstrap.min.js"></script>
<!-- Menu Plugin JavaScript -->
<script src="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
<!--slimscroll JavaScript -->
<script src="js/jquery.slimscroll.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<!--Wave Effects -->
<script src="js/waves.js"></script>
<!-- Custom Theme JavaScript -->
<script src="js/custom.min.js"></script>
<script>
    $(document).ready(function () {
        $('#example').DataTable();
    });
</script>
<script src="js/task.js"></script>
</body>

</html>