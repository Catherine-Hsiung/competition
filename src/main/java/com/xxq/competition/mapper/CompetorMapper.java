package com.xxq.competition.mapper;

import com.xxq.competition.entity.Competor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CompetorMapper {
    void insertCompetor(Competor competor);
    void updateCompetor(Competor competor);
    Map selectCompetor(Integer id);
    int getCompetorRole(Integer id);
    @Select("select id form competor")
    List<Integer> selectAllcompetorId();
}
