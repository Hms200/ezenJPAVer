package com.ezenjpa.ezenjpaver.security.oauth;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.enums.Role;
import com.ezenjpa.ezenjpaver.enums.UserProvider;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {

    private final String userId;
    private final String password;
    private final UserProvider userProvider;
    private final Role role;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getUsername() {
        return userId;
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
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public String getName() {
        return userId;
    }



    public static UserPrincipal create(UserEntity user){
        log.info("Oauth2 user principal 생성");
        return new UserPrincipal(
                user.getUserId(),
                user.getUserPw(),
                user.getUserProvider(),
                Role.MEMBER,
                Collections.singletonList(new SimpleGrantedAuthority(Role.MEMBER.getValue())));
    }

    public static UserPrincipal create(UserEntity user, Map<String, Object> attributes){
        log.info("user principal 에 {}에서 제공받은 사용자정보를 등록합니다.", user.getUserProvider());
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
}
