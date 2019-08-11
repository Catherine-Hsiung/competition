package com.xxq.competition.mapper;

import com.xxq.competition.entity.Competor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CompetorMapper {
    void insertCompetor(Competor competor);
    void updateCompetor(Competor competor);
}
