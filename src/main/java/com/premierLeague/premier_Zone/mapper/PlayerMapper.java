package com.premierLeague.premier_Zone.mapper;

import com.premierLeague.premier_Zone.dtos.PlayerDto;
import com.premierLeague.premier_Zone.entity.Player;

public class PlayerMapper {
    public static PlayerDto playerToDto(Player player){
        PlayerDto dto = new PlayerDto();
        dto.setPlayer_Id(player.getPlayer_Id());//comment out for dev
        dto.setName(player.getName());
        dto.setNation(player.getNation());
        dto.setPos(player.getPos());
        dto.setAge(player.getAge());
        dto.setMp(player.getMp());
        dto.setStarts(player.getStarts());
        dto.setMin(player.getMin());
        dto.setGls(player.getGls());
        dto.setAst(player.getAst());
        dto.setPk(player.getPk());
        dto.setYcrd(player.getYcrd());
        dto.setRcrd(player.getRcrd());
        dto.setXg(player.getXg());
        dto.setXag(player.getXag());
        dto.setTeam(player.getTeam());
        return dto;
    }
}
