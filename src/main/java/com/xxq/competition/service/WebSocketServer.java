package com.xxq.competition.service;


import com.alibaba.fastjson.JSONObject;
import com.xxq.competition.entity.Qbank;
import com.xxq.competition.entity.Result;
import com.xxq.competition.mapper.ResultMapper;
import com.xxq.competition.utils.Constant;
import com.xxq.competition.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{competorId}")
@Component
@Slf4j
public class WebSocketServer {

    ResultMapper resultMapper = (ResultMapper)applicationContext.getBean("resultMapper");;

    HostService hostService = (HostService)applicationContext.getBean("hostService");

    CompetorService competorService = (CompetorService)applicationContext.getBean("competorService");;

    RedisUtil redisUtil = (RedisUtil)applicationContext.getBean("redisUtil");

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
//        resultMapper = (ResultMapper)applicationContext.getBean("resultMapper");
//        hostService = (HostService)applicationContext.getBean("hostService");
//        competorService = (CompetorService)applicationContext.getBean("competorService");
//        redisUtil = (RedisUtil)applicationContext.getBean("redisUtil");
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    private String competorId = "";
    /**
     * key -session id; value-competor id;
     */
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    //存放所有参赛者
    private Set<Integer> allCompetorSet = new HashSet<>();
    //存放按时提交答案参赛者
    private Set<Integer> commitAnswerSet = new HashSet<>();

    public static int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    //建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("competorId") String competorId) {
        //---什么时候可以连接-----
        sessions.add(session);
        map.put(session.getId(), competorId);
        addOnlineCount(); //在线人数+1
        this.competorId = competorId;
        log.info(competorId + "建立连接，" + "当前在线人数为：" + getOnlineCount());
        //---什么时候推送---
        try {
            session.getBasicRemote().sendText("jkk");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 退出，关闭连接
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        //在线人数-1
        subOnlineCount();
        map.remove(session.getId());
        log.info("当前在线人数为：" + getOnlineCount());

    }

    /**
     * @return
     * @throws
     * @description: 接收参赛者提交的答案, 以及当前题目相关信息
     * @author xxq
     * @date 2019/8/18
     * @time 23:10
     * @Param
     */
    @OnMessage
    public void onMessage(String answer, Session session) {
        //提交答案时间
        log.info("answer:"+answer);
        JSONObject object = JSONObject.parseObject(answer).getJSONObject("msg");
        Integer qb = object.getInteger("qbankId");
        if (qb == null) {
            log.error("未知题目。。。。");
            return;
        }
        String as = object.getString("result");
        if (StringUtils.isEmpty(as)) {
            log.error("答案错误。。。。");
            return;
        }
        if (!qb.equals(HostService.getCurrentQuestion().getId())) {
            log.error("未知错误2。。。。");
            return;
        }
        Result result = new Result();
        Long endtime = System.currentTimeMillis();
        if (!hostService.isCurrentQuestionFlag()) {
            result.setTakeTime(endtime - HostService.getBeginTime());
            if (as.equals(HostService.getCurrentQuestion().getRightAnswer())) {
                result.setScore(1);
            }
            result.setResult(as);
        }else {
            log.info("答案提交超时"+answer);
            return;
        }
        result.setQbankId(HostService.getCurrentQuestion().getId());
        result.setCompetorId(Integer.valueOf(map.get(session.getId())));
        result.setTurnIndex(HostService.getCurrentQuestion().getTurnId());
        result.setStartTime(HostService.getBeginTime());
        commitAnswerSet.add(Integer.valueOf(map.get(session.getId())));
        redisUtil.set(Constant.REDIS_KEY_PRIFIX+HostService.getCurrentQuestion().getTurnId()+"_"
        +HostService.getCurrentQuestion().getId(),map.get(session.getId()));
        resultMapper.commitResult(result);
    }

    /**
     * 将所有用户id加入队列
     * @return
     */
    public Set addAllCompetorToSet(){
        allCompetorSet.addAll(competorService.getAllCompetroID());
        return allCompetorSet;
    }

    /**
     *
     * @param id
     */
    public void addCompetorToSet(Integer id){
        allCompetorSet.add(id);
    }

    /**
     * 设置未按时提交的参赛用户相关答题信息
     */
    public void uncommitCompetor(){
        Set<Integer> newAllCompetorSet = new HashSet<>();
        newAllCompetorSet.addAll(allCompetorSet);
        newAllCompetorSet.removeAll(commitAnswerSet);
        for (Integer integer : newAllCompetorSet) {
            Result uncommitResult = new Result();
            uncommitResult.setCompetorId(integer);
            uncommitResult.setQbankId(HostService.getCurrentQuestion().getId());
            uncommitResult.setScore(0);
            uncommitResult.setTakeTime(10000);
            uncommitResult.setTurnIndex(HostService.getCurrentQuestion().getTurnId());
            uncommitResult.setResult(Constant.OUT_OF_TIME_RESULT);
        }
    }

    /**
     * 通过 competorId查找sessionId
     * @param competorId
     * @return
     */
    public Session getSessionId(String competorId){
        for (String s : map.keySet()) {
            if (map.get(s).equals(competorId)){
                for (Session session : sessions) {
                    if (session.getId().equals(s)){
                        return session;
                    }
                }
            }
        }
        return null;
        
    }

    /**
     * 群发
     *
     * @param question
     */
    public static void sendGroupMsg(Qbank question) {
        for (Session s : sessions) {
            try {
                s.getBasicRemote().sendObject(question);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                //e.printStackTrace();
                continue;
            }
        }
    }


    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

}
