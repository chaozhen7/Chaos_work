<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户详情</title>
</head>

<body>
        ${user.username}
        <br/>
        ${user.createDate?datetime}

<br/>
<a target="_self" href="/chaos/user/userlist">参看所有</a>
</body>
</html>