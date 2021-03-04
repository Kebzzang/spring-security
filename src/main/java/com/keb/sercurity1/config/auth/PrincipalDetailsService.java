package com.keb.sercurity1.config.auth;

import com.keb.sercurity1.Model.User;
import com.keb.sercurity1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//시큐리티 설정에서 loginProcessingUrl("/login")
//로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: "+username);
       User userEntity=userRepository.findByUsername(username);
       if(userEntity!=null){
           return new PrincipalDetails(userEntity);
       }
        return null;
    }
}
