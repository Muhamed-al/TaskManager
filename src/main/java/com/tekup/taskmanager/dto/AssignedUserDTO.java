package com.tekup.taskmanager.dto;


import com.tekup.taskmanager.entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class AssignedUserDTO {

    private Long userId;
    private String username;
    private String email;
    private List<Role> roles;
}
