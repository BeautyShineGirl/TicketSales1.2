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
    function imgPreview(fileDom){
        //判断是否支持FileReader
        if (window.FileReader) {
            var reader = new FileReader();
        } else {
            alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
        }

        //获取文件
        var file = fileDom.files[0];
        var imageType = /^image\//;
        //是否是图片
        if (!imageType.test(file.type)) {
            alert("请选择图片！");
            return;
        }
        //读取完成
        reader.onload = function(e) {
            //获取图片dom
            var img = document.getElementById("preview");
            //图片路径设置为读取的图片
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
</script>
<style type="text/css">
	#name{
		width: 100px;
		height: 30px;
		border: 1px,solid,gray;
	}
	#select{
		width: 100px;
		height: 30px;
		border: 1px,solid,gray;
	}
</style>
  </head>
  
  <body>
  	 <form action="view/add.action" method="post" style="margin-left:300px;" enctype="multipart/form-data">
    	<img id="preview" /><br>
    	上传文-件：<input type="file" name="file" multiple="multiple"  onchange="imgPreview(this)"/><br><br>
    	名字：<input type="text" name="name" id="name"/><br><br>
    	所属省份：<select id="select" name="province">
    		<option>陕西</option>
    		<option>山西</option>
    	</select>
    	<br>
    	<input type="submit" value="提交"/>
    </form>	 
    
     <!--    <form action="view/add.action" method="post"  >    
        选择文件:<input data-role="none" type="file" name="file" width="120px">    
        <button data-role="none" onclick="testUpload();">测试</button>  
        <input type="submit" value="提交"/>
    </form>   -->
  </body>
</html>
