package io.hexlet.typoreporter.service.oauth2vk;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static final String VK = "VK";

    public static OAuth2UserInfo getOAuth2UserInfo(String provider, String accessToken,
                                                   Map<String, Object> attributes) {
        return switch (provider.toUpperCase()) {
            case VK -> new VkOAuthUserInfo(accessToken, attributes);
            default -> throw new IllegalArgumentException("Unsupported provider: " + provider);
        };
    }
}
