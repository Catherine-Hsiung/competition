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
    int selectByTitle(String title);
}
