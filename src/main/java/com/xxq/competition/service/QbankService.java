package com.xxq.competition.service;

import com.xxq.competition.entity.Qbank;
import com.xxq.competition.mapper.QbankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QbankService {

    @Autowired
    QbankMapper qbankMapper;

    public void addNewQuestion(Qbank qbank){
        qbankMapper.insertQuestion(qbank);
    }

    public void updateQuestion(Qbank qbank){
        qbankMapper.updateQuestion(qbank);
    }

    public Map selectQuestion(Integer id){
        return qbankMapper.selectQuestion(id);
    }

    public void deleteQuestion(Integer id){
        qbankMapper.deleteQuestion(id);
    }

}
