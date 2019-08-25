package com.xxq.competition.mapper;

import com.xxq.competition.entity.RankInfo;
import com.xxq.competition.entity.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ResultMapper {
    void commitResult(Result result);
    //个人总分查询
    int competorScores(Integer competorId);
    //个人分数查询
    int competorTurnScore(@Param("competorId") Integer competorId,@Param("turnIndex") Integer turnIndex);
    //轮次排名
    List<RankInfo> getTurnRank(Integer turnIndex);
    //比赛总结果排名
}
