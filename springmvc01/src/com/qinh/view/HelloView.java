package com.qinh.view;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 自定义视图，必须放进spring容器
 *
 * BeanNameViewResolver#resolveViewName(String viewName, Locale locale)
 * public View resolveViewName(String viewName, Locale locale) throws BeansException {
 * 		ApplicationContext context = obtainApplicationContext();
 * 		if (!context.containsBean(viewName)) {
 * 			// Allow for ViewResolver chaining...
 * 			return null;
 *                }
 * 		if (!context.isTypeMatch(viewName, View.class)) {
 * 			if (logger.isDebugEnabled()) {
 * 				logger.debug("Found bean named '" + viewName + "' but it does not implement View");
 *            }
 * 			// Since we're looking into the general ApplicationContext here,
 * 			// let's accept this as a non-match and allow for chaining as well...
 * 			return null;
 *        }
 * 		return context.getBean(viewName, View.class);*
 * }
 *
 *
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
