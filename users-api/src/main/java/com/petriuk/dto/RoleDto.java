package com.petriuk.dto;

public class RoleDto {
    private String roleName;
    private Long userId;

    public RoleDto(String roleName, Long userId) {
        this.roleName = roleName;
        this.userId = userId;
    }

    public RoleDto() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
