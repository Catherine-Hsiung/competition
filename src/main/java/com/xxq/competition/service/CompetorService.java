package com.xxq.competition.service;

import com.xxq.competition.entity.Competor;
import com.xxq.competition.mapper.CompetorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetorService {
    @Autowired
    CompetorMapper competorMapper;

    public void addNewCompetor(Competor competor){
        competorMapper.insertCompetor(competor);
    }
    public void updateCompetor(Competor competor){
        competorMapper.updateCompetor(competor);
    }
}
