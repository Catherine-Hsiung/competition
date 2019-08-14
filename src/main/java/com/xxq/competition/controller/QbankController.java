package com.xxq.competition.controller;

import com.xxq.competition.entity.Qbank;
import com.xxq.competition.response.ResponseMessage;
import com.xxq.competition.service.QbankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/question")
@Slf4j
public class QbankController {
    @Autowired
    QbankService qbankService;

    @RequestMapping("/addquestion")
    public ResponseMessage addNewQusetion(@Valid @RequestBody Qbank qbank){
        qbankService.addNewQuestion(qbank);
        return new ResponseMessage("success",null,0);
    }
    @RequestMapping("/updatequestion")
    public ResponseMessage updateQuestion(@Valid @RequestBody Qbank qbank){
        qbankService.updateQuestion(qbank);
        return new ResponseMessage("success",null,0);
    }
    @RequestMapping("/checkquestion")
    public ResponseMessage checkQuestion(@NotNull Integer id){
//        qbankService.selectQuestion(id);
        return new ResponseMessage("success",qbankService.selectQuestion(id),0);
    }
    @RequestMapping("/deletequestion")
    public ResponseMessage delQuestion(@NotNull Integer id){
        qbankService.deleteQuestion(id);
        return new ResponseMessage("success",null,0);
    }

    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public boolean importQuestion(@RequestParam("proxyfile") MultipartFile file){
        boolean test = false;
        String filename = file.getOriginalFilename();
        try {
            test = qbankService.importQuestionExcelFile(filename,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }
}
