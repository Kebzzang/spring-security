package com.keb.jwt.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req=(HttpServletRequest) servletRequest;
//        HttpServletResponse res=(HttpServletResponse) servletResponse;
//        //토큰 : keb 면 인증이 되게 하고(필터를 타게 해서) 아니면
        System.out.println("필터 1입니다.");
            filterChain.doFilter(servletRequest, servletResponse);
    }
}
