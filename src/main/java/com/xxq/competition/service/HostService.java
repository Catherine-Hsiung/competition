package com.xxq.competition.service;

import com.xxq.competition.entity.Qbank;
import com.xxq.competition.mapper.QbankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    @Autowired
    QbankMapper qbankMapper;

    public Qbank selectRandomQuestion(){
        return qbankMapper.selectQusetionRandomly();
    }

}
