package com.premierLeague.premier_Zone.dtos;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PlayerDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String player_Id;
    private String name;
    private String nation;
    private String pos;
    private Integer age;
    private Integer mp;
    private Integer starts;
    private Double min;
    private Double gls;
    private Double ast;
    private Double pk;
    private Double ycrd;
    private Double rcrd;
    private Double xg;
    private Double xag;
    private String team;
}
