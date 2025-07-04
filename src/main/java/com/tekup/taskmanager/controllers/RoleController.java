package com.tekup.taskmanager.controllers;


import com.tekup.taskmanager.entities.Role;
import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.services.IRoleService;
import com.tekup.taskmanager.services.RoleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v0/roles")
public class RoleController {

    private IRoleService roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @Valid @RequestBody Role role) {
        return ResponseEntity.ok(roleService.updateRole(id, role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getUsersByRoleId(id));
    }

    @PostMapping("/{roleId}/assign/{userId}")
    public ResponseEntity<Void> assignRole(@PathVariable Long roleId, @PathVariable Long userId) {
        roleService.assignRoleToUser(roleId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{roleId}/remove/{userId}")
    public ResponseEntity<Void> removeRole(@PathVariable Long roleId, @PathVariable Long userId) {
        roleService.removeRoleFromUser(roleId, userId);
        return ResponseEntity.ok().build();
    }
}
