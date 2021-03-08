package com.keb.jwt.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class MyFilter3  implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse res=(HttpServletResponse) servletResponse;
        //토큰 : keb 이걸 만들어주기 id, pw가 들어와 정상적으로 로그인이 완료되면 토큰을 만들어주고 그걸 응답해줌
        // 요청할 때마다 header에 Authorization 에 value 값으로 토큰을 가지고 오겠지??
        //그 때 토큰이 넘어오면, 이 토큰이 내가 만든 토큰이 맞는지만 검증만 하면 댐(RSA, HS256으로 토큰 검증!!)
        if(req.getMethod().equals("POST")){
            System.out.println("포스트 요청됨 !!");
            String headerAuth=req.getHeader("Authorization");
            System.out.println(headerAuth);

            if(headerAuth.equals("keb")){
                filterChain.doFilter(req, res);
            }else{
                PrintWriter out=res.getWriter();
                out.println("인증 안댐");

            }
        }
    }
}

