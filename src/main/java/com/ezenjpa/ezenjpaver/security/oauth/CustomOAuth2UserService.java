package com.ezenjpa.ezenjpaver.security.oauth;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.enums.UserProvider;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomOAuthUserDetailsService userDetailsService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("-------- OAuth2 로그인 요청 ---------");
        log.info("provider 에서 사용자 정보를 불러옵니다.");
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (InternalAuthenticationServiceException e){
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user){
        UserProvider userProvider = UserProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userProvider, user.getAttributes());

        log.info("DB에서 사용자 정보를 조회합니다.");
        UserEntity registeredUser = userRepository.findByUserId(userInfo.getId());
        if(registeredUser != null){
            if(!userProvider.equals(registeredUser.getUserProvider())){
                throw new OAuthProviderMissMatchException(
                        "provider miss match! 로그인 시도 provider : " +
                        userProvider + " , 저장된 provider : " +
                                registeredUser.getUserProvider()
                );
            }
            log.info("기존 사용자의 로그인 시도. 사용자 정보를 업데이트 합니다.");
            registeredUser = updateUser(registeredUser, userInfo);
        }else {
            log.info("새로운 사용자의 로그인 시도. 새 사용자를 등록합니다.");
            registeredUser = createUser(userInfo, userProvider);
        }
        return UserPrincipal.create(registeredUser, user.getAttributes());
    }

    private UserEntity createUser(OAuth2UserInfo userInfo, UserProvider userProvider){
        UserEntity user = UserEntity.builder()
                .userId(userInfo.getId())
                .userName(userInfo.getName())
                .userEmail(userInfo.getEmail())
                .userProvider(userProvider)
                .build();
        log.info("새로운 사용자 {}, 이름 : {} 생성했습니다.", userProvider.getValue(), userInfo.getName());
        return userRepository.save(user);
    }

    private UserEntity updateUser(UserEntity user, OAuth2UserInfo userInfo){
        if(userInfo.getName() != null && !user.getUserName().equals(userInfo.getName())){
            user.setUserName(userInfo.getName());
        }
        return userRepository.save(user);
    }
}
