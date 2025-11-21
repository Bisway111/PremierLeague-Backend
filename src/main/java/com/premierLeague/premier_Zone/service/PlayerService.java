package com.premierLeague.premier_Zone.service;

import com.premierLeague.premier_Zone.entity.Player;
import com.premierLeague.premier_Zone.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private  PlayerRepository playerRepository;


    public List<Player> getPlayers(){

        return playerRepository.findAll();
    }

    public List<Player> getPlayersFromTeam(String teamName){

        return playerRepository.findByTeam(teamName);
    }

    public List<Player> getPlayersByName(String name){
        return playerRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Player> getPlayerByPos(String position){
        return playerRepository.findByPosContainingIgnoreCase(position);
    }

    public List<Player> getPlayerByNation(String nation){
        return playerRepository.findByNationContainingIgnoreCase(nation);
    }


    //    public List<Player> getPlayerBYTeamAndPosition(String team , String position){
//        return playerRepository.findAll().stream()
//                .filter(player -> team.equals(player.getTeam()) && position.equals(player.getPos()))
//                .collect(Collectors.toList());
//    }
//    public Player addPlayer(Player player) {
//        playerRepository.save(player);
//        return player;
//    }
//    public Player updatePlayer(Player updatedPlayer) {
//        Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());
//
//        if (existingPlayer.isPresent()) {
//            Player playerToUpdate = existingPlayer.get();
//            playerToUpdate.setName(updatedPlayer.getName());
//            playerToUpdate.setTeam(updatedPlayer.getTeam());
//            playerToUpdate.setPos(updatedPlayer.getPos());
//            playerToUpdate.setNation(updatedPlayer.getNation());
//            playerRepository.save(playerToUpdate);
//            return playerToUpdate;
//        }
//        return null;
//    }
    @Transactional
    public void deletePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }

}
