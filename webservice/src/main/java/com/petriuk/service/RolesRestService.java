package com.petriuk.service;

import com.petriuk.dto.RoleDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RolesRestService {
    private static final String baseUrl = "http://roles-api:8080/roles/";
    private static final RestTemplate restTemplate = new RestTemplate();

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
