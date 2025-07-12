package com.tekup.taskmanager.controllers;

import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.entities.Task;
import com.tekup.taskmanager.exception.UserNotFoundException;
import com.tekup.taskmanager.services.IUserService;
import com.tekup.taskmanager.dto.AssignedUserDTO;
import com.tekup.taskmanager.mapper.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/api/v0/users")
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private final IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }




    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = AssignedUserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public AssignedUserDTO getById(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) throws UserNotFoundException {
        return Mapper.toAssignedUserDTO(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users in the system (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AssignedUserDTO.class))),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<AssignedUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }




    @GetMapping("/search")
    public ResponseEntity<AssignedUserDTO> searchUser(@RequestParam(required = false) String username,
                                           @RequestParam(required = false) String email) {
        if (username != null) {
            return userService.getUserByUsername(username)
                    .map(user -> ResponseEntity.ok(Mapper.toAssignedUserDTO(user)))
                    .orElse(ResponseEntity.notFound().build());
        } else if (email != null) {
            return userService.getUserByEmail(email)
                    .map(user -> ResponseEntity.ok(Mapper.toAssignedUserDTO(user)))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<AssignedUserDTO>> getUsersByRole(@PathVariable String roleName) {
        List<AssignedUserDTO> dtos = userService.getUsersByRole(roleName).stream()
            .map(Mapper::toAssignedUserDTO)
            .toList();
        return ResponseEntity.ok(dtos);
    }
}
