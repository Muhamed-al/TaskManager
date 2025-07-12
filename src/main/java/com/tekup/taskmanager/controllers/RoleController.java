package com.tekup.taskmanager.controllers;

import com.tekup.taskmanager.entities.Role;
import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.services.IRoleService;
import com.tekup.taskmanager.services.RoleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v0/roles")
@Tag(name = "Roles", description = "Role management APIs")
public class RoleController {

    private IRoleService roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates a new role in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role created successfully",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Get all roles", description = "Retrieves all roles in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
