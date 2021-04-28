<%--
  Created by IntelliJ IDEA.
  User: Qh
  Date: 2021/4/28
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <fmt:message key="i18n.user"/>
    <br/>

    <a href="i18n2"> I18N2 PAGE</a>
    <br/>

    <a href="i18n?locale=zh_CH"> 中文</a>
    <br/>


    <a href="i18n?locale=en_US"> 英文</a>
    <br/>

</body>
</html>
