package com.xxq.competition.service;

import com.xxq.competition.entity.Competor;
import com.xxq.competition.mapper.CompetorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CompetorService {
    @Autowired
    CompetorMapper competorMapper;
    @Autowired
    WebSocketServer webSocketServer;


    /**
     * 将该用户增加到数据库
     *
     * @param competor
     */
    public void addNewCompetor(Competor competor) {
        competorMapper.insertCompetor(competor);
        webSocketServer.addCompetorToSet(competor.getId());
    }


    /**
     * 更新该用户的信息到数据库
     *
     * @param competor
     */
    public void updateCompetor(Competor competor) {
        competorMapper.updateCompetor(competor);
    }


    /**
     * 通过id选择用户
     *
     * @param id
     * @return
     */
    public Map selectCompetor(Integer id) {
        return competorMapper.selectCompetor(id);
    }

    /**
     * 获取用户角色
     * @param id
     * @return
     */
    public int getCompetorRole(Integer id) {
        return competorMapper.getCompetorRole(id);
    }

    /**
     * 获取所有用户id
     * @return
     */
    public List getAllCompetroID() {
        return competorMapper.selectAllcompetorId();
    }
}
