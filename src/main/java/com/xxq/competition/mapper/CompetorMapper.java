package com.xxq.competition.mapper;

import com.xxq.competition.entity.Competor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface CompetorMapper {
    int getCount();
    @Select("select * from competor where p_id=#{pid} ")
    Map selectByPrimaryKey (Integer pid);
//    void insertCompetitorInfo (Competor competor);
}
