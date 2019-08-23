package com.xhy.xhyappserver.nativefilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 滚犊子过滤器
 */
public class GdzFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter已启动");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        System.out.println(requestURI);
        if(requestURI.endsWith(".php")){
            HttpServletResponse response= (HttpServletResponse)servletResponse;
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("滚犊子,去你大爷！");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }



    @Override
    public void destroy() {

    }
}
