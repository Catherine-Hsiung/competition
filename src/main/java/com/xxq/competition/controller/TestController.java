package com.xxq.competition.controller;

import com.xxq.competition.entity.Competor;
import com.xxq.competition.mapper.CompetorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    CompetorMapper competorMapper;

    @RequestMapping("/getuserinfo")
    public Map getUserInfo(String name,Integer id){
        Competor competor = new Competor();
        if (name == null){
            return null;
        }
        System.out.println("request id:"+name);
        //competor.setPhigh(168.8f);
        competor.setPName("lgg");
        competor.setPAge(30);
        competor.setPId(id);
        competor.setPSex(0);
        competor.setPPhone("1332342133");
        competor.setPEmail("lgg@qq.com");
        competor.setPType(1);
        //competorMapper.getCount();
//        try{
//            competorMapper.insertCompetitorInfo(competor);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return competorMapper.selectByPrimaryKey(id);

    }
    @RequestMapping("/getmyinfo")
    public Competor getMyInfo(Integer id){
        Competor competor = new Competor();
        if (id == null){
            return null;
        }
        System.out.println("request id:"+id);
        competor.setPId(123);
        competor.setPName("xxq");
        competor.setPAge(22);
        //competorMapper.getCount();
        competorMapper.selectByPrimaryKey(1);
        return competor;
    }
}
