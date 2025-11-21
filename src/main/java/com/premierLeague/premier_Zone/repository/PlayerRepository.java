package com.premierLeague.premier_Zone.repository;

import com.premierLeague.premier_Zone.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,String>{
    List<Player>findByTeam(String team);
    List<Player>findByNameContainingIgnoreCase(String name);
    List<Player>findByNationContainingIgnoreCase(String nation);
    List<Player>findByPosContainingIgnoreCase(String position);

    void deleteByName(String playerName);

}

