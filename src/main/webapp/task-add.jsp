<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="<c:url value="/plugins/images/favicon.png"/> ">
    <title>CRM</title>
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="<c:url value="/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css"/>" rel="stylesheet">
    <!-- animation CSS -->
    <link href="<c:url value="/css/animate.css"/>" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <!-- color CSS -->
    <link href="<c:url value="/css/colors/blue-dark.css"/>" id="theme" rel="stylesheet">
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
            <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse"
               data-target=".navbar-collapse">
                <i class="fa fa-bars"></i>
            </a>
            <div class="top-left-part">
                <a class="logo" href="<c:url value="/home"/>">
                    <b>
                        <img src="<c:url value="/plugins/images/pixeladmin-logo.png"/>" alt="home"/>
                    </b>
                    <span class="hidden-xs">
                            <img src="<c:url value="/plugins/images/pixeladmin-text.png"/>" alt="home"/>
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
                            <img src="<c:url value="/plugins/images/users/${loginUser.getAvatar()}"/>" alt="user-img" width="36"
                                 class="img-circle"/>
                            <b class="hidden-xs"><c:out value="${loginUser.getFullName()}"/></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/user/profile"/> ">Thông tin cá nhân</a></li>
                            <li><a href="<c:url value="/user/details?userID=${loginUser.getId()}"/>">Thống kê công
                                việc</a></li>
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
                    <a href="<c:url value="/home"/> " class="waves-effect"><i class="fa fa-clock-o fa-fw"
                                                                              aria-hidden="true"></i><span
                            class="hide-menu">Dashboard</span></a>
                </li>
                <li>
                    <a href="<c:url value="/user"/>" class="waves-effect"><i class="fa fa-user fa-fw"
                                                                             aria-hidden="true"></i><span
                            class="hide-menu">Thành viên</span></a>
                </li>
                <li>
                    <a href="<c:url value="/role"/>" class="waves-effect"><i class="fa fa-modx fa-fw"
                                                                             aria-hidden="true"></i><span
                            class="hide-menu">Quyền</span></a>
                </li>
                <li>
                    <a href="<c:url value="/group-work"/>" class="waves-effect"><i class="fa fa-table fa-fw"
                                                                                   aria-hidden="true"></i><span
                            class="hide-menu">Dự án</span></a>
                </li>
                <li>
                    <a href="<c:url value="/task"/> " class="waves-effect"><i class="fa fa-table fa-fw"
                                                                              aria-hidden="true"></i><span
                            class="hide-menu">Công việc</span></a>
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
                    <h4 class="page-title">Thêm công việc</h4>
                </div>
            </div>
            <!-- /.row -->
            <!-- .row -->
            <div class="row">
                <div class="col-md-2 col-12"></div>
                <div class="col-md-8 col-xs-12">
                    <div class="white-box">
                        <form class="form-horizontal form-material" method="post" action="<c:url value="/task/add"/> ">
                            <div class="form-group">
                                <label class="col-sm-12" for="select-groupWork">Chọn dự án</label>
                                <div class="col-sm-12">
                                    <select class="form-control form-control-line" name="select-groupWork"
                                            id="select-groupWork" required>
                                        <c:forEach var="work" items="${listGroupWork}">
                                            <option value="${work.getId()}">${work.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-12" for="task-name">Tên công việc</label>
                                <div class="col-md-12">
                                    <input type="text" class="form-control form-control-line"
                                           placeholder="Nhập tên công việc"
                                           name="task-name" id="task-name" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12" for="start-date">Ngày bắt đầu</label>
                                <input type="date" name="start-date" id="start-date"
                                       class="form-control form-control-line" required>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12" for="end-date">Ngày kết thúc</label>
                                <input type="date" name="end-date" id="end-date"
                                       class="form-control form-control-line" required>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12" for="select-member">Thành viên phụ trách</label>
                                <div class="col-md-12">
                                    <select class="form-control form-control-line" name="select-member"
                                            id="select-member" required>
                                        <c:forEach var="member" items="${listMember}">
<%--                                            <option value="${member.getId()}">${member.getFullName()}</option>--%>
                                            <option value="${member.getId()}">
                                                <c:choose>
                                                    <c:when test="${member.getRoleId() eq 2}">Quản lý:  ${member.getFullName()}</c:when>
                                                    <c:when test="${member.getRoleId() eq 3}">Nhân viên:  ${member.getFullName()}</c:when>
                                                    <c:otherwise>${member.getFullName()}</c:otherwise>
                                                </c:choose>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12" for="select-status">Trạng thái</label>
                                <div class="col-md-12">
                                    <select class="form-control form-control-line" name="select-status"
                                            id="select-status" required>
                                        <c:forEach var="status" items="${listStatus}">
                                            <option value="${status.getId()}">${status.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <button type="submit" class="btn btn-success">Lưu lại</button>
                                    <a href="<c:url value="/task"/>" class="btn btn-primary">Quay lại</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-2 col-12"></div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
        <footer class="footer text-center"> 2023 - Cybersoft</footer>
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
<!--slimscroll JavaScript -->
<script src="<c:url value="/js/jquery.slimscroll.js"/>"></script>
<!--Wave Effects -->
<script src="<c:url value="/js/waves.js"/>"></script>
<!-- Custom Theme JavaScript -->
<script src="<c:url value="/js/custom.min.js"/>"></script>
</body>

</html>