# SpringMVC



## 一、SpringMVC 概述

Spring 为展现层提供的基于 MVC 设计理念的优秀的 Web 框架，是目前最主流的 MVC 框架之一 

• Spring3.0 后全面超越 Struts2，成为最优秀的 MVC 框架 

• Spring MVC 通过一套 MVC 注解，让 POJO 成为处理请求的控制器，而无须实现任何接口。 

• 支持 REST 风格的 URL 请求 

• 采用了松散耦合可插拔组件结构，比其他 MVC 框架更具扩展性和灵活性



## 二、SpringMVC 的 HelloWorld

### 步骤：

```
– 加入 jar 包 
– 在 web.xml 中配置 DispatcherServlet 
– 加入 Spring MVC 的配置文件 
– 编写处理请求的处理器，并标识为处理器 
– 编写视图
```



#### 1.jar 包： 

```
– commons-logging-1.1.3.jar 
– spring-aop-4.0.0.RELEASE.jar 
– spring-beans-4.0.0.RELEASE.jar 
– spring-context-4.0.0.RELEASE.jar 
– spring-core-4.0.0.RELEASE.jar 
– spring-expression-4.0.0.RELEASE.jar 
– spring-web-4.0.0.RELEASE.jar 
– spring-webmvc-4.0.0.RELEASE.jar
```



#### 2.在 web.xml 中配置 DispatcherServlet

配置 DispatcherServlet ：DispatcherServlet 默认加载 /WEB-INF/<servletName-servlet>.xml 的 Spring 配置文件, 启动 WEB 层 的 Spring 容器。可以通过 contextConfigLocation 初始化参数自定义配置文件的位置和名称

```xml
<!-- 配置DispatcherServlet -->
    <servlet>
        <servlet-name>springDispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 配置DispatcherServlet的一个初始化参数，配置SpringMVC配置文件的位置和名称 -->
        <!--
            实际上也可以不通过contextConfigLocation 来配置SpringMVC的配置文件，而使用默认的配置文件
            默认的配置文件为：/WEB-INF/<servlet-name>-servlet.xml
        -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springDispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

配置路径时 /和/*的区别

```
其中/和/*的区别：
< url-pattern > / </ url-pattern >   不会匹配到*.jsp，即：*.jsp不会进入spring的 DispatcherServlet类 。
< url-pattern > /* </ url-pattern > 会匹配*.jsp，会出现返回jsp视图时再次进入spring的DispatcherServlet 类，导致找不到对应的controller所以报404错。

总之，关于web.xml的url映射的小知识:
< url-pattern>/</url-pattern>  会匹配到/login这样的路径型url，不会匹配到模式为*.jsp这样的后缀型url
< url-pattern>/*</url-pattern> 会匹配所有url：路径型的和后缀型的url(包括/login,*.jsp,*.js和*.html等)

1. 首先/这个是表示默认的路径，及表示：当没有找到可以匹配的URL就用这个URL去匹配。
2. 在springmvc中可以配置多个DispatcherServlet，比如： 配置多个DispatcherServlet有/和/*，先匹配的是/*这个

3. 当配置相同的情况下，DispathcherServlet配置成/和/*的区别
<一>　/：使用/配置路径，直接访问到jsp，不经springDispatcherServlet
<二>　/*：配置/*路径，不能访问到多视图的jsp
当我在客户端调用URL：/user/list然后返回user.jsp视图，当配置的是/：DispathcherServlet拿到这个请求然后返回对应的controller，然后依据Dispather Type为Forward类型转发到user.jsp视图，即就是请求user.jsp视图(/user/user.jsp)，此时Dispather没有拦截/user/user.jsp，
因为此时你配置的是默认的/，就顺利的交给ModleAndView去处理显示了。
当配置的是/*：DispathcherServlet拿到这个请求然后返回对应的controller，然后通过Dispather Type通过Forward转发到user.jsp视图，即就是请求user.jsp视图(/user/user.jsp)，此时Dispather已经拦截/user/user.jsp，Dispatcher会把他当作Controller去匹配，没有匹配到就会报404错误。

结论：/ 能匹配路径型URL，不能匹配后缀型URL /* 能匹配任何类型URL，在配置视图的时候尽量用/这种方式。
```



#### 3.创建Spring MVC 的配置文件 

```xml
	<!-- 配置扫描的包 -->
    <context:component-scan base-package="com.qinh"/>

    <!-- 配置视图解析器 ：如何把handler方法返回值解析为实际的物理视图 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```



#### 4.编写处理请求的处理器，并标识为处理器 

```java
	/**
     * 1.使用@RequestMapping注解来映射请求的URL
     * 2.返回值会通过视图解析器解析为实际的物理视图，对于 InternalResourceViewResolver视图解析器，会做如下的解析：
     * 通过 prefix + returnVal + 后缀这样的方式得到实际的物理实物图，然后做转发操作
     * /WEB-INF/views/success.jsp
     * @return
     */
    @RequestMapping("/helloworld")
    public String hello(){
        System.out.println("hello world");
        return "success";
    }
