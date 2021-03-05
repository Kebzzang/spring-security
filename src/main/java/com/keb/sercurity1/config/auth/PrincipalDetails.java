package com.keb.sercurity1.config.auth;

import com.keb.sercurity1.Model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user; //composition:
    private Map<String, Object> attributes;

    public PrincipalDetails(User user){
        this.user=user;
    } //일반 로그인시 사용하는 생성자

    public PrincipalDetails(User user, Map<String, Object> attributes){ //oauth 로그인시 사용하는 생성자
        this.user=user;
        this.attributes=attributes;
    }
    //해당 유저의 권한을 리턴하는 곳!!컬렉션 타입
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect=new ArrayList<GrantedAuthority>();
        collect.add(new SimpleGrantedAuthority(user.getRole()));
        return collect;
    }


    @Override
    public Map<String, Object> getAttributes(){
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //사이트에 1년동안 회원이 로그인 안하면? -> 휴면 계정으로 넘기기
        return true;
    }
}
