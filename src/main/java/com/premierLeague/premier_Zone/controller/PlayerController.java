package com.premierLeague.premier_Zone.controller;

import com.premierLeague.premier_Zone.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*")
@Tag(name="Player API's", description = "getAllPlayers, search, filter by team, filter by nation")
public class PlayerController {
    @Autowired
    private PlayerService playerService;



 @GetMapping("/getAllPlayers")
 @SecurityRequirements
 @Operation(summary = "To get all player states , no login required")
 public ResponseEntity<?> getPlayers(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam(defaultValue = "gls") String sortBy
 ){
     return ResponseEntity.ok(playerService.getPlayers(page,size,sortBy));

 }

 @GetMapping("/search")
 @SecurityRequirements
 @Operation(summary = "To search  player state by player name , team name, nation, position , no login required")
 public ResponseEntity<?> searchPlayer(
         @RequestParam String q,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam(defaultValue = "gls") String sortBy){
     return ResponseEntity.ok(playerService.searchPlayers(q,page,size,sortBy));
 }






    @GetMapping("/team/{team}")
    @SecurityRequirements
    @Operation(summary = "To filter player states  by team, no login required")
    public ResponseEntity<?> getPlayersByTeam(
            @PathVariable String team,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "gls") String sortBy){
        return ResponseEntity.ok(playerService.getPlayersFromTeam(team,page,size,sortBy));
    }

    @GetMapping("/nation/{nation}")
    @SecurityRequirements
    @Operation(summary = "To filter player states  by team, no login required")
    public ResponseEntity<?> getPlayersByNation(
            @PathVariable String nation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "gls") String sortBy){
        return ResponseEntity.ok(playerService.getPlayerByNation(nation,page,size,sortBy));
    }




}
