package com.xxq.competition.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Turn {

    private Integer id;
    private String label;
    @NotNull
    @Range(min = 1,max = 4)
    private Integer index;
    @NotNull
    private String questionId;
    @NotNull
    private Integer currentQuestion;
    private Boolean turnFlag;
}
