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
        competorService.addNewCompetor(competor);
        return new ResponseMessage("success",null,0);
    }
}