```



#### 5.编写视图

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Success  <br/>
</body>
</html>

```



## 三、注解、参数说明

### 1.使用 @RequestMapping 映射请求

Spring MVC 使用 @RequestMapping 注解为控制器指定可 以处理哪些 URL 请求 

• 在控制器的类定义及方法定义处都可标注 @RequestMapping 

– 类定义处：提供初步的请求映射信息。相对于 WEB 应用的根目录 

– 方法处：提供进一步的细分映射信息。相对于类定义处的 URL。若类定义处未标注 @RequestMapping，则方法处标记的 URL 相对于 WEB 应用的根目录 

• DispatcherServlet 截获请求后，就通过控制器上 @RequestMapping 提供的映射信息确定请求所对应的处理方法

![1619486929942](assets\1619486929942.png)

  

### 2.@RequestMapping 还支持 Ant 风格的 URL： 

Ant 风格资源地址支持 3 种匹配符： 

– ?：匹配文件名中的一个字符 

– *：匹配文件名中的任意字符 

– **： 匹配多层路径 

– /user/*/createUser: 匹配 

/user/aaa/createUser、/user/bbb/createUser 等 URL 

– /user/**/createUser: 匹配 

/user/createUser、/user/aaa/bbb/createUser 等 URL 

– /user/createUser??: 匹配 

/user/createUseraa、/user/createUserbb 等 URL

```html

```



```java

```



### 3.HTTP请求报文

![1619487061419](assets\1619487061419.png)



• @RequestMapping 除了可以使用请求 URL 映射请求外， 还可以使用请求方法、请求参数及请求头映射请求 

• @RequestMapping 的 value、method、params 及 heads  分别表示请求 URL、请求方法、请求参数及请求头的映射条 

件，他们之间是与的关系，联合使用多个条件可让请求映射更加精确化。 

### 4.params 和 headers支持简单的表达式： 

– param1: 表示请求必须包含名为 param1 的请求参数 

– !param1: 表示请求不能包含名为 param1 的请求参数 

– param1 != value1: 表示请求包含名为 param1 的请求参数，但其值不能为 value1 

– {“param1=value1”, “param2”}: 请求必须包含名为 param1 和param2  的两个请求参数，且 param1 参数的值必须为 value1



```java
	/**
     * 了解：可以使用parmas 和 headers 来更加精确的映射请求。params和headers支持简单的表达式
     *
     * @return
     */
    @RequestMapping(value = "/testParamsAndHeaders" ,params = {"username","age != 10"},headers = {"Accept-Language=en-US,zh;q=0.9,en;q=0.8"})
    public String testParamsAndHeaders(){
        System.out.println("testParamsAndHeaders");
        return SUCCESS;
    }
```



### 5.@PathVariable 映射 URL 绑定的占位符

• 带占位符的 URL 是 Spring3.0 新增的功能，该功能在 SpringMVC 向 REST 目标挺进发展过程中具有里程碑的意义 

• 通过 @PathVariable 可以将 URL 中占位符参数绑定到控 制器处理方法的入参中：URL 中的 {xxx} 占位符可以通过 

@PathVariable("xxx") 绑定到操作方法的入参中

```java
	/**
     * @PathVariable 可以来映射URL中的占位符到目标方法的参数中
     *
     * @param id
     * @return
     */
    @RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable("id") String id){
        System.out.println("testPathVariable : " + id);
        return SUCCESS;
    }
```



### 6.REST 

REST：即 Representational State Transfer。（资源）表现层状态转化。是目前最流行的一种互联网软件架构。它结构清晰、符合标准、易于理解、扩展方便， 所以正得到越来越多网站的采用

资源（Resources）：网络上的一个实体，或者说是网络上的一个具体信息。它 可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的存在。 可以用一个URI（统一资源定位符）指向它，每种资源对应一个特定的 URI 。要 获取这个资源，访问它的URI就可以，因此 URI 即为每一个资源的独一无二的识别符。 

表现层（Representation）：把资源具体呈现出来的形式，叫做它的表现层 （Representation）。比如，文本可以用 txt 格式表现，也可以用 HTML 格 式、XML 格式、JSON 格式表现，甚至可以采用二进制格式。 

状态转化（State Transfer）：每发出一个请求，就代表了客户端和服务器的一 次交互过程。HTTP协议，是一个无状态协议，即所有的状态都保存在服务器 端。因此，如果客户端想要操作服务器，必须通过某种手段，让服务器端发生“ 状态转化”（State Transfer）。而这种转化是建立在表现层之上的，所以就是 “ 表现层状态转化”。具体说，就是 HTTP 协议里面，四个表示操作方式的动 词：GET、POST、PUT、DELETE。它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源。



示例： 

– /order/1 HTTP GET ：得到 id = 1 的 order  

– /order/1 HTTP DELETE：删除 id = 1的 order  

