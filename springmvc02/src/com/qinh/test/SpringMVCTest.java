package com.qinh.test;

import com.qinh.crud.dao.EmployeeDao;
import com.qinh.crud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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


    @RequestMapping("/testFileUpload")
    public String testFileUpload(@RequestParam("desc") String desc, @RequestParam("file")MultipartFile file) throws IOException {
        System.out.println("desc : " + desc);
        System.out.println("OriginalFileName: " + file.getOriginalFilename());
        System.out.println("InputStream : " + file.getInputStream());
        return "success";
    }

    @RequestMapping("/i18n")
    public String testI18n(Locale locale){
        String message = resourceBundleMessageSource.getMessage("i18n.user", null, locale);
        System.out.println(message);
        return "i18n";
    }


    @RequestMapping("/testResponseEntity")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        ServletContext servletContext = session.getServletContext();
        InputStream in = servletContext.getResourceAsStream("/file/连接池配置.txt");

        byte[] body = new byte[in.available()];
        in.read(body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=连接池配置.txt");

        HttpStatus statusCode = HttpStatus.OK;

        ResponseEntity<byte[]> reponse = new ResponseEntity<>(body,headers,statusCode);

        return reponse;
    }

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
