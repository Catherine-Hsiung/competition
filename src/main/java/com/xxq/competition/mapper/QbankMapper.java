package com.xxq.competition.mapper;

import com.xxq.competition.entity.Qbank;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface QbankMapper {
    void insertQuestion(Qbank qbank);
    void updateQuestion(Qbank qbank);
    void updateByTitle(Qbank qbank);
    Map selectQuestion(Integer id);
    void deleteQuestion(Integer id);
    //查询题中某个题目数量
    int selectByTitle(String title);
    //随机选取一个题目
    Qbank selectQusetionRandomly();
    //计算当前轮次选取题目个数
    int calTurnQuestionNum(Integer turnId);

}
