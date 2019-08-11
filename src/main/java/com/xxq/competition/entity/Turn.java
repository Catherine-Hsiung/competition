package com.xxq.competition.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Turn {
    private Integer id;
    private String label;
    private String index;
    private Date start;
    private Date end;
}
