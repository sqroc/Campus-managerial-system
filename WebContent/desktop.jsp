<%@page import="ems.model.Admin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>物业设备管理模拟桌面系统</title>

<link rel="stylesheet" type="text/css"
	href="js/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/examples.css" />
<link rel="stylesheet" type="text/css" href="css/desktop.css" />

<!-- GC -->
<!-- LIBS -->
<script type="text/javascript" src="js/extjs/adapter/ext/ext-base.js"></script>
<!-- ENDLIBS -->

<script type="text/javascript" src="js/extjs/ext-all.js"></script>
<script type='text/javascript' src='dwr/interface/Admin.js'></script>
<script type='text/javascript' src='dwr/interface/Equipment.js'></script>
<script type='text/javascript' src='dwr/interface/Provider.js'></script>
<script type='text/javascript' src='dwr/interface/LogSession.js'></script>
<script type='text/javascript' src='dwr/interface/Permission.js'></script>
<script type='text/javascript' src='dwr/interface/Fettle.js'></script>
<script type='text/javascript' src='dwr/interface/ManualWarn.js'></script>
<script type='text/javascript' src='dwr/interface/LogOverhaul.js'></script>
<script type='text/javascript' src='dwr/interface/Checkoverhaul.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<!-- DESKTOP -->
<script type="text/javascript" src="js/StartMenu.js"></script>
<script type="text/javascript" src="js/TaskBar.js"></script>
<script type="text/javascript" src="js/Desktop.js"></script>
<script type="text/javascript" src="js/App.js"></script>
<script type="text/javascript" src="js/Module.js"></script>
<script type="text/javascript" src="js/ChatWin.js"></script>
<script type="text/javascript" src="js/examples.js"></script>
<script type="text/javascript" src="js/sample.js"></script>
<script type="text/javascript" src="js/accordionWindow.js"></script>

<%
	int rank = 2;
	Admin admin = new Admin();
	if (request.getSession().getAttribute("username") == null) {
%>
<script type="text/javascript">
	window.location.href = "login.html";
</script>
<%
	} else {
		admin = (Admin) request.getSession().getAttribute("username");
		rank = admin.getRank();
	}
%>
<script type="text/javascript">
<!--
	window.onbeforeunload = onbeforeunload_handler;
	window.onunload = onunload_handler;
	function onbeforeunload_handler() {
		var warning = "确认离开此页面?";
		return warning;
	}

	function onunload_handler() {
		Ext.Ajax.request({
			url : 'dialog!dialogOutline.action'
		});
		var warning = "数据保存成功。欢迎您的使用！";
		alert(warning);
	}
// -->
</script>


</head>
<body scroll="no">
	<div id="loading-mask" style="">
		<div id="loading">
			<div style="text-align: center; padding-top: 26%">
				<img src="images/extanim32.gif" width="32" height="32"
					style="margin-right: 8px;" align="middle" />Loading...
			</div>
		</div>
	</div>
	<div id="x-desktop">
		<div
			style="margin: 5px; float: right; color: white; border: 2; font-size: 14px; font-weight: bold;">
			欢迎您,<%=admin.getName()%>（<%
			if (rank == 0) {
				out.print("超级管理员");
			}
			if (rank == 1) {
				out.print("信息管理员");
			}
			if (rank == 2) {
				out.print("楼层管理员");
			}
		%>）
		</div>

		<dl id="x-shortcuts">
			<%
				if (rank == 0) {
			%>

			<dt id="grid-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>用户管理</div> </a>
			</dt>
			<%
				}
			%>

			<dt id="grid2-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>设备管理</div> </a>
			</dt>
			<%
				if (rank < 2) {
			%>
			<dt id="grid3-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>供应商管理</div> </a>
			</dt>
			<%
				}
			%>
			<dt id="grid4-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>故障处理管理</div> </a>
			</dt>
			<%
				if (rank == 0) {
			%>
			<dt id="grid5-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>管理员登录日志</div> </a>
			</dt>
			<%
				}
			%>
			<dt id="grid6-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>故障报告</div> </a>
			</dt>
			<dt id="grid7-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>报销清单</div> </a>
			</dt>
			<dt id="grid8-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>生成统计图</div> </a>
			</dt>
			<dt id="grid9-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>后勤支出记录</div> </a>
			</dt>
			<dt id="grid10-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>维修工管理</div> </a>
			</dt>
			<dt id="acc-win-shortcut">
				<a href="#"><img src="images/s.gif" />
					<div>即时聊天平台</div>
				</a>
			</dt>
		</dl>
	</div>

	<div id="ux-taskbar">
		<div id="ux-taskbar-start"></div>
		<div id="ux-taskbuttons-panel"></div>
		<div class="x-clear"></div>
	</div>

</body>
</html>
