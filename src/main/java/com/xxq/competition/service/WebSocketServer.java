package com.xxq.competition.service;


import com.xxq.competition.entity.Qbank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{competorId}")
@Component
@Slf4j
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<>();

    private String competorId = "";


    public static int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    //推送题目
    public void onOpen(Session session,@PathParam("competorId") String competorId,Qbank qusetion){
        this.session = session;
        //---什么时候可以连接-----
        webSocketSet.add(session);
        addOnlineCount(); //在线人数+1
        this.competorId = competorId;
        log.info(competorId+"建立连接，"+"当前在线人数为："+getOnlineCount());
        //---什么时候推送---
        try {
            sendMessage(qusetion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //推送结果

    public void onClose(){
        webSocketSet.remove(this);
        //在线人数-1
        subOnlineCount();
        log.info("当前在线人数为："+getOnlineCount());
        
    }

    /**
     * @description: 接收参赛者提交的答案
     * @author xxq
     * @date 2019/8/18 
     * @time 23:10
     * @Param 
     * @return 
     * @throws 
    */
    public void onMessage(String answer,Session session){

        for (Session answerSession:webSocketSet
             ) {
            try {
                answerSession.getBasicRemote().sendText(answer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendMessage(Qbank question) throws Exception{
        this.session.getBasicRemote().sendObject(question);
    }


}
