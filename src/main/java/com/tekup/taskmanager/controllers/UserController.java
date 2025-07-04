package com.tekup.taskmanager.controllers;


import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.entities.Task;
import com.tekup.taskmanager.exception.UserNotFoundException;
import com.tekup.taskmanager.services.IUserService;
import com.tekup.taskmanager.dto.AssignedUserDTO;
import com.tekup.taskmanager.mapper.Mapper;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/api/v0/users")
public class UserController {

    private final IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }




    @GetMapping("/{id}")
    public AssignedUserDTO getById(@PathVariable Long id) throws UserNotFoundException {
        return Mapper.toAssignedUserDTO(userService.getUserById(id));
    }

    @GetMapping
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
