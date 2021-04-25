<%--
  Created by IntelliJ IDEA.
  User: Qh
  Date: 2021/4/24
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"   %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <a href="helloworld"> Hello World</a> <br/>
  <a href="springmvc/testRequestMapping"> Test RequestMapping</a> <br/>

  <%--<a href="testMethod"> Test testMethod</a>--%>
  <form action="springmvc/testMethod" method="post">
    <input type="submit" value="提交">
  </form>  <br/>

  <a href="springmvc/testParamsAndHeaders?username=qinh&age=11"> test ParamsAndHeaders</a> <br/>

  <a href="springmvc/testAntPath/xxxaa/abc"> test AntPath</a> <br/>

  <a href="springmvc/testPathVariable/1"> test PathVariable</a> <br/>

  <%-- get请求 --%>
  <a href="springmvc/testRest/1"> test Rest Get</a> <br/>

  <form action="springmvc/testRest" method="post">
    <input type="submit" value="TestRest POST">
  </form> <br/>

  <form action="springmvc/testRest/1" method="post">
    <input type="hidden" name="_method" value="DELETE">
    <input type="submit" value="TestRest DELETE">
  </form> <br/>

  <form action="springmvc/testRest/1" method="post">
    <input type="hidden" name="_method" value="PUT">
    <input type="submit" value="TestRest PUT">
  </form> <br/>


  <a href="springmvc/testRequestParam?username=qinhao&age=11"> test RequestParam</a> <br/>

  <a href="springmvc/testRequestHeader"> test RequestHeader</a> <br/>

  <a href="springmvc/testCookieValue"> test CookieValue</a> <br/>

  <form action="springmvc/testPojo" method="post">
    username: <input type="text" name="username"> <br/>
    password: <input type="password" name="password"> <br/>
    email: <input type="text" name="email"> <br/>
    age: <input type="text" name="age"> <br/>
    city: <input type="text" name="address.city"> <br/>
    province: <input type="text" name="address.province"> <br/>
    <input type="submit" value="submit">
  </form> <br/>

  <a href="springmvc/testServletAPI"> test ServletAPI</a> <br/>

  <a href="springmvc/testModelAndView"> test ModelAndView</a> <br/>

  <a href="springmvc/testMap"> test Map</a> <br/>

  <a href="springmvc/testSessionAttributes"> test SessionAttributes</a> <br/>


  <%--
    模拟修改操作
    1. 原始数据为：1,Tom,123456,xx@gmail.com,12
    2. 密码不能被修改
    3. 表单回显，模拟操作直接在表单填写对应的属性值
  --%>

  <form action="springmvc/testModelAttribute" method="post">
    <input type="hidden" name="id" value="1">
    username: <input type="text" name="username" value="Tom"> <br/>
    email: <input type="text" name="email" value="xx@gmail.com"> <br/>
    age: <input type="text" name="age" value="12"> <br/>
    <input type="submit" value="submit">
  </form> <br/>


  <a href="springmvc/testViewAndViewResolver"> test ViewAndViewResolver</a> <br/>

  <a href="springmvc/testView"> test  View</a> <br/>

  <a href="springmvc/testRedirect"> test  Redirect</a> <br/>

  <a href="springmvc/testForward"> test  Forward</a> <br/>


  </body>
</html>
