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
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/select.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/udialog.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.js"></script>
<script src="${pageContext.request.contextPath}/js/paging.tools.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/select-ui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/date.util.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
	var deptId = "";
	$(function(){
		loadTreeData();
		
		$(".seachform").find(".scbtn").bind("click", function(){
			// 点击查询时需要重置部门Id
			deptId = "";
			loadDeptList(1);
		});
		loadDeptList(1);
	});
	
	//初始化树
	function initTree(depts, callBack){
		var setting = {
			view: {dblClickExpand: false, showLine: true,selectedMulti: false},
			data: {
				simpleData: {enable:true, idKey: "id", pIdKey: "pid", rootPId: ""}
			},
			callback: {
				beforeClick: function(treeId, treeNode) {
					if(callBack){
						callBack(treeNode);
					}
				}
			}
		};
		var widget = $("#tree");
		widget.html("");
		$.fn.zTree.init(widget, setting, depts);
	}
	
	// 加载菜单列表
	function loadTreeData(){
		$.ajax({
			dataType : "json",
			type : "post",
			url : "${pageContext.request.contextPath}/dept/getTreeData.action",
			success: function(depts){
				initTree(depts, function(treeNode){
					deptId = treeNode.id;
					loadDeptList(1);
				});
			}
		});
	}
	
	function deleteDept(id){
		if(confirm("你确认删除记录吗?")){
			$.ajax({
				dataType : "json",
				type : "post",
				url : "${pageContext.request.contextPath}/data/option.json",
				data: {id: id},
				success: function(result){
					if(result.code == "1"){
						loadTreeData();
						loadDeptList(1);
						alert("删除成功!");
					}else if(result.code == 3){
						alert("删除失败，必须先删除子节点!");
					}else if(result.code == 4){
						alert("删除失败，必须先删除节点下的所有用户!");
					}else{
						alert("删除失败!");
					}
				},
				error: function(){
					alert("删除失败, 请重试!");
				}
			});
		}
	}
	
	// 分页查询
	function loadDeptList(pageIndex){
		var deptName = $(".seachform #deptName").val();
		var params = {pageIndex: pageIndex, pageSize:3, deptId:deptId, deptName:deptName};
		var dataItems = $("#dataItems");
		$(dataItems).find("tbody").find("tr").remove();
		$.ajax({
			dataType : "json",
			type : "post",
			url : "${pageContext.request.contextPath}/dept/findPageData.action",
			data: params,
			success: function(paging){
				var total = paging.allCount;
				var pageTotal = paging.pageCount;
				if(total <= 0){
					rowHtml = "<tr><td colspan='5'>没有检索到可用数据</td></tr>";
				}else{
					for(var i=0; i<paging.datas.length; i++){
						var row = paging.datas[i];
						var rowHtml = "<tr " + (i % 2 != 0?"class='odd'":"") + ">";
						rowHtml += "<td>" + row.name + "</td>";
						rowHtml += "<td>" + (row.parent != null?row.parent.name:"") + "</td>";
						rowHtml += "<td>" + new Date(row.createTime).format("yyyy-MM-dd hh:mm:ss") + "</td>";
						rowHtml += "<td>";
						if("${LoginUser.dept.id}" != row.id){
							// 不能修改自己所在部门信息
							rowHtml += "<a href='deptUpdate.html?id=" + row.id + "'>修改</a>";
							rowHtml += "&nbsp;&nbsp;<a href='javascript:deleteDept(\"" + row.id + "\");'>删除</a>";
						}
						rowHtml += "</td>";
						rowHtml += "</tr>";
						$(dataItems).find("tbody").append(rowHtml);
					}
				}
				initPagingTools(total, pageTotal, pageIndex, loadDeptList);
			}
		});
	}
</script>
</head>
<body>
	success
</body>
</html>