– /order/1 HTTP PUT：更新id = 1的 order  

– /order HTTP POST：新增 order 

• HiddenHttpMethodFilter：浏览器 form 表单只支持 GET  与 POST 请求，而DELETE、PUT 等 method 并不支持，Spring3.0 添加了一个过滤器，可以将这些请求转换为标准的 http 方法，使得支持 GET、POST、PUT 与 DELETE 请求

```html
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
```



```java
	/**
     * Rest风格的URL
     * 以CRUD为例：
     * 新增：/order  POST
     * 修改：/order/1 PUT       update?id=1
     * 获取：/order/1 GET       get?id=1
     * 删除：/order/1 DELETE    delete?id=1
     *
     * 如何发送PUT请求和DELETE请求呢？
     * 1. 需要配置HiddenHttpMethodFilter
     * 2. 需要发送POST
     * 3. 需要在发送POST请求时携带一个name="_method"的隐藏域，值为DELETE或PUT
     *
     * 在SpringMVC的目标方法中如何得到id呢？
     * 使用@PathVariable注解
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/testRest/{id}" ,method = RequestMethod.GET)
    public String testRest(@PathVariable Integer id){
        System.out.println("testRest GET : " + id);
        return SUCCESS;
    }


    @RequestMapping(value = "/testRest" ,method = RequestMethod.POST)
    public String testRest(){
        System.out.println("testRest POST ");
        return SUCCESS;
    }

    @RequestMapping(value = "/testRest/{id}" ,method = RequestMethod.DELETE)
    public String testRestDelete(@PathVariable Integer id){
        System.out.println("testRest DELETE : " + id);
        return "redirect:/springmvc/testRest/1";
    }

    @RequestMapping(value = "/testRest/{id}" ,method = RequestMethod.PUT)
    public String testRestPut(@PathVariable Integer id){
        System.out.println("testRest PUT : " + id);
        return "redirect:/springmvc/testRest/1";
    }
```



## 四、映射请求参数 & 请求参数

### 1.请求处理方法签名 

• Spring MVC 通过分析处理方法的签名，将 HTTP 请求信 息绑定到处理方法的相应入参中。 

• Spring MVC 对控制器处理方法签名的限制是很宽松的， 几乎可以按喜欢的任何方式对方法进行签名。 

• 必要时可以对方法及方法入参标注相应的注解（ @PathVariable 、@RequestParam、@RequestHeader 等）、Spring  

MVC 框架会将 HTTP 请求的信息绑定到相应的方法入参 中，并根据方法的返回值类型做出相应的后续处理



### 2.使用 @RequestParam 绑定请求参数值 

• 在处理方法入参处使用 @RequestParam 可以把请求参数传递给请求方法 

– value：参数名 

– required：是否必须。默认为 true, 表示请求参数中必须包含对应的参数，若不存在，将抛出异常

```jsp
<a href="springmvc/testRequestParam?username=qinhao&age=11"> test RequestParam</a> <br/>
```



```java
	/**
     * @RequestParam 来映射请求参数
     * value 值即请求参数的参数名
     * required 该参数是否必须，默认为true
     * defaultValue 请求参数的默认值
     *
     * @param username
     * @param age
     * @return
     */
    @RequestMapping(value = "/testRequestParam")
    public String testRequestParam(@RequestParam(value = "username") String username,@RequestParam(value = "age",required = false,defaultValue = "0") Integer age){
        System.out.println("testRequestParam,username : " + username + " , age : " + age);
        return SUCCESS;
    }
```



### 3.使用 @RequestHeader 绑定请求报头的属性值 

• 请求头包含了若干个属性，服务器可据此获知客户端的信 息，通过 @RequestHeader 即可将请求头中的属性值绑 

定到处理方法的入参中

```java
	/**
     * 映射请求头信息
     * 用法同@RequestParam
     *
     * @param al
     * @return
     */
    @RequestMapping("/testRequestHeader")
    public String testRequestHeader(@RequestHeader(value = "accept-language") String al){
        System.out.println("testRequestHeader : " + al);
        return SUCCESS;
    }
```



### 4.使用 @CookieValue 绑定请求中的 Cookie 值 

• @CookieValue 可让处理方法入参绑定某个 Cookie 值

```java
	/**
     * @CookieValue: 映射一个Cookie值，属性同@RequestParam
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID") String sessionId){
        System.out.println("testCookieValue : " + sessionId);
        return SUCCESS;
    }
```



### 5.使用 POJO 对象绑定请求参数值 

• Spring MVC 会按请求参数名和 POJO 属性名进行自动匹 配，自动为该对象填充属性值。支持级联属性。 如：dept.deptId、dept.address.tel 等

```html
 <form action="springmvc/testPojo" method="post">
    username: <input type="text" name="username"> <br/>
    password: <input type="password" name="password"> <br/>
    email: <input type="text" name="email"> <br/>
    age: <input type="text" name="age"> <br/>
    city: <input type="text" name="address.city"> <br/>
    province: <input type="text" name="address.province"> <br/>
    <input type="submit" value="submit">
  </form> <br/>
```



