package io.hexlet.typoreporter.service.oauth2vk;

import java.util.Map;

public interface OAuth2UserInfo {

    String getEmail();
    String getPassword();
    String getUsername();
    String getFirstName();
    String getLastName();

    Map<String, Object> getAttributes();

}
