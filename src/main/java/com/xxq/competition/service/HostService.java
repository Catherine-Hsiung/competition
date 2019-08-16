package com.xxq.competition.service;

import com.xxq.competition.entity.Qbank;
import com.xxq.competition.entity.Turn;
import com.xxq.competition.mapper.QbankMapper;
import com.xxq.competition.mapper.TurnMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HostService {
    @Autowired
    QbankMapper qbankMapper;
    @Autowired
    TurnMapper turnMapper;


    public Qbank selectRandomQuestion() {
        //获取当前进行到第几轮
        Turn turn = turnMapper.getMaxIndexTurn();
        if (turn == null) {
            log.info("轮次信息未开启");
            return null;
        }
        if (turn.getTurnFlag()) {
            log.info("当前轮次已结束");
            return null;
        }
        int turnQuestionNum = qbankMapper.calTurnQuestionNum(turn.getId());
        if (turnQuestionNum == 6) {
            log.info("当前轮次已结束");
            if (!turn.getTurnFlag()){
                turn.setTurnFlag(true);
                turnMapper.updateTurn(turn);
            }
            return null;
        }
        Qbank qbank = qbankMapper.selectQusetionRandomly();
        if (turn.getIndex() <= 4 && qbank != null) {
            turn.setCurrentQuestion(turnQuestionNum + 1);
            turn.setQuestionId(qbank.getId());
            turnMapper.updateTurn(turn);
            qbank.setTurnId(turn.getId());
            qbankMapper.updateQuestion(qbank);
            return qbank;
        }
        log.error("未知错误");
        return null;
    }

}
