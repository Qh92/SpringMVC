package com.qinh.handler;

import com.qinh.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-04-24-14:13
 */
//@SessionAttributes(value = {"user"},types = {String.class})
@Controller
@RequestMapping("/springmvc")
public class SpringMVCTest {

    private static final String SUCCESS = "success";

    /**
     * 1. @RequestMapping除了修饰方法，还可以来修饰类
     * 2.
     * 1). 类定义处 : 提供初步的请求映射信息。相对于WEB应用的根目录
     * 2). 方法处 : 提供进一步的细分映射信息
     * 相对于类定义处的URL。若类定义处未标注@RequestMapping，则方法处标记的URL相对于WEB应用的根目录
     *
     * @return
     */
    @RequestMapping("/testRequestMapping")
    public String testRequestMapping(){
        System.out.println("testRequestMapping");
        return SUCCESS;
    }

    /**
     * 使用method属性来指定请求方式
     *
     * @return
     */
    @RequestMapping(value = "/testMethod",method = RequestMethod.POST)
    public String testMethod(){
        System.out.println("testMethod");
        return SUCCESS;
    }

    /**
     * 了解：可以使用params 和 headers 来更加精确的映射请求。params和headers支持简单的表达式
     *
     * @return
     */
    @RequestMapping(value = "/testParamsAndHeaders" ,params = {"username","age != 10"},headers = {"Accept-Language=en-US,zh;q=0.9,en;q=0.8"})
    public String testParamsAndHeaders(){
        System.out.println("testParamsAndHeaders");
        return SUCCESS;
    }

    @RequestMapping("/testAntPath/*/abc")
    public String testAntPath(){
        System.out.println("testAntPath");
        return SUCCESS;
    }

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
    @RequestMapping(value = "/testRest/id/{id}/name/{name}" ,method = RequestMethod.GET)
    public String testRest(@PathVariable Integer id,@PathVariable("name") String userName){
        System.out.println("testRest GET : id, " + id + " name, " + userName );
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
        return "redirect:/springmvc/testRest/id/1/name/qinh";
        //return SUCCESS;
    }

    @RequestMapping(value = "/testRest/{id}" ,method = RequestMethod.PUT)
    public String testRestPut(@PathVariable Integer id){
        System.out.println("testRest PUT : " + id);
        return "redirect:/springmvc/testRest/id/1/name/qinh";
    }


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

    /**
     * 目标方法可以添加Map类型(实际上也可以是Model类型或ModelMap类型)的参数
     *
     * @param map
     * @return
     */
    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){
        //org.springframework.validation.support.BindingAwareModelMap
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("Tom","Jerry","Mike"));
        return SUCCESS;
    }


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
     * 1). 若目标方法的 POJO 类型的参数木有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
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



    @RequestMapping("/testViewAndViewResolver")
    public String testViewAndViewResolver(){
        System.out.println("testViewAndViewResolver");
        return SUCCESS;
    }


    /**
     * 我们知道如果return 语句中这样写return "list"; 表示进入视图解析器添加前缀和后缀找到对应的文件 
     *
     * 最后变成/WEB-INF/views/list.jsp
     *
     * 但是如果return 中是请求转发的方式呢？
     *
     * 例如return "redirect:/list";
     *
     * return中使用redirect方式或者forward 方式(return "forward:/list";) 都不会进入视图解析器中解析，而是通过请求转发的方式进行传输。
     * 而这样情况又分两种①return "redirect:/list"; ②return "redirect:/list.jsp";
     *
     * 第一种方式，会重定向controller中@RequestMapping("/list")对应的处理器进行处理
     *
     * 第二种方式，则会进入webapp/根目录下面去寻找一个list.jsp的文件并响应
     *
     * forward方式同理
     *
     *
     * @return
     */
    @RequestMapping("/testView")
    public String testView(){
        System.out.println("testView");
        return "helloView";
    }

    @RequestMapping("/testRedirect")
    public String testRedirect(){
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }

    @RequestMapping("/testForward")
    public String testForward(){
        System.out.println("testForward");
        return "forward:/index.jsp";
    }


}
