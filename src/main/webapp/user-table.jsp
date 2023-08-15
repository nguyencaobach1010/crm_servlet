<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="<c:url value="/plugins/images/favicon.png"/>">
    <title>CRM</title>
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="<c:url value="/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
    <!-- animation CSS -->
    <link href="<c:url value="/css/animate.css"/>" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <!-- color CSS -->
    <link href="<c:url value="/css/colors/blue-dark.css"/>" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/css/custom.css"/>">
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
                        <img src="<c:url value="/plugins/images/pixeladmin-logo.png"/>" alt="home" />
                    </b>
                    <span class="hidden-xs">
                                <img src="<c:url value="/plugins/images/pixeladmin-text.png"/>" alt="home" />
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
                            <img src="<c:url value="/plugins/images/users/${loginUser.getAvatar()}"/>" alt="user-img" width="36" class="img-circle" />
                            <b class="hidden-xs"><c:out value="${loginUser.getFullName()}"/></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/user/profile"/>">Thông tin cá nhân</a></li>
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
                    <a href="<c:url value="/task"/>" class="waves-effect"><i class="fa fa-table fa-fw"
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
                    <h4 class="page-title">Danh sách thành viên</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                    <a href="<c:url value="/user/add"/>" class="btn btn-sm btn-success">Thêm mới</a>
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
                                    <th>Họ tên</th>
                                    <th>Email</th>
                                    <th>Vai trò</th>
                                    <th>Hành động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${listAllUsers}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${user.getFullName()}</td>
                                        <td>${user.getEmail()}</td>
                                        <c:set var="roleId" value="${user.getRoleId()}" />
                                        <c:set var="roleName" value="Chưa phân quyền" />
                                        <c:forEach var="role" items="${listAllRoles}">
                                            <c:if test="${role.id eq roleId}">
                                                <c:set var="roleName" value="${role.getDescription()}" />
                                            </c:if>
                                        </c:forEach>
                                        <td>${roleName}</td>
                                        <td>
                                            <a href="<c:url value="/user/edit?userId=${user.getId()}"/>" class="btn btn-sm btn-primary">Sửa</a>
                                            <a href="#" userID="${user.getId()}" class="btn btn-sm btn-danger btn-delete-user">Xóa</a>
                                            <a href="<c:url value="/user/details?userID=${user.getId()}"/>" class="btn btn-sm btn-info">Xem</a>
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
<!-- jQuery -->
<script src="<c:url value="/plugins/bower_components/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap Core JavaScript -->
<script src="<c:url value="/bootstrap/dist/js/bootstrap.min.js"/>"></script>
<!-- Menu Plugin JavaScript -->
<script src="<c:url value="/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"/>"></script>
<!--Slimscroll JavaScript -->
<script src="<c:url value="/js/jquery.slimscroll.js"/>"></script>
<script src="<c:url value="/js/jquery.dataTables.js"/>"></script>
<!--Wave Effects -->
<script src="<c:url value="/js/waves.js"/>"></script>
<!-- Custom Theme JavaScript -->
<script src="<c:url value="/js/custom.min.js"/>"></script>
<%--Delete user without loading page--%>
<script src="<c:url value="/js/user-table.js"/>"></script>
<script>
    $(document).ready(function () {
        $('#example').DataTable();
    });
</script>
</body>

</html>