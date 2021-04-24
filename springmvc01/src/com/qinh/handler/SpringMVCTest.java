package com.qinh.handler;

import com.qinh.entity.User;
import org.springframework.stereotype.Controller;
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
     * 了解：可以使用parmas 和 headers 来更加精确的映射请求。params和headers支持简单的表达式
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
     * @param user
     * @return
     */
    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(User user){
        System.out.println("修改： " + user);
        return SUCCESS;
    }


    /**
     * 运行流程:
     * 1. 执行@ModelAttribu注解修饰的方法：从数据库中取出对象，把对象放入到Map中，键为：user
     * 2. SpringMVC 从Map中取出User对象，并把表单的请求参数赋给该User对象的对应属性
     * 3. SpringMVC 把上述对象传入目标方法的参数
     *
     * 注意：在@ModelAttribute修饰的方法中，放入到Map时的键需要和目标方法入参类型的第一个字母小写的字符串一致！！
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
}
