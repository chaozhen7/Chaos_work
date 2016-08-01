<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>添加用户</title>
</head>

<body>
    <#--<!-- 自动绑定 &ndash;&gt;-->
    <#--<form:form modelAttribute="user" method="post">-->
        <#--用户名：<form:input path="username"/><br/>-->
        <#--密 码：<form:password path="password"/><br/>-->
        <#--<input type="submit" value="提交" />-->
    <#--</form:form>-->
    <!-- 非自动绑定 -->
    <form action="user/addnotauto"  method="post">
        用户名:<input type="text" name="username" /><br/>
        密码:<input type="password" name="password" /><br/>
        头像:<input type="file" name="formFile" /><br/>
        <#--<input type="file" name="formFile" /><br/>-->
        <#--<input type="file" name="formFile" /><br/>-->
        <input type="submit" value="提交" />
    </form>
</body>
</html>