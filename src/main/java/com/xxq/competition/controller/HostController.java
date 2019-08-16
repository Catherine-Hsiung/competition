package com.xxq.competition.controller;

import com.xxq.competition.response.ResponseMessage;
import com.xxq.competition.service.HostService;
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

    @RequestMapping("/random")
    public ResponseMessage selectRandomQuestion() {
        return new ResponseMessage("success", hostService.selectRandomQuestion(), 0);
    }


    //比赛开始接口
    //获取轮次
    //随机获取题目

    //获取多少人在线

    //开启轮次接口
    //开启下一题接口
}
