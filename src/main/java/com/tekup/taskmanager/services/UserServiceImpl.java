package com.tekup.taskmanager.services;


import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.exception.UserNotFoundException;
import com.tekup.taskmanager.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import com.tekup.taskmanager.dto.AssignedUserDTO;
import com.tekup.taskmanager.mapper.Mapper;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    @Autowired // Injection de dépendance via l'annotation le constructeur
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public User updateUser(Long id, User updatedUser) throws UserNotFoundException {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setRoles(updatedUser.getRoles());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }


    @Override
    public List<AssignedUserDTO> getAllUsers() {
        // Use eager loading to avoid LazyInitializationException when mapping to DTO
        return userRepository.findAllWithRoles().stream()
                .map(Mapper::toAssignedUserDTO)
                .toList();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        // Use eager loading to avoid LazyInitializationException when roles are accessed
        return userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findByRoles_Name(roleName);
    }

    
}
