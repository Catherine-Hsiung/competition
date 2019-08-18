package com.xxq.competition.entity;

import lombok.Data;

@Data
public class Result {
    private int id ;
    private int result ;//"答题结果,1，答对 2，答错 3 放弃"
    private long startTmime ;// "题目开始时间"
    private long takeTime ;// "答题花费时间"
    private int competorId ;// "参赛人员信息表id"
    private int qbankId ;// "题库id"
    private int turnIndex;//"题目轮次"
}
