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

    public Turn getMaxIndexTurn() {

        Turn turn = turnMapper.getMaxIndexTurn();
        if (turn == null) {
            turn = new Turn();
            turn.setIndex(1);
            turn.setLabel("第1轮");
            turn.setTurnFlag(false);
            turnMapper.createTurn(turn);
            return turn;
        } else {
            log.info("index:{}", JSON.toJSONString(turn));
            if (turn.getTurnFlag()) {
                if (turn.getIndex() < 4) {
                    Turn nextTurn = new Turn();
                    nextTurn.setIndex(turn.getIndex() + 1);
                    nextTurn.setLabel("第"+nextTurn.getIndex()+"轮");
                    nextTurn.setTurnFlag(false);
                    turnMapper.createTurn(nextTurn);
                    return nextTurn;
                } else {
                    log.info("比赛结束");
                    return null;
                }
            } else {
                return turn;
            }

        }

    }
}
