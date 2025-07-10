package com.tekup.taskmanager.repositories;

import com.tekup.taskmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameContainingIgnoreCase(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRoles_Name(String roleName);
    
    // Fetch user with roles eagerly to avoid LazyInitializationException
    @org.springframework.data.jpa.repository.Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findByUsernameWithRoles(String username);
    
    // Fetch all users with roles eagerly to avoid LazyInitializationException
    @org.springframework.data.jpa.repository.Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();
    
    // Fetch user by ID with roles eagerly to avoid LazyInitializationException
    @org.springframework.data.jpa.repository.Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findByIdWithRoles(Long id);
}