```java
	/**
     * SpringMVC 会按请求参数名和POJO属性名进行自动匹配
     * 自动为该对象填充属性值。支持级联属性。如：dept.deptId,dept.address.tel等
     *
     * @param user
     * @return
     */
    @RequestMapping("/testPojo")
    public String testPojo(User user){
        System.out.println("testPojo : " + user);
        return SUCCESS;
    }
```



```java
public class User {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private Integer age;
    private Address address;

}
```



```java
public class Address {

    private String province;
    private String city;

}
```



### 6.使用 Servlet API 作为入参

```java
	/**
     * 可以使用Servlet原生的API作为目标方法的参数，具体支持以下类型：
     *
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * Locale
     * InputStream
     * OutputStream
     * Reader
     * Writer
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/testServletAPI")
    public void testServletAPI(HttpServletRequest request, HttpServletResponse response, Writer writer) throws IOException {
        System.out.println("testServletAPI , " + request + " , " + response);
        writer.write("hello springmvc!!!!");
        //return SUCCESS;
    }
```



## 五、处理模型数据 

• Spring MVC 提供了以下几种途径输出模型数据： 

– ModelAndView: 处理方法返回值类型为 ModelAndView 时, 方法体即可通过该对象添加模型数据 

– Map 及 Model: 入参为 org.springframework.ui.Model、org.springframework.ui. ModelMap 或 java.uti.Map 时，处理方法返回时，Map中的数据会自动添加到模型中。 

– @SessionAttributes: 将模型中的某个属性暂存到 HttpSession 中，以便多个请求之间可以共享这个属性 

– @ModelAttribute: 方法入参标注该注解后, 入参的对象 就会放到数据模型中



### 1.ModelAndView 

• 控制器处理方法的返回值如果为 ModelAndView, 则其既 包含视图信息，也包含模型数据信息。 

• 添加模型数据: 

– MoelAndView addObject(String attributeName, Object  attributeValue) 

– ModelAndView addAllObject(Map<String, ?> modelMap) 

• 设置视图: 

– void setView(View view) 

– void setViewName(String viewName)

```java
	/**
     * 目标方法的返回值可以时ModelAndView类型。其中可以包含视图和模型信息
     * SpringMVC会把ModelAndView的model中数据放入到request域对象中
     *
     * @return
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        String viewName = SUCCESS;
        ModelAndView modelAndView = new ModelAndView(viewName);

        //添加模型数据到ModelAndView中
        modelAndView.addObject("time",new Date());

        return modelAndView;
    }
```



### 2.Map 及 Model 

• Spring MVC 在内部使用了一个 org.springframework.ui.Model 接口存储模型数据 

• 具体步骤 

– Spring MVC 在调用方法前会创建一个隐含的模型对象作为模型数据的存储容器。 

– 如果方法的入参为 Map 或 Model 类 型，Spring MVC 会将隐含模型的引用传递给这些入参。在方法体内，开发者可以 通过这个入参对象访问到模型中的所有数据，也可以向模型中添加新的属性数据

![1619491837657](assets\1619491837657.png)



```java
 	/**
     * 目标方法可以添加Map类型(实际上也可以是Model类型或ModelMap类型)的参数
     *
     * @param map
     * @return
     */
    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("Tom","Jerry","Mike"));
        return SUCCESS;
    }
```



```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Success  <br/>
    time : ${requestScope.time} <br/> 
    names : ${requestScope.names} <br/> //"Tom","Jerry","Mike"
</body>
</html>
```



### 3.@SessionAttributes 

• 若希望在多个请求之间共用某个模型属性数据，则可以在 控制器类上标注一个 @SessionAttributes, Spring MVC  

将在模型中对应的属性暂存到 HttpSession 中。 

• @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外，还可以通过模型属性的对象类型指定哪些 

模型属性需要放到会话中 

– @SessionAttributes(types=User.class) 会将隐含模型中所有类型为 User.class 的属性添加到会话中。 

– @SessionAttributes(value={“user1”, “user2”}) 

– @SessionAttributes(types={User.class, Dept.class}) 

– @SessionAttributes(value={“user1”, “user2”},  types={Dept.class})

```java
@SessionAttributes(value = {"user"},types = {String.class})
@Controller
@RequestMapping("/springmvc")
public class SpringMVCTest {
```



```java
	/**
     * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外（实际上使用的是value属性值）
     * ，还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中（实际上使用的是types属性值）
     *
     * 注意：该注解只能放在类的上面，而不能修饰方法
     *
     * @param map
     * @return
     */
    @RequestMapping("/testSessionAttributes")
    public String testSessionAttributes(Map<String,Object> map){
        User user = new User("Tom", "12222", "xx@gmail.com", 22);
        map.put("user",user);
        //也可以放入到会话中
        map.put("school","beijingqinghua");
        return SUCCESS;
    }
```



