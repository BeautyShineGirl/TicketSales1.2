<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function testUpload(){  
    var form = new FormData(document.getElementById("test"));  
      
    $.ajax({  
        url : url,  
        data : form,  
        type : 'post',  
        processData:false,  
        contentType:false,  
        success : function(data){  
            alert("成功");  
        },  
        error : function(data){  
              
        }  
    });  
} 
</script>
  </head>
  
  <body>
  	 <form action="custom/contractDownload.action" method="post" style="margin-left:300px;" enctype="multipart/form-data">
    	<!-- 分销商编号：<input type="text" name="id"/><br>
    	合同上传：<input type="file" name="file"/><br>  -->
    	下载:<input type="text" name="fileName"/><br>
    	<input type="submit" value="提交"/>
  </form>
  </body>
</html>
