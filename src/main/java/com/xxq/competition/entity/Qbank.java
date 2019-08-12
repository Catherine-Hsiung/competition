package com.xxq.competition.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Qbank {
    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String answer_a;
    @NotNull
    private String answer_b;
    @NotNull
    private String answer_c;
    @NotNull
    private String answer_d;
    @NotNull
    private String right_answer;
    @NotNull
    private Integer turn_id;
}
