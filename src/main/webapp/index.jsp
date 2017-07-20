<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Welcome</title>
	<link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<hr/>
<form action="<%=request.getContextPath() %>/config?key=hello" class="form-horizontal" method="post">
	<div class="form-group">
		<label for="firstname" class="col-sm-2 control-label">Username: </label>
		<div class="col-sm-10"  style="width:70%">
			<input type="text" class="form-control" id="name"  placeholder="请输入姓名">
		</div>
	</div>
	<div class="form-group">
		<label for="lastname" class="col-sm-2 control-label">UserSex:</label>
		<div class="col-sm-10" style="width:70%">
			<input type="text" class="form-control" name="sex" placeholder="请输入性别">
		</div>
	</div>
	<div class="form-group">
		<label for="lastname" class="col-sm-2 control-label">UserAge:</label>
		<div class="col-sm-10" style="width:70%">
			<input type="text" class="form-control" name="age" placeholder="请输入年龄">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" class="btn btn-default" value="提交"/>
		</div>
	</div>
</form>
<script src="<%=request.getContextPath() %>/js/jquery-3.1.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
</body>
</html>