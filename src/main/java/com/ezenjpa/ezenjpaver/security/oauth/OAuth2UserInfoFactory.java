package com.ezenjpa.ezenjpaver.security.oauth;

import com.ezenjpa.ezenjpaver.enums.UserProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(UserProvider userProvider, Map<String, Object> attributes){
        switch (userProvider){
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("잘못된 provider 정보");
        }
    }
}
