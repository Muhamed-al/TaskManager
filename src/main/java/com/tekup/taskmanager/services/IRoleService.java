package com.tekup.taskmanager.services;

import com.tekup.taskmanager.entities.Role;
import com.tekup.taskmanager.entities.User;

import java.util.List;
import java.util.Set;

public interface IRoleService {

    public Role createRole(Role role);
    public Role updateRole(Long id, Role updatedRole);
    public void deleteRole(Long id);
    public List<Role> getAllRoles();
    public List<User> getUsersByRoleId(Long roleId);
    public void assignRoleToUser(Long roleId, Long userId);
    public void removeRoleFromUser(Long roleId, Long userId);
}
