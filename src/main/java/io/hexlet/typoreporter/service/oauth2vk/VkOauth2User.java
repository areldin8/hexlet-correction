package io.hexlet.typoreporter.service.oauth2vk;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class VkOauth2User extends DefaultOAuth2User {

    private final String nickname;

    public VkOauth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String keyAttribute) {
        super(authorities, attributes, keyAttribute);
        this.nickname = (String) attributes.get("login");
    }

}
