<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Bootstrap -->
<link href="${ctx}/css/bootstrap/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/list.css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <link rel="stylesheet" type="text/css" href="${ctx}/css/ie_8.css"/>
<![endif]-->
<script type="text/javascript" src="${ctx}/js/jquery-1.11.1.min.js"></script>
<script src="${ctx}/js/bootstrap/bootstrap.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/select/jquery.cityselect.js"
	type="text/javascript"></script>
<script src="${ctx}/js/upload/jquery.ui.widget.js"
	type="text/javascript"></script>
<script src="${ctx}/js/upload/jquery.iframe-transport.js"
	" type="text/javascript"></script>
<script src="${ctx}/js/upload/jquery.fileupload.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="container">
		<jsp:include page="../commons/header.jsp"></jsp:include>
		<div id="content" class="row mt_78">
			<div class="col-md-2" id="left">
				<jsp:include page="left-asset.jsp"></jsp:include>
			</div>
			<div class="col-md-10" id="right">
				<div class="breadcrumb">
					<span>您当前所在的位置：</span> > <a>配置信息</a> > <a>应用系统</a>
				</div>

				<div class="row whitebackground mb_10">
					<div class="span12">
						<h3 class="show_tit">查询</h3>
						<div class="show_conment">
							<form class="form-horizontal" action="${ctx}/asset/appsystem/query.do">
								<div class="form-group">
									<div class="col-xs-12 row">									   
										<div class="col-xs-3 pr_0 pl_0 wid_30">
											<span>应用系统名称：</span> <input type="text"
												class="form-control ml_5 wid_65"  name="name" id="name"
												value="${name}">
										</div>										
									</div>
								</div>

		<div class="col-xs-12 row">  <div class="col-xs-5 pr_0 pl_0 mr_5"> </div>
		 <button class="btn btn-primary mr_20" id="querybotton" type="button" onclick="refresh();">
			<span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;查询
		</button>
		</div>
		
               <div class="col-xs-12 row dpheight15"></div>
							</form>
						</div>
					</div>
				</div>
				<div class="row whitebackground mb_10">
					<div class="span12">
					 <div class="show_tit3">
           <div id="operateBtns">
	<div class="pull-left">
	<h4 class="showtit4">应用系统列表</h4>
	</div>
	<div class="pull-right">
			<div class="pull-right">
		<c:set var="permisions" value="${permisions}" scope="session" /> 
		<c:if test="${fn:contains(permisions, 'asset:add')}">
		<button class="btn btn-primary mr_20" id="addtaskbotton" type="button" onclick="newreport();">
			<span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;添加
		</button>
		 </c:if>
		 <c:if test="${fn:contains(permisions, 'asset:delete')}">
		<button class="btn btn-primary mr_20" type="button" id="deleteButton" onclick="deleteRates();">
			<span class="glyphicon glyphicon-trash"></span>&nbsp;&nbsp;删除
		</button>
		 </c:if>
		  <c:if test="${fn:contains(permisions, 'asset:export')}">
		<button class="btn btn-primary mr_20" type="button" id="exportButton" onclick="exportSystem()">
			<span class="glyphicon glyphicon-new-window"></span>&nbsp;&nbsp;导出
		</button>
		 </c:if>
	</div>
	</div>
</div>
          
          </div>
						<div class="show_conment pad_5">
								<table id="contentTable" class="table table-striped table-bordered table-condensed">
									<thead>
										<tr>
											<th><input type="checkbox" id="allroleselected"
												onclick="selectAll(this)" /></th>
											<th>名称</th>
											<th>描述</th>
											<th>管理</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>
									<c:when test="${!empty rates.result }">
										<c:forEach items="${rates.result}" var="rate">
											<tr>
												<td><input type="checkbox" name="rolecheckbox"
													value="${rate.appId}" /></td>
												<td>${rate.appName}</td>
												<td>${rate.description}</td>
												<c:set var="permisions" value="${permisions}" scope="session" /> 
												<td><c:if test="${fn:contains(permisions, 'asset:modify')}"><a href="${ctx}/asset/appsystem/update_pre.do?id=${rate.appId}">修改 </a></c:if>
												<a href="${ctx}/asset/topo/appsystem.do?appid=${rate.appId}">查看拓扑</a>
												</td> 
											</tr>
										</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="4" class="emptyResut">暂无数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
									<tr style="background-color:#eee">
											<th colspan="4"><tags:pagination page="${rates}" paginationSize="5" />	</th>
										</tr>
									</tbody>
								</table>
              
        </div></div>
      </div>
    </div>
  </div>
		<jsp:include page="../commons/footer.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
	var pageNo =${pageNo};
		//响应删除角色按钮
		function deleteRates() {
			var deleteroleids = "";
			$("input[name='rolecheckbox']:checked").each(function() {
				deleteroleids += $(this).val() + ",";
			});
			if ("" == deleteroleids) {
				$.messager.alert("系统提示", "请选择要删除数据！");
				return;
			}
			$.messager.model = {
				ok : {
					text : "确定",
					classed : 'btn-primary'
				},
				cancel : {
					text : "取消",
					classed : 'btn-error'
				}
			};
			$.messager.confirm("系统提示", "您确定删除所选数据？", function() {
				window.location.href = "${ctx}/asset/appsystem/delete.do?pageNo="+pageNo+"&deleteids="+deleteroleids;
			});
		}
		//全选的动作
		function selectAll(object) {
			if (object.checked) {
				$("input[name='rolecheckbox']").prop("checked", true);
			} else {
				$("input[name='rolecheckbox']").prop("checked", false);
			}
			return false;
		}
		function newreport(){
			window.location.href="${ctx}/asset/appsystem/add_pre.do";
		}
		function refresh(){
			window.location.href="${ctx}/asset/appsystem/query.do?"+getQueryParam();
		}
		function exportSystem(){
			window.location.href="${ctx}/asset/appsystem/export.do?"+getQueryParam();
		}
		function getQueryParam(){
			var Organization = $("#Organization").val();
			var name=$("#name").val();
			return "Organization="+Organization+"&name="+name+"&page="+pageNo;
		}
	</script>
</body>
</html>