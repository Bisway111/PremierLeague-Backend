package com.premierLeague.premier_Zone.service;

import com.premierLeague.premier_Zone.dtos.PlayerDto;
import com.premierLeague.premier_Zone.entity.Player;
import com.premierLeague.premier_Zone.mapper.PlayerMapper;
import com.premierLeague.premier_Zone.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PlayerService {

    @Autowired
    private  PlayerRepository playerRepository;


    @Transactional(readOnly = true)
    public Page<PlayerDto> getPlayers(int page , int size, String sortBy){
        log.info("Fetching all players | page={}, size={}, sortBy={}", page, size, sortBy);
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy).descending());
       Page<PlayerDto> result =  playerRepository.findAll(pageable).map(PlayerMapper::playerToDto);
       log.info("Fetched {} players",result.getTotalElements());
        return result;

    }
//    @Cacheable(value="search",key="#q")
    @Transactional(readOnly = true)
   public Page<PlayerDto> searchPlayers(String q, int page,int size, String sortBy){
        log.info("Search players | query={}, page={}, size={}, sortBy={}", q, page, size, sortBy);
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy).descending());
        Page<PlayerDto> result = playerRepository.search(q,pageable).map(PlayerMapper::playerToDto);
        log.info("Search complete| matched={} players",result.getTotalElements());
        return result;
   }
//   @Cacheable(value ="team", key="#teamName")
    @Transactional(readOnly = true)
    public Page<PlayerDto> getPlayersFromTeam(String teamName , int page, int size, String sortBy){
       log.info("Fetching  players from team='{}' |  page={}, size={}, sortBy={}", teamName, page, size, sortBy);
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy).descending());
       Page<PlayerDto> result = playerRepository.findByTeamContainingIgnoreCase(teamName,pageable).map(PlayerMapper::playerToDto);
       log.info("Team fetch complete| team='{}',  players={}", teamName, result.getTotalElements());
        return result;
    }
//    @Cacheable(value ="nation", key="#nation")
    @Transactional(readOnly = true)
    public Page<PlayerDto> getPlayerByNation(String nation ,int page, int size, String sortBy){
        log.info("Fetching  players by nation='{}' |  page={}, size={}, sortBy={}", nation, page, size, sortBy);
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy).descending());
        Page<PlayerDto> result = playerRepository.findByNationContainingIgnoreCase(nation,pageable).map(PlayerMapper::playerToDto);
        log.info("Nation fetch complete| nation='{}',  players={}", nation, result.getTotalElements());
        return result;
    }

}
