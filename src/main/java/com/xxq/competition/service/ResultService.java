package com.xxq.competition.service;

import com.xxq.competition.mapper.ResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    ResultMapper resultMapper;

    //用户轮次分数
    public int competorTurnScore(Integer competorId, Integer turnIndex) {
        return resultMapper.competorTurnScore(competorId,turnIndex);
    }

    //用户总分数
    public int competorScores(Integer competorId) {
        return resultMapper.competorScores(competorId);
    }



}
