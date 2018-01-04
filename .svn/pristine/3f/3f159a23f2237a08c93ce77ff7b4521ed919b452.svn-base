<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>部门列表</title>

<script type="text/javascript">
	function getUser(){
		$.ajax({
			dataType : "json",
			type : "post",
			url : "${pageContext.request.contextPath}/user/list.action",
			success: function(test){
				alert(test);
			}
		});
	}
</script>
</head>
<body>
	success
</body>
</html>

