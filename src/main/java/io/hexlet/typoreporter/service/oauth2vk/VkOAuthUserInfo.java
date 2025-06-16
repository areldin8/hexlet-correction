package io.hexlet.typoreporter.service.oauth2vk;

import java.util.Map;

public class VkOAuthUserInfo implements OAuth2UserInfo {

    private final String accessToken;
    private final Map<String, Object> attributes;

    public VkOAuthUserInfo(String accessToken, Map<String, Object> attributes) {

        this.attributes = attributes;
        this.accessToken = accessToken;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("default_email");
    }

    @Override
    public String getUsername() {
        return attributes.get("login").toString();
    }

//    @Override
//    public String getPassword() {
//        return "";
//    }

    @Override
    public String getFirstName() {
        String[] names = attributes.get("name").toString().split(" ");
        return names.length > 0 ? names[0] : "";
    }

    @Override
    public String getLastName() {
        String[] names = attributes.get("name").toString().split(" ");
        return names.length > 1 ? names[1] : "";
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
