package com.qinh.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-04-24-13:49
 */
@Controller
public class HelloWorld {

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
}
