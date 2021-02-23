package com.petriuk.service;

import com.petriuk.entity.Role;
import com.petriuk.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RoleRepository roleRepository;

    public String getRoleNameByUserId(Long userId) {
        return roleRepository.findRoleNameByUserId(userId)
            .orElseThrow(RoleNotFoundException::new);
    }

    public List<String> getAllRoles() {
        return roleRepository.selectDistinctNames();
    }

    @Transactional
    public void mapUserIdToRole(RoleDto newRole) {
        roleRepository.deleteUserId(newRole.getUserId());
        Role role = roleRepository.findByName(newRole.getRoleName())
            .orElseThrow(RoleNotFoundException::new);
        role.getUserIdList().add(newRole.getUserId());
        roleRepository.save(role);
    }

    @Transactional
    public void deleteUserRole(Long userId) {
        roleRepository.deleteUserId(userId);
    }

}
