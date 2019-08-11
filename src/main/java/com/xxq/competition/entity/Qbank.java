package com.xxq.competition.entity;

import lombok.Data;

@Data
public class Qbank {
    private Integer id;
    private String title;
    private String answer_a;
    private String answer_b;
    private String answer_c;
    private String answer_d;
    private String right_answer;
    private Integer turn_id;
}