```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <fmt:message key="i18n.username"/>  <br/>
    <fmt:message key="i18n.password"/>  <br/>
</body>
</html>
```



### 4.@ModelAttribute 

• 在方法定义上使用 @ModelAttribute 注解：Spring MVC 在调用目标处理方法前，会先逐个调用在方法上标注了 

@ModelAttribute 的方法。 

• 在方法的入参前使用 @ModelAttribute 注解： 

– 可以从隐含对象中获取隐含的模型数据中获取对象，再将请求参数绑定到对象中，再传入入参 

– 将方法入参对象添加到模型中



由@SessionAttributes引发的异常 :org.springframework.web.HttpSessionRequiredException:  Session attribute 'user' required - not found in session 

• 如果在处理类定义处标注了@SessionAttributes(“xxx”)，则尝试从会话中获取该属性，并将其赋给该入参，然后再用 

请求消息填充该入参对象。如果在会话中找不到对应的属性，则抛出 HttpSessionRequiredException 异常



```java
	/**
     * 由@ModelAttribute 标记的方法，会在每个目标方法执行之前被SpringMVC调用！！！
     *
     * 运行流程:
     * 1. 执行@ModelAttribu注解修饰的方法：从数据库中取出对象，把对象放入到Map中，键为：user
     * 2. SpringMVC 从Map中取出User对象，并把表单的请求参数赋给该User对象的对应属性
     * 3. SpringMVC 把上述对象传入目标方法的参数
     *
     * 注意：在@ModelAttribute修饰的方法中，放入到Map时的键需要和目标方法入参类型的第一个字母小写的字符串一致！！
     *
     * SpringMVC 确定目标方法 POJO 类型入参的过程
     * 1. 确定一个 key:
     * 1). 若目标方法的 POJO 类型的参数没有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
     * 2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值.
     * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
     * 1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到.
     * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰,
     * 若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
     * 对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常.
     * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
     * 会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
     * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中.
     *
     * 源码分析的流程：
     * 1. 调用@ModelAttribu注解修饰的方法。实际上把@ModelAttribute方法中Map中的数据放在了implicitModel中
     * 2. 解析请求处理器的目标参数，实际上该目标参数来自于WebDataBinder对象的target属性
     * 1). 创建WebDataBinder对象：
     * ①. 确定objectName属性：若传入的attrName属性值为"",则objectName为类名第一个字母小写。
     * *注意： attrName.若目标方法的POJO属性使用了@ModelAttribu来修饰，则attrName值即为@ModelAttribu的value属性值
     *
     * ②. 确定target属性：
     *  > 在implicitModel中查找attrName对象的属性值。若存在，ok
     *  > *若不存在：则验证当前Handler是否使用了@SessionAttributes进行修饰，若使用了，则尝试从Session中获取attrName所对应的属性值。若session中没有对应的属性值，则抛出异常。
     *  > *若Handler没有使用@SessionAttributes进行修饰，或@SessionAttributes中没有使用value值指定的key和attrName相匹配，则通过反射创建了POJO对象
     *
     * 2). SpringMVC把表单的请求参数赋给了WebDataBinder的target对应的属性
     * 3). *SpringMVC 会把WebDataBinder的attrName和target给到implicitModel，进而传到request域对象中
     * 4). 把WebDataBinder的target作为参数传递给目标方法的入参
     *
     *
     * @param user
     * @return
     */
    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(User user){
        System.out.println("修改： " + user);
        return SUCCESS;
    }


    /**
     * 1. 有 @ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用!
     * 2. @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参, 其 value 属性值有如下的作用:
     * 1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
     * 2). SpringMVC 会一 value 为 key, POJO 类型的对象为 value, 存入到 request 中.
     *
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id" ,required = false) Integer id,Map<String,Object> map){
        System.out.println("ModelAttribute method");
        if (id != null){
            User user = new User(1, "Tom", "123456", "xx@gmail.com", 22);
            System.out.println("从数据库中获取一个对象 : " + user);
            map.put("user",user);
        }
    }
```



## 六、视图和视图解析器

![1619506318807](assets\1619506318807.png)





### 1.视图和视图解析器 

• 请求处理方法执行完成后，最终返回一个 ModelAndView对象。对于那些返回 String，View 或 ModeMap 等类型的 

处理方法，Spring MVC 也会在内部将它们装配成一个ModelAndView 对象，它包含了逻辑名和模型对象的视图 

• Spring MVC 借助视图解析器（ViewResolver）得到最终的视图对象（View），最终的视图可以是 JSP ，也可能是 

Excel、JFreeChart 等各种表现形式的视图 

• 对于最终究竟采取何种视图对象对模型数据进行渲染，处理器并不关心，处理器工作重点聚焦在生产模型数据的工 

作上，从而实现 MVC 的充分解耦

### 



### 2.视图 

• 视图的作用是渲染模型数据，将模型里的数据以某种形式呈现给客 户。 

