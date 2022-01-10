package com.qinh.test;

import com.qinh.crud.dao.EmployeeDao;
import com.qinh.crud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-04-27-22:35
 */
@Controller
public class SpringMVCTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;


    @RequestMapping("/testSimpleMappingExceptionResolver")
    public String testSimpleMappingExceptionResolver(@RequestParam("i") int i){
        String[] vals = new String[10];
        System.out.println(vals[i]);
        return "success";
    }

    @RequestMapping(value="/testDefaultHandlerExceptionResolver",method= RequestMethod.POST)
    public String testDefaultHandlerExceptionResolver(){
        System.out.println("testDefaultHandlerExceptionResolver...");
        return "success";
    }

    //@ResponseStatus(reason="测试",value=HttpStatus.NOT_FOUND)
    @RequestMapping("/testResponseStatusExceptionResolver")
    public String testResponseStatusExceptionResolver(@RequestParam("i") int i){
        if(i == 13){
            throw new UserNameNotMatchPasswordException();
        }
        System.out.println("testResponseStatusExceptionResolver...");

        return "success";
    }

	/*@ExceptionHandler({RuntimeException.class})
    public ModelAndView handleArithmeticException2(Exception ex){
        System.out.println("[出异常了]: " + ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        return mv;
    }*/

    /**
     * 1. 在 @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
     * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
     * 3. @ExceptionHandler 方法标记的异常有优先级的问题.
     * 4. @ControllerAdvice: 如果在当前 Handler 中找不到 @ExceptionHandler 方法来解决当前方法出现的异常,
     * 则将去 @ControllerAdvice 标记的类中查找 @ExceptionHandler 标记的方法来处理异常.
     */
	/*@ExceptionHandler({ArithmeticException.class})
    public ModelAndView handleArithmeticException(Exception ex){
        System.out.println("出异常了: " + ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        return mv;
    }*/

    @RequestMapping("/testExceptionHandlerExceptionResolver")
    public String testExceptionHandlerExceptionResolver(@RequestParam("i") int i){
        System.out.println("result: " + (10 / i));
        return "success";
    }


    @RequestMapping("/testFileUpload")
    public String testFileUpload(@RequestParam("desc") String desc, @RequestParam("file")MultipartFile file) throws IOException {
        System.out.println("desc : " + desc);
        System.out.println("OriginalFileName: " + file.getOriginalFilename());
        System.out.println("InputStream : " + file.getInputStream());
        return "success";
    }

    @RequestMapping("/i18n")
    public String testI18n(Locale locale,HttpSession session){
        System.out.println(session);
        String message = resourceBundleMessageSource.getMessage("i18n.user", null, locale);
        System.out.println(message);
        return "i18n";
    }


    /**
     * 返回ResponseEntity对象，模拟文件下载的效果
     *
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/testResponseEntity")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        ServletContext servletContext = session.getServletContext();
        InputStream in = servletContext.getResourceAsStream("/file/gc.log");

        byte[] body = new byte[in.available()];
        in.read(body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=gc.log");

        HttpStatus statusCode = HttpStatus.OK;

        ResponseEntity<byte[]> reponse = new ResponseEntity<>(body,headers,statusCode);

        return reponse;
    }

    /**
     * 请求报文 -> HttpInputMessage -> HttpMessageConverter -> java对象
     * java对象 -> HttpMessageConverter -> HttpOutputMessage -> 响应报文
     *
     * @RequestBody：修饰目标方法入参
     *
     * @param body
     * @return
     */
    @ResponseBody
    @RequestMapping("/testHttpMessageConverter")
    public String testHttpMessageConverter(@RequestBody String body){
        System.out.println(body);
        return "helloworld!" + LocalDateTime.now();
    }

    @RequestMapping("/testJson")
    @ResponseBody
    public Collection<Employee> testJson(){
        return employeeDao.getAll();
    }

    @RequestMapping("/testConversionServiceConverter")
    public String testConverter(@RequestParam("employee")Employee employee){
        System.out.println(" save employee : " + employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }
}
