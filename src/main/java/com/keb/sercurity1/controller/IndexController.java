package com.keb.sercurity1.controller;

import com.keb.sercurity1.Model.User;
import com.keb.sercurity1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @GetMapping({"", "/"})
    public String index(){
        //머스태치!!권장!! 기본폴더 src/main/resources/
        //뷰리졸버를 설정할 때 templates(prefix), .mustache(suffix)
        return "index"; //src/main/resources/templates/index.mustache
    }
    @GetMapping("/user")
    public @ResponseBody
    String user(){
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

        userRepository.save(user); //<- 비밀번호 유출됨 시큐리티로 로그인 할 수 없음 암호화 필요함
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
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //매니저와 어드민 권한으로만 들어갈 수 있게 걸었음
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }

}
