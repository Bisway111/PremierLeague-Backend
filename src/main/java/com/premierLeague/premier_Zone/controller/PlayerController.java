package com.premierLeague.premier_Zone.controller;

import com.premierLeague.premier_Zone.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*")
public class PlayerController {
    @Autowired
    private PlayerService playerService;



 @GetMapping("/getAllPlayers")
 public ResponseEntity<?> getPlayers(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam(defaultValue = "gls") String sortBy
 ){
     return ResponseEntity.ok(playerService.getPlayers(page,size,sortBy));

 }

 @GetMapping("/search")
 public ResponseEntity<?> searchPlayer(
         @RequestParam String q,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam(defaultValue = "gls") String sortBy){
     return ResponseEntity.ok(playerService.searchPlayers(q,page,size,sortBy));
 }






    @GetMapping("/team/{team}")
    public ResponseEntity<?> getPlayersByTeam(
            @PathVariable String team,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "gls") String sortBy){
        return ResponseEntity.ok(playerService.getPlayersFromTeam(team,page,size,sortBy));
    }

    @GetMapping("/nation/{nation}")
    public ResponseEntity<?> getPlayersByNation(
            @PathVariable String nation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "gls") String sortBy){
        return ResponseEntity.ok(playerService.getPlayerByNation(nation,page,size,sortBy));
    }
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @GetMapping("/redis-info")
    public String redisInfo() {
        // Cast to Lettuce implementation
        var lettuce = (org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory) connectionFactory;

        // Get actual standalone config built by Spring Boot
        var cfg = lettuce.getStandaloneConfiguration();

        return "Redis Host: " + cfg.getHostName() + ", Port: " + cfg.getPort();
    }




}
