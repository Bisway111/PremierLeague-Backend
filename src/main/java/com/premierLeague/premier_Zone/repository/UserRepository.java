package com.premierLeague.premier_Zone.repository;

import com.premierLeague.premier_Zone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

Optional<User> findByUsername(String username);
Optional<User> findByEmailIgnoreCase(String email);
boolean existsByEmailIgnoreCase(String email);
}
