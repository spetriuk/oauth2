package com.petriuk.controller;

import com.petriuk.service.RoleDto;
import com.petriuk.service.RoleNotFoundException;
import com.petriuk.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RolesRestController {

    @Autowired
    private RolesService rolesService;

    @GetMapping("/roles/{userId}")
    public String getRoleNameByUserId(@PathVariable Long userId) {
        return rolesService.getRoleNameByUserId(userId);
    }

    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return rolesService.getAllRoles();
    }

    @PostMapping("/roles")
    public ResponseEntity<?> addUserRole(@RequestBody RoleDto role) {
        rolesService.mapUserIdToRole(role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/roles")
    public ResponseEntity<?> editUserRole(@RequestBody RoleDto role) {
        rolesService.mapUserIdToRole(role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/roles/{userId}")
    public ResponseEntity<?> deleteUserRole(@PathVariable Long userId) {
        rolesService.deleteUserRole(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFound(RoleNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
