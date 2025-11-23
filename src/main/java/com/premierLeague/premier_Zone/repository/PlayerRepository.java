package com.premierLeague.premier_Zone.repository;

import com.premierLeague.premier_Zone.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,String>{
    Page<Player> findByTeamContainingIgnoreCase(String team, Pageable pageable);
    Page<Player>findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Player>findByNationContainingIgnoreCase(String nation, Pageable pageable);
    Page<Player>findByPosContainingIgnoreCase(String position, Pageable pageable);

    @Query("SELECT p FROM Player p WHERE " + "LOWER(p.name) LIKE LOWER(CONCAT('%',:q,'%')) OR " + "LOWER(p.team) LIKE LOWER(CONCAT('%',:q,'%')) OR " + "LOWER(p.pos) LIKE LOWER(CONCAT('%',:q,'%')) OR " + "LOWER(p.nation) LIKE LOWER(CONCAT('%',:q,'%'))" )
    Page<Player>search(@Param("q") String query, Pageable pageable);



}

