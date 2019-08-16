package com.xxq.competition.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Qbank {
    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String answerA;
    @NotNull
    private String answerB;
    @NotNull
    private String answerC;
    @NotNull
    private String answerD;
    @NotNull
    private String rightAnswer;
    private Integer turnId;
    private Boolean questionEffect;
}
