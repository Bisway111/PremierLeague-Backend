package com.premierLeague.premier_Zone.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="player_statistic",
        indexes = {@Index(columnList = "player_name"),
                @Index(columnList = "nation"),
                @Index(columnList = "position"),
                @Index(columnList = "team_name")})
public class Player {
    @Id    //comment out for dev
    private String player_Id;//comment out for dev
//    @Id// comment out off for dev
    @Column(name="player_name")
    private String name;
    @Column(name="nation")
    private String nation;
    @Column(name="position")
    private String pos;
    @Column(name="age")
    private Integer age;
    @Column(name="matches_played")
    private Integer mp;
    @Column(name="starts")
    private Integer starts;
    @Column(name="minutes_played")
    private Double min;
    @Column(name="goals")
    private Double gls;
    @Column(name="assists")
    private Double ast;
    @Column(name="penalties_scored")
    private Double pk;
    @Column(name="yellow_card")
    private Double ycrd;
    @Column(name="red_cards")
    private Double rcrd;
    @Column(name="expected_goals")
    private Double xg;
    @Column(name="expected_assists")
    private Double xag;
    @Column(name="team_name")
    private String team;

}

