package com.keb.sercurity1.controller;

import com.keb.sercurity1.Model.User;
import com.keb.sercurity1.Repository.UserRepository;
import com.keb.sercurity1.config.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 리턴
public class IndexController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication,
                                          @AuthenticationPrincipal PrincipalDetails userDetails){ //DI 디펜던시 인젝션
        //PrincipalDetails 는 UserDetails를 impelement햇으므로 UserDetails userDetails를 프린시펄디테일스로 받아도 댐
        System.out.println("/test/origin =====================");
        PrincipalDetails principalDetails=(PrincipalDetails) authentication.getPrincipal();
        System.out.println("PrincipalDetails에서 권한 뽑기 :" + userDetails.getAuthorities());
        System.out.println("authentication: "+principalDetails.getUser());
        System.out.println("userDetails: "+userDetails.getUsername());
        System.out.println("userDetails: "+userDetails.getUser()); //getUsername() , getUser() 둘 다 가능해짐
        return "세션 정보 확인하기";
    }
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,
                                          @AuthenticationPrincipal PrincipalDetails userDetails){ //DI 디펜던시 인젝션
        //PrincipalDetails 는 UserDetails를 impelement햇으므로 UserDetails userDetails를 프린시펄디테일스로 받아도 댐
        System.out.println("/test/oauth/origin =====================");

        OAuth2User oauth2User=(OAuth2User) authentication.getPrincipal();
       /* System.out.println("authentication: "+oauth2User.getUser());
        System.out.println("userDetails: "+oauth2User.getUsername());
        System.out.println("userDetails: "+oauth2User.getUser()); //getUsername() , getUser() 둘 다 가능해짐
    */
        System.out.println("authentication: "+oauth2User.getAttributes());
        return "oauth세션 정보 확인하기";
    }
    @GetMapping({"", "/"})
    public String index(){
        //머스태치!!권장!! 기본폴더 src/main/resources/
        //뷰리졸버를 설정할 때 templates(prefix), .mustache(suffix)
        return "index"; //src/main/resources/templates/index.mustache
    }

    //일반 로그인이든, oauth 로그인이든 ok
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails: "+principalDetails.getUser());
        System.out.println("principalDetails2: "+principalDetails.getUsername());
        return "user";
    }


    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @PostMapping("/join")
    public  String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String raw=user.getPassword();
        String encPassword=bCryptPasswordEncoder.encode(raw);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }
    //config 작성 후 시큐리티가 낚아채지 않는다
    @GetMapping("/loginForm")
    public  String loginForm(){
        return "loginForm";
    }
    @GetMapping("/login")
    public @ResponseBody String login(){
        return "login";
    }

    @GetMapping("/joinForm")
    public  String joinForm(){
        return "joinForm";
    }

    @Secured("ROLE_ADMIN") //특정 메서드에 롤로 제한 걸고 싶으면 간단하게!!
    //여기는 이제 어드민 계정만 접속 가능함
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    //매니저와 어드민 권한으로만 들어갈 수 있게 걸었음
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }

}
