package com.example.spring_boot_rest.repository;

import com.example.spring_boot_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
        User findByUsername(String username);
}
