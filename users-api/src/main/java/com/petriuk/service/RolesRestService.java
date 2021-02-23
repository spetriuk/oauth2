package com.petriuk.service;

import com.petriuk.dto.RoleDto;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RolesRestService {
    private static final String baseUrl = "http://petriuk-roles-api:8080/roles/";

    @Autowired
    private KeycloakRestTemplate restTemplate;

    public String getRoleByUserId(Long userId) {
        ResponseEntity<String> response = restTemplate
            .getForEntity(baseUrl + userId, String.class);
        return response.getBody();
    }

    public void postUserRole(RoleDto roleDto) {
        setUserRole(HttpMethod.POST, roleDto);
    }

    public void putUserRole(RoleDto roleDto) {
        setUserRole(HttpMethod.PUT, roleDto);
    }

    public void deleteUserRole(Long userId) {
        restTemplate.delete(baseUrl + userId);
    }

    private void setUserRole(HttpMethod method, RoleDto roleDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RoleDto> entity = new HttpEntity<>(roleDto, headers);
        restTemplate.exchange(baseUrl, method, entity, RoleDto.class);
    }
}