• 为了实现视图模型和具体实现技术的解耦，Spring 在 org.springframework.web.servlet 包中定义了一个高度抽象的 View 

接口： 

```java
public interface View {
    String RESPONSE_STATUS_ATTRIBUTE = View.class.getName() + ".responseStatus";
    String PATH_VARIABLES = View.class.getName() + ".pathVariables";
    String SELECTED_CONTENT_TYPE = View.class.getName() + ".selectedContentType";

    @Nullable
    default String getContentType() {
        return null;
    }

    void render(@Nullable Map<String, ?> var1, HttpServletRequest var2, HttpServletResponse var3) throws Exception;
}
```

• 视图对象由视图解析器负责实例化。由于视图是无状态的，所以他们不会有线程安全的问题



![1619506904224](assets\1619506904224.png)





### 3.视图解析器 

• SpringMVC 为逻辑视图名的解析提供了不同的策略，可以在 Spring WEB 上下文中配置一种或多种解析策略，并 

指定他们之间的先后顺序。每一种映射策略对应一个具体的视图解析器实现类。 

• 视图解析器的作用比较单一：将逻辑视图解析为一个具体的视图对象。 

• 所有的视图解析器都必须实现 ViewResolver 接口：

```java
public interface ViewResolver {
    @Nullable
    View resolveViewName(String var1, Locale var2) throws Exception;
}
```



![1619507200994](assets\1619507200994.png)



程序员可以选择一种视图解析器或混用多种视图解析器 

• 每个视图解析器都实现了 Ordered 接口并开放出一个 order 属性，可以通过 order 属性指定解析器的优先顺序，order 越小优先级越高。 

• SpringMVC 会按视图解析器顺序的优先顺序对逻辑视图名进行解析，直到解析成功并返回视图对象，否则将抛出 ServletException 异 常

```xml
 <!-- 配置视图 BeanNameViewResolver 解析器: 使用视图的名字来解析视图 -->
    <!-- 通过order属性来定义视图解析器的优先级，order值越小优先级越高 -->
    <bean class="org.springframework.web.servlet.view.BeanNameViewResolver">
        <property name="order" value="100"/>
    </bean>
```



### 4.InternalResourceViewResolver 

• JSP 是最常见的视图技术，可以使用InternalResourceViewResolver 作为视图解析器：

![1619507293980](assets\1619507293980.png)



若项目中使用了 JSTL，则 SpringMVC 会自动把视图由 InternalResourceView 转为 JstlView 

• 若使用 JSTL 的 fmt 标签则需要在 SpringMVC 的配置文件中配置国际化资源文件

```java
<!-- 配置国际化资源文件 -->
    <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basename" value="i18n"/>
    </bean>
```



若希望直接响应通过 SpringMVC 渲染的页面，可以使用 mvc:view-controller 标签实现

```xml
<!-- 配置直接转发的页面 -->
    <!-- 可以直接响应转发的页面，而无需再经过Handler的方法 -->
    <mvc:view-controller path="/success" view-name="success"/>
```



Excel 视图 

• 若希望使用 Excel 展示数据列表，仅需要扩展SpringMVC 提供的 AbstractExcelView 或 AbstractJExcel View 即可。实现 buildExcelDocument()  方法，在方法中使用模型数据对象构建 Excel 文档就可以了。 

• AbstractExcelView 基于 POI API，而AbstractJExcelView 是基于 JExcelAPI 的。 

• 视图对象需要配置 IOC 容器中的一个 Bean，使用BeanNameViewResolver 作为视图解析器即可 

• 若希望直接在浏览器中直接下载 Excel 文档，则可以设置响应头 Content-Disposition 的值attachment;filename=xxx.xls



关于重定向 

• 一般情况下，控制器方法返回字符串类型的值会被当成逻辑视图名处理 

• 如果返回的字符串中带 forward: 或 redirect: 前缀 时，SpringMVC 会对他们进行特殊处理：将 forward: 和 redirect: 当成指示符，其后的字符串作为 URL 来处理 

– redirect:success.jsp：会完成一个到 success.jsp 的重定向的操作 

– forward:success.jsp：会完成一个到 success.jsp 的转发操作



## 七、RESTful CRUD

1.新增：/order  POST

2.删除：/order/1 DELETE    delete?id=1

3.修改：/order/1 PUT       update?id=1

4.查询：/order/1 GET       get?id=1

```
如何发送PUT请求和DELETE请求呢？
1. 需要配置HiddenHttpMethodFilter
2. 需要发送POST
3. 需要在发送POST请求时携带一个name="_method"的隐藏域，值为DELETE或PUT
在SpringMVC的目标方法中如何得到id呢？
使用@PathVariable注解
```

```xml
<!-- 配置org.springframework.web.filter.HiddenHttpMethodFilter：可以把POST请求转为DELETE或PUT请求-->
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

测试代码：

```jsp
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
```



```java
@RequestMapping(value = "/testRest/{id}" ,method = RequestMethod.GET)
public String testRest(@PathVariable Integer id){
    System.out.println("testRest GET : " + id);
    return SUCCESS;
}


