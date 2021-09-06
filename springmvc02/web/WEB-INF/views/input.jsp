<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Qh
  Date: 2021/4/26
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="testConversionServiceConverter" method="post">
        <%-- lastname-email-gender-department.id  例如: GG-gg@gmail.com-0-105 --%>
        Employee: <input type="text" name="employee">
            <input type="submit" value="submit" />
    </form>
    <!--
        1.为什么使用form标签？
        可以更快速的开发出表单页面，而且可以更方便的进行表单值的回显
        2.注意：
        可以通过modelAttribute属性指定绑定的模型属性，
        若没有指定该属性，则默认从request域对象中读取command的表单bean，
        如果该属性值也不存在，则会发生错误。
    -->
    <form:form action="${pageContext.request.contextPath}/emp" method="post" modelAttribute="employee">

        <form:errors path="*"/>
        <br/>

        <c:if test="${employee.id == null } ">
            <!-- path 属性对应html 表单标签的name属性值 -->
            LastName: <form:input path="lastName"/>
            <form:errors path="lastName"/>
        </c:if>
        <c:if test="${employee.id != null}">
            <form:hidden path="id"/>
            <%--
                对于_method不能使用 form:hidden 标签，因为modelAttribute对应的bean中没有_method这个属性
                <form:hidden path="_method" value="PUT"/>
            --%>
            <input type="hidden" name="_method" value="PUT">
        </c:if>
        <br/>
        Email: <form:input path="email"/>
        <form:errors path="email"/>
        <br/>
        <%
            Map<String,String> genders = new HashMap<>();
            genders.put("1","Male");
            genders.put("0","Female");
            request.setAttribute("genders",genders);
        %>
        Gender: <form:radiobuttons path="gender" items="${genders}"/>
        <br/>
        Department: <form:select path="department.id" items="${departments}" itemLabel="departmentName" itemValue="id"/>
        <br/>
        <!--
            1. 数据类型转换(String -> Date)
            2. 数据类型格式化
            3. 数据校验
            1). 如何校验? 注解?
            ①. 使用JSR 303验证标准
            ②. 加入hibernate validator 验证框架的jar包
            ③. 在SpringMVC 配置文件中添加<mvc:annotation-driven />
            ④. 需要在bean的属性上添加对应的注解
            ⑤. 在目标方法bean类型的前面添加@Valid注解
            2). 验证出错转向到哪一个页面?
            注意：需校验的Bean 对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其它的入参
            3). 错误消息? 如何显示，如何把错误信息进行国际化
        -->
       <%-- Birth: <form:input path="birth"/>--%>
        <form:errors path="birth"/>
        <br/>
        Salary: <form:input path="salary"/>
        <br/>
        <input type="submit" value="submit" />
    </form:form>


</body>
</html>
