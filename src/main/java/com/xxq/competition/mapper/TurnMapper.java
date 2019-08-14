package com.xxq.competition.mapper;

import com.xxq.competition.entity.Turn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Mapper
@Repository
public interface TurnMapper {


    void createTurn(Turn turn);
    void updateTurn(Turn turn);
    Turn selectTurn(Integer id);
    Turn getMaxIndexTurn();

}
