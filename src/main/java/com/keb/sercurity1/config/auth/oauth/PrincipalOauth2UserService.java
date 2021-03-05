package com.keb.sercurity1.config.auth.oauth;

import com.keb.sercurity1.Model.User;
import com.keb.sercurity1.Repository.UserRepository;
import com.keb.sercurity1.config.auth.PrincipalDetails;
import com.keb.sercurity1.config.auth.oauth.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //함수 종료시 @Authentication 어노테이션이 만들어진다.
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(("getClientRegistration: "+userRequest.getClientRegistration()));
        System.out.println(("AccessToken: "+userRequest.getAccessToken().getTokenValue()));
       //구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code를 리턴 (OAuth Client 라이브러리) -> AccessToken 요청
        //userRequest 정보 -> 회원 프로필 받아야 함! 이 때 loadUser 함수 호출 -> 구글로부터 회원 프로필을 받을 수 있음
        //
        System.out.println(("getAttributes: "+super.loadUser(userRequest).getAttributes()));
        OAuth2User oauth2user=super.loadUser(userRequest);
        System.out.println("getAttributes:" + oauth2user.getAttributes());

        OAuth2UserInfo oAuth2UserInfo=null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo=new GoogleUserInfo(oauth2user.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo=new FacebookUserInfo(oauth2user.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo=new NaverUserInfo((Map)oauth2user.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo=new KakaoUserInfo(oauth2user.getAttributes());
        }
        else{
            System.out.println("구글, 페이스북 서비스만 지원합니다. ");
        }
      /*  String provider=userRequest.getClientRegistration().getRegistrationId();
        String providerId= oauth2user.getAttribute("sub");
        String username=provider+"_"+providerId;
        String password=bCryptPasswordEncoder.encode("encodedPassword");
        String email=oauth2user.getAttribute("email");
        String role="ROLE_USER";*/
        String providerId= oAuth2UserInfo.getProviderId();
        String provider=oAuth2UserInfo.getProvider();

        String username=provider+"_"+providerId;
        String password=bCryptPasswordEncoder.encode("encodedPassword");
        String email=oAuth2UserInfo.getEmail();
        String role="ROLE_USER";

        //이미 회원가입이 되어 있는 경우를 고려
       User userEntity= userRepository.findByUsername(username);
        if(userEntity==null){
            //없으면 새로 회원가입시키면 됨
            userEntity=User.builder()
                    .username(username)
                    .password(password)
                    .provider(provider)
                    .role(role)
                    .email(email)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oauth2user.getAttributes());
    }
}
