package com.example.demosecurity.Repository;

import com.example.demosecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    Optional<User> findByName(String username);

}
