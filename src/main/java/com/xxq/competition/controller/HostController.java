package com.xxq.competition.controller;

import com.xxq.competition.response.ResponseMessage;
import com.xxq.competition.service.HostService;
import com.xxq.competition.service.TurnService;
import com.xxq.competition.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host")
@Slf4j
public class HostController {

    @Autowired
    HostService hostService;
    @Autowired
    TurnService turnService;
    @Autowired
    WebSocketServer webSocketServer;

    /**
     * 比赛开始接口
     * @return
     */
    @RequestMapping("/start")
    public ResponseMessage  competitionStart(){
        return new ResponseMessage("比赛正式开始",null,0);
    }


    /**
     * 开启轮次
     * @return
     */
    @RequestMapping("/index")
    public ResponseMessage getMaxTuenIndex(){
        //int maxIndex = turnService.getMaxIndexTurn().getIndex();
        return new ResponseMessage("success",turnService.getMaxIndexTurn(),0);
    }

    /**
     * 开启下一题
     * @return
     */
    @RequestMapping("/random")
    public ResponseMessage selectRandomQuestion() {
        return new ResponseMessage("success", hostService.selectRandomQuestion(), 0);
    }

    //获取多少人在线
    @RequestMapping("/countOnline")
    public ResponseMessage countOnline() {
        return new ResponseMessage("当前参与答题人数", WebSocketServer.getOnlineCount(), 0);
    }

//计算这道题的正确率
    //显示倒计时
    //这道题用时最短者
    //轮次排名
    //轮次答题王
    //

}
