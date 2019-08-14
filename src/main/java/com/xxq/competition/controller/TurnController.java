package com.xxq.competition.controller;

import com.xxq.competition.response.ResponseMessage;
import com.xxq.competition.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/turn")
@RestController
public class TurnController {
    @Autowired
    TurnService turnService;

    @RequestMapping("/index")
    public ResponseMessage getMaxTuenIndex(){
        //int maxIndex = turnService.getMaxIndexTurn().getIndex();
        return new ResponseMessage("success",turnService.getMaxIndexTurn(),0);
    }
}
