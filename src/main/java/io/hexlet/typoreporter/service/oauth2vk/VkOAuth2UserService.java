package io.hexlet.typoreporter.service.oauth2vk;

import io.hexlet.typoreporter.service.AccountService;
import io.hexlet.typoreporter.service.account.signup.SignupAccount;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountService accountService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        log.debug("Raw VK OAuth2 response: {}", oAuth2User.getAttributes());

        Map<String, Object> vkUser = extractVkUserData(oAuth2User.getAttributes());
        String vkId = String.valueOf(vkUser.get("id"));

        SignupAccount account = new SignupAccount(
            "vk_" + vkId,
            (String) vkUser.get("email"),
            null,
            (String) vkUser.get("first_name"),
            (String) vkUser.get("last_name"),
            "VK",
            vkId
        );

        if (!accountService.existsByExternalId(vkId)) {
            accountService.signup(account);
        }

        return new DefaultOAuth2User(
            oAuth2User.getAuthorities(),
            Map.of(
                "id", vkId,
                "username", "vk_" + vkId,
                "firstName", account.firstName(),
                "lastName", account.lastName(),
                "email", account.email() != null ? account.email() : ""
            ),
            "id"
        );
    }

    private Map<String, Object> extractVkUserData(Map<String, Object> attributes) {
        Object responseObj = attributes.get("response");

        if (responseObj == null) {
            throw new OAuth2AuthenticationException("VK response field is missing");
        }

        if (!(responseObj instanceof List<?> responseList)) {
            throw new OAuth2AuthenticationException("VK response is not a list");
        }

        if (responseList.isEmpty()) {
            throw new OAuth2AuthenticationException("VK user data not found");
        }

        Object firstItem = responseList.get(0);
        if (!(firstItem instanceof Map<?, ?>)) {
            throw new OAuth2AuthenticationException("VK user data has invalid format");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> userData = (Map<String, Object>) firstItem;

        return userData;
    }
}


