package com.premierLeague.premier_Zone.repository;

import com.premierLeague.premier_Zone.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,String>{
    void deleteByName(String playerName);
    Optional<Player>findByName(String name);
}

