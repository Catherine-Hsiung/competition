package com.xxq.competition.service;

import com.xxq.competition.entity.Qbank;
import com.xxq.competition.entity.Turn;
import com.xxq.competition.mapper.QbankMapper;
import com.xxq.competition.mapper.TurnMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HostService {
    @Autowired
    QbankMapper qbankMapper;
    @Autowired
    TurnMapper turnMapper;
    @Autowired
    WebSocketServer webSocketServer;


    //false-当前题目未结束，true-当前题目已结束
    private volatile boolean currentQuestionFlag = true;
    private volatile static Qbank currentQuestion = null;
    private volatile static long beginTime;

    public Qbank selectRandomQuestion() {
        //获取当前进行到第几轮
        Turn turn = turnMapper.getMaxIndexTurn();
        if (turn == null) {
            log.info("轮次信息未开启");
            return null;
        }
        if (!currentQuestionFlag) {
            log.info("上题未结束");
            return null;
        }
        int turnQuestionNum = qbankMapper.calTurnQuestionNum(turn.getIndex());
        if (turnQuestionNum == 6) {
            log.info("当前轮次已结束");
            if (!turn.getTurnFlag()) {
                turn.setTurnFlag(true);
                turnMapper.updateTurn(turn);
            }
            return null;
        }
        Qbank qbank = qbankMapper.selectQusetionRandomly();
        if (turn.getIndex() <= 4 && qbank != null) {
            turn.setCurrentQuestion(turnQuestionNum + 1);
            turn.setQuestionId(qbank.getId());
            turnMapper.updateTurn(turn);//轮次表中插入题目ID
            qbank.setTurnId(turn.getIndex());//题目更新轮次
            qbankMapper.updateQuestion(qbank);//
            currentQuestionFlag = false;
            WebSocketServer.sendGroupMsg(qbank);//群发题目
            currentQuestion = qbank;//缓存当前题目
            beginTime = System.currentTimeMillis();
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.schedule(() -> {
                currentQuestionFlag = true;//该题目作答时间已过
                webSocketServer.uncommitCompetor();
            }, 100, TimeUnit.SECONDS);

            return qbank;
        } else if (qbank == null) {
            log.error("没有可用题目");
            return null;
        }
        log.error("未知错误");
        return null;
    }

   /*
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
        int turnQuestionNum = qbankMapper.calTurnQuestionNum(turn.getIndex());
        if (turnQuestionNum == 6) {
            log.info("当前轮次已结束");
            if (!turn.getTurnFlag()) {
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
            qbank.setTurnId(turn.getIndex());
            qbankMapper.updateQuestion(qbank);
            return qbank;
        } else if (qbank == null) {
            log.error("没有可用题目");
            return null;
        }
        log.error("未知错误");
        return null;
    }
    */

    public static Qbank getCurrentQuestion() {
        return currentQuestion;
    }

    public static long getBeginTime() {
        return beginTime;
    }

    public boolean isCurrentQuestionFlag() {
        return currentQuestionFlag;
    }
}
