package com.keb.sercurity1.config.auth.oauth.provider;

import net.minidev.json.JSONObject;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private Map<String, Object> properties; // oauth2user.getAttributes()

    public KakaoUserInfo(Map<String, Object> originProperties){
        this.properties = originProperties;
    }

    @Override
    public String getProviderId() {
        System.out.println("properties!!: "+ properties.get("id"));
        System.out.println("properties!!: "+ properties.get("properties"));
        return properties.get("id").toString();
    }

    @Override
    public String getProvider() {
      return "kakao";
    }

    @Override
    public String getEmail() {
         Map<String, Object> temp= (Map<String, Object>) properties.get("kakao_account");
      //   Map<String, Object> temp2=(Map<String, Object>) temp.get("email");
        System.out.println("user email here: "+ temp.get("email"));
        return (String) temp.get("email");
    }

    @Override
    public String getName() {
        Map<String, Object> temp= (Map<String, Object>) properties.get("properties");
        return (String) temp.get("nickname");
    }
}
