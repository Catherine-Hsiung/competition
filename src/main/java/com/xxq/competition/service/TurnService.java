package com.xxq.competition.service;

import com.alibaba.fastjson.JSON;
import com.xxq.competition.entity.Turn;
import com.xxq.competition.mapper.TurnMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TurnService {
    @Autowired
    TurnMapper turnMapper;

    public Turn getMaxIndexTurn(){

        Turn indexTurn = turnMapper.getMaxIndexTurn();
        if (indexTurn==null){
            indexTurn = new Turn();
            indexTurn.setIndex(1);
            indexTurn.setLabel("第1轮");
            indexTurn.setTurnFlag(false);
            turnMapper.createTurn(indexTurn);
            return indexTurn;
        }else {
            log.info("index:{}", JSON.toJSONString(indexTurn));
            if (indexTurn.getTurnFlag()){
                if (indexTurn.getIndex()<4) {
                    indexTurn.setIndex(indexTurn.getIndex() + 1);
                    turnMapper.createTurn(indexTurn);
                    return indexTurn;
                }else {
                    log.info("比赛结束");
                    return null;
                }
            }else {
                return indexTurn;
            }

        }

    }
}
