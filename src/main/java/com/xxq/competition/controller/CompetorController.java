package com.xxq.competition.controller;

import com.xxq.competition.entity.Competor;
import com.xxq.competition.response.ResponseMessage;
import com.xxq.competition.service.CompetorService;
import com.xxq.competition.service.ResultService;
import com.xxq.competition.service.TurnService;
import com.xxq.competition.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.Session;

@RestController
@RequestMapping("/competition")
@Slf4j
public class CompetorController {
    @Autowired
    CompetorService competorService;
    @Autowired
    TurnService turnService;
    @Autowired
    ResultService resultService;
//    @Autowired
//    WebSocketServer webSocketServer;

    /**
     * 新增用户信息
     *
     * @param competor
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseMessage addUserInfo(@Valid @RequestBody Competor competor) {
        competorService.addNewCompetor(competor);
        return new ResponseMessage("success", null, 0);
    }

    /**
     * 更新用户信息
     *
     * @param competor
     * @return
     */
    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)
    public ResponseMessage updateUserInfo(@Valid @RequestBody Competor competor) {
        competorService.updateCompetor(competor);
        return new ResponseMessage("success", null, 0);
    }

    /**
     * 更具用户id选择用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectusers", method = RequestMethod.GET)
    public ResponseMessage selectUserInfo(Integer id) {
        competorService.selectCompetor(id);
        return new ResponseMessage("success", competorService.selectCompetor(id), 0);
    }

    /**
     * 显示该用户角色
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getrole")
    public ResponseMessage getCompetitionRole(Integer id) {
        return new ResponseMessage("角色", competorService.getCompetorRole(id), 0);
    }
    //预答题练习


    /**
     * 显示当前轮次
     *
     * @return
     */
    @RequestMapping(value = "/getindex")
    public ResponseMessage getTurnIndex() {
        return new ResponseMessage("当前轮次", turnService.getMaxIndexTurn().getIndex(), 0);
    }

    //推送题目及选项
    //提交答案接口
//    public void getAnswer(String answer, String competorId) {
//        Session session = webSocketServer.getSessionId(competorId);
//        if ( session!= null) {
//            webSocketServer.onMessage(answer,session);
//        } else {
//            log.info("提交答案异常");
//        }
//
//    }
    //推送正确答案

    //显示轮次排名

    //显示总分排名

    //该用户目前总分数
    @RequestMapping(value = "/scores")
    public ResponseMessage getScore(Integer competorId) {
        return new ResponseMessage("总分数", resultService.competorScores(competorId), 0);
    }
    //该用户本轮总分数
//    public ResponseMessage getTurnRank(Integer competorId, Integer turnIndex){
//
//    }
    //获取答题历史记录
    //


}
