package com.qinh.view;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-04-25-23:32
 */
@Component
public class HelloView implements View {
    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().print("hello view, time : " + LocalDateTime.now());
    }
}
