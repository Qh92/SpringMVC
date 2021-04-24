<%--
  Created by IntelliJ IDEA.
  User: Qh
  Date: 2021/4/24
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Success  <br/>

    time : ${requestScope.time} <br/>

    names : ${requestScope.names} <br/>

    request user : ${requestScope.user} <br/>

    session user : ${sessionScope.user} <br/>

    request school : ${requestScope.school} <br/>

    session school : ${sessionScope.school} <br/>
</body>
</html>