@RequestMapping(value = "/testRest" ,method = RequestMethod.POST)
public String testRest(){
    System.out.println("testRest POST ");
    return SUCCESS;
}

@RequestMapping(value = "/testRest/{id}" ,method = RequestMethod.DELETE)
public String testRestDelete(@PathVariable Integer id){
    System.out.println("testRest DELETE : " + id);
    return "redirect:/springmvc/testRest/1";
}

@RequestMapping(value = "/testRest/{id}" ,method = RequestMethod.PUT)
public String testRestPut(@PathVariable Integer id){
    System.out.println("testRest PUT : " + id);
    return "redirect:/springmvc/testRest/1";
}
```



## 八、SpringMVC表单标签&处理静态资源

### SpringMVC表单标签

通过 SpringMVC 的表单标签可以实现将模型数据 中的属性和 HTML表单元素相绑定，以实现表单数据更便捷编辑和表单值的回显

1.form 标签

1)一般情况下，通过 GET 请求获取表单页面，而通过POST 请求提交表单页面，因此获取表单页面和提交表单 页面的 URL 是相同的。只要满足该最佳条件的契 约，<form:form> 标签就无需通过 action 属性指定表单提交的 URL 

2)可以通过 modelAttribute 属性指定绑定的模型属性，若没有指定该属性，则默认从 request 域对象中读取 command 的表单 bean，如果该属性值也不存在，则会发生错误

2.表单标签

SpringMVC 提供了多个表单组件标签，如 <form:input/>、<form:select/> 等，用以绑定表单字段的属性值，它们的共有属性如下： 

– path：表单字段，对应 html 元素的 name 属性，支持级联属性 

– htmlEscape：是否对表单值的 HTML 特殊字符进行转换，默认值为 true 

– cssClass：表单组件对应的 CSS 样式类名 

– cssErrorClass：表单组件的数据存在错误时，采取的 CSS 样式

form:input、form:password、form:hidden、form:textarea ：对应 HTML 表单的 text、password、hidden、textarea 

标签 

form:radiobutton：单选框组件标签，当表单 bean 对应的属性值和 value 值相等时，单选框被选中 

form:radiobuttons：单选框组标签，用于构造多个单选框

– items：可以是一个 List、String[] 或 Map 

– itemValue：指定 radio 的 value 值。可以是集合中 bean 的一个属性值 

– itemLabel：指定 radio 的 label 值 

– delimiter：多个单选框可以通过 delimiter 指定分隔符

form:checkbox：复选框组件。用于构造单个复选框 

form:checkboxs：用于构造多个复选框。使用方式同 

form:radiobuttons 标签 

form:select：用于构造下拉框组件。使用方式同 

form:radiobuttons 标签 

form:option：下拉框选项组件标签。使用方式同 

form:radiobuttons 标签 

form:errors：显示表单组件或数据校验所对应的错误 

– <form:errors path= “ *” /> ：显示表单所有的错误 

– <form:errors path= “ user*” /> ：显示所有以 user 为前缀的属性对应的错误 

– <form:errors path= “ username” /> ：显示特定表单对象属性的错误



### 处理静态资源

优雅的 REST 风格的资源URL 不希望带 .html 或 .do 等后缀 

• 若将 DispatcherServlet 请求映射配置为 /，则 Spring MVC 将捕获WEB 容器的所有请求，包括静态资源的请求， SpringMVC 会将他 们当成一个普通请求处理，因找不到对应处理器将导致错误。 

• 可以在 SpringMVC 的配置文件中配置 <mvc:default-servlet-handler/> 的方式解决静态资源的问题： 

– <mvc:default-servlet-handler/> 将在 SpringMVC 上下文中定义一个DefaultServletHttpRequestHandler，它会对进入 DispatcherServlet 的请求进行筛查，如果发现是没有经过映射的请求，就将该请求交由 WEB 应用服务器默认的 Servlet 处理，如果不是静态资源的请求，才由DispatcherServlet 继续处理 

– 一般 WEB 应用服务器默认的 Servlet 的名称都是 default。若所使用的WEB 服务器的默认 Servlet 名称不是 default，则需要通过 default-servlet-name 属性显式指定

其中/和/*的区别：
< url-pattern > / </ url-pattern >  不会匹配到*.jsp，即：*.jsp不会进入spring的 DispatcherServlet类 。
< url-pattern > /* </ url-pattern > 会匹配*.jsp，会出现返回jsp视图时再次进入spring的DispatcherServlet 类，导致找不到对应的controller所以报404错。

总之，关于web.xml的url映射的小知识:
< url-pattern>/</url-pattern>  会匹配到/login这样的路径型url，不会匹配到模式为*.jsp这样的后缀型url
< url-pattern>/*</url-pattern> 会匹配所有url：路径型的和后缀型的url(包括/login,*.jsp,*.js和*.html等)

```
1. 首先/这个是表示默认的路径，及表示：当没有找到可以匹配的URL就用这个URL去匹配。
2. 在springmvc中可以配置多个DispatcherServlet，比如： 配置多个DispatcherServlet有/和/*，先匹配的是/*这个

3. 当配置相同的情况下，DispathcherServlet配置成/和/*的区别
<一>　/：使用/配置路径，直接访问到jsp，不经springDispatcherServlet
<二>　/*：配置/*路径，不能访问到多视图的jsp
当我在客户端调用URL：/user/list然后返回user.jsp视图，当配置的是/：DispathcherServlet拿到这个请求然后返回对应的controller，
然后依据Dispather Type为Forward类型转发到user.jsp视图，即就是请求user.jsp视图(/user/user.jsp)，此时Dispather没有拦截/user/user.jsp，
因为此时你配置的是默认的/，就顺利的交给ModleAndView去处理显示了。
当配置的是/*：DispathcherServlet拿到这个请求然后返回对应的controller，然后通过Dispather Type通过Forward转发到user.jsp视图，
即就是请求user.jsp视图(/user/user.jsp)，此时Dispather已经拦截/user/user.jsp，Dispatcher会把他当作Controller去匹配，没有匹配到就会报404错误。

结论：/ 能匹配路径型URL，不能匹配后缀型URL /* 能匹配任何类型URL，在配置视图的时候尽量用/这种方式。
```

## 九、数据转换 & 数据格式化 & 数据校验

### 数据绑定流程

1. Spring MVC 主框架将 ServletRequest 对象及目标方法的入参实例传递给 WebDataBinderFactory 实例，以创建 DataBinder 实例对象 

2. DataBinder 调用装配在 Spring MVC 上下文中的ConversionService 组件进行数据类型转换、数据格式化工作。将 Servlet 中的请求信息填充到入参对象中 

3. 调用 Validator 组件对已经绑定了请求消息的入参对象进行数据合法性校验，并最终生成数据绑定结果BindingData 对象 

4. Spring MVC 抽取 BindingResult 中的入参对象和校验错误对象，将它们赋给处理方法的响应入参

5. Spring MVC 通过反射机制对目标处理方法进行解析，将请求消息绑定到处理方法的入参中。数据绑定的核心部件是 

   DataBinder，运行机制如下：

   ![1628845067776](assets\1628845067776.png)

自定义类型转换器：

```xml
<!-- 在实际开发中通常都需要配置 mvc:annotation-driven 标签 -->
<mvc:annotation-driven conversion-service="conversionService"/>

<!-- 配置ConversionService -->
<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <set>
            <ref bean="employeeConverter"/>
        </set>
    </property>
</bean>
```



```jsp
<form action="testConversionServiceConverter" method="post">
    <%-- lastname-email-gender-department.id  例如: GG-gg@gmail.com-0-105 --%>
    Employee: <input type="text" name="employee">
    <input type="submit" value="submit" />
</form>
```



```java
@RequestMapping("/testConversionServiceConverter")
public String testConverter(@RequestParam("employee")Employee employee){
    System.out.println(" save employee : " + employee);
    employeeDao.save(employee);
    return "redirect:/emps";
}

@Component
public class EmployeeConverter implements Converter<String, Employee> {

    @Override
    public Employee convert(String s) {
        if (s != null){
            String[] values = s.split("-");
            if (values != null && values.length == 4){
                String lastName = values[0];
                String email = values[1];
                Integer gender = Integer.parseInt(values[2]);
                Department department = new Department();
                department.setId(Integer.parseInt(values[3]));
                Employee employee = new Employee(null, lastName, email, gender, department);
                System.out.println(s + "--convert--" + employee);
                return employee;
            }
        }
        return null;
    }
}
```



Spring 支持的转换器 

Spring 定义了 3 种类型的转换器接口，实现任意一个转换器接口都可以作为自定义转换器注册到 ConversionServiceFactroyBean 中： 

– Converter<S,T>：将 S 类型对象转为 T 类型对象 

– ConverterFactory：将相同系列多个 “同质” Converter 封装在一起。如果希望将一种类型的对象转换为另一种类型及其子类的对象（例如将 String 转换为 Number 及 Number 子类（Integer、Long、Double 等）对象）可使用该转换器工厂类 

– GenericConverter：会根据源类对象及目标类对象所在的宿主类中的上下文信息进行类型转换



关于 mvc:annotation-driven 

• <mvc:annotation-driven /> 会自动注册RequestMappingHandlerMapping 、RequestMappingHandlerAdapter 与 

ExceptionHandlerExceptionResolver 三个bean。 

• 还将提供以下支持： 

– 支持使用 ConversionService 实例对表单参数进行类型转换 

– 支持使用 @NumberFormat annotation、@DateTimeFormat注解完成数据类型的格式化 

– 支持使用 @Valid 注解对 JavaBean 实例进行 JSR 303 验证 

– 支持使用 @RequestBody 和 @ResponseBody 注解