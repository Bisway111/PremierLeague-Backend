package com.premierLeague.premier_Zone.service;

import com.premierLeague.premier_Zone.dtos.PlayerDto;
import com.premierLeague.premier_Zone.entity.Player;
import com.premierLeague.premier_Zone.mapper.PlayerMapper;
import com.premierLeague.premier_Zone.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private  PlayerRepository playerRepository;


    @Transactional(readOnly = true)
    public Page<PlayerDto> getPlayers(int page , int size, String sortBy){
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy).descending());
        return playerRepository.findAll(pageable).map(PlayerMapper::playerToDto);
    }
    @Transactional(readOnly = true)
   public Page<PlayerDto> searchPlayers(String q, int page,int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy).descending());
        return playerRepository.search(q,pageable).map(PlayerMapper::playerToDto);
   }
    @Transactional(readOnly = true)
    public Page<PlayerDto> getPlayersFromTeam(String teamName , int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy).descending());
        return playerRepository.findByTeamContainingIgnoreCase(teamName,pageable).map(PlayerMapper::playerToDto);
    }

    @Transactional(readOnly = true)
    public Page<PlayerDto> getPlayerByNation(String nation ,int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy).descending());
        return playerRepository.findByNationContainingIgnoreCase(nation,pageable).map(PlayerMapper::playerToDto);
    }

}
