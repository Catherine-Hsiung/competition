package com.xxq.competition.controller;

import com.xxq.competition.entity.Competor;
import com.xxq.competition.response.ResponseMessage;
import com.xxq.competition.service.CompetorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/competition")
@Slf4j
public class CompetorController {
    @Autowired
    CompetorService competorService;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ResponseMessage addUserInfo(@Valid @RequestBody Competor competor){
        competorService.addNewCompetor(competor);
        return new ResponseMessage("success",null,0);
    }
    @RequestMapping(value = "/updateuser",method = RequestMethod.POST)
    public ResponseMessage updateUserInfo(@Valid @RequestBody Competor competor){
        competorService.updateCompetor(competor);
        return new ResponseMessage("success",null,0);
    }

    @RequestMapping(value = "/selectusers",method = RequestMethod.GET)
    public ResponseMessage selectUserInfo(Integer id){
        competorService.selectCompetor(id);
        return new ResponseMessage("success",competorService.selectCompetor(id),0);
    }


    //预答题练习

    //获取比赛开始接口
    //显示该用户角色
    //显示当前轮次
    //显示当前题目选项
    //提交答案接口
    //显示正确答案
    //实时显示在线答题人数
    //显示轮次排名
    //显示总分排名

    //该用户目前总分数
    //该用户本轮总分数
    //获取答题历史记录
    //


}
