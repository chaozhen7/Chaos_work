<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>User List</title>
    <style type="text/css">
        <!--
        .STYLE1 {
            font-family: Arial, Helvetica, sans-serif;
            font-weight: bold;
            font-size: 36px;
            color: #FF0000;
        }

        .STYLE13 {
            font-size: 24
        }

        .STYLE15 {
            font-family: Arial, Helvetica, sans-serif;
            font-size: 24px;
        }

        -->
    </style>
</head>

<body>
<table width="150" height="60" border="0" cellpadding="0"
       cellspacing="0">
    <tr>
        <td width="50" height="20">&nbsp;</td>
        <td width="50" height="20" align="center" valign="middle">
            <div
                    align="center">
                <span class="STYLE1">列表 </span>
            </div>
        </td>
        <td width="50" height="20">&nbsp;</td>
    </tr>
    <tr>
        <td width="50" height="20">&nbsp;</td>
        <td width="50" height="20">
            <table width="50" height="20"
                   border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="80" height="30" align="center" valign="middle"><span
                            class="STYLE15">ID</span></td>
                    <td width="80" height="30" align="center" valign="middle"><span
                            class="STYLE15">name</span></td>
                    <td width="80" height="30" align="center" valign="middle"><span
                            class="STYLE15">操作</span></td>
                </tr>
            <#list userlist as user>
                <tr>
                    <td width="80" height="30" align="center" valign="middle"><span
                            class="STYLE15">${user.id}</span></td>
                    <td width="80" height="30" align="center" valign="middle"><span
                            class="STYLE15"><a target="_blank"
                                               href="/chaos/user/show/${user.id}">${user.username}</a></span>
                    </td>
                    <td width="80" height="30" align="center" valign="middle"><span
                            class="STYLE15"><a href="/chaos/user/del/${user.id}">删除</a></span>
                    </td>
                </tr>
            </#list>
            </table>
        </td>
        <td width="50" height="20">&nbsp;</td>
    </tr>
    <tr>
        <td width="50" height="20">&nbsp;</td>
        <td width="50" height="20">&nbsp;</td>
        <td width="50" height="20">&nbsp;</td>
    </tr>
</table>
</body>
</html>