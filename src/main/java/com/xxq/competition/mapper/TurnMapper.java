package com.xxq.competition.mapper;

import com.xxq.competition.entity.Turn;

import java.util.Map;

public interface TurnMapper {


    void createTurn(Turn turn);
    void updateTurn(Turn turn);
    Turn selectTurn(Integer id);
}
