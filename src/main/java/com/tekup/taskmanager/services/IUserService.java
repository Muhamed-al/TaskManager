package com.tekup.taskmanager.services;

import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.exception.UserNotFoundException;
import com.tekup.taskmanager.dto.AssignedUserDTO;

import java.util.List;
import java.util.Optional;


public interface IUserService {

    public User updateUser(Long id, User updatedUser) throws UserNotFoundException;     // Mettre à jour un utilisateur existant
    public void deleteUser(Long id) throws UserNotFoundException; // Supprimer un utilisateur par son ID
    public List<AssignedUserDTO> getAllUsers(); // Récupérer un utilisateur par son ID
    public User getUserById(Long id) throws UserNotFoundException;
    public Optional<User> getUserByUsername(String username); // Récupérer tous les utilisateurs
    public Optional<User> getUserByEmail(String email); // Vérifier l'existence d'un utilisateur par email
    public List<User> getUsersByRole(String roleName);
}
