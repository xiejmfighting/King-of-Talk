package com.kot.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kot.backend.consumer.utils.JwtAuthentication;
import com.kot.backend.mapper.UserMapper;
import com.kot.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    //开一个线程安全，存储所有链接所对应的用户userId：
    private static final ConcurrentHashMap<Integer,WebSocketServer> users=new ConcurrentHashMap<>();
    //开一个线程安全的匹配池
    private static final CopyOnWriteArraySet<User> matchpool=new CopyOnWriteArraySet<>();



    private User user;
    private Session session=null;//维护每次链接

    //注入用户数据库，注意和之前的方式不同：
    private static UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper=userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session=session;
        System.out.println("connected");
        Integer userId= JwtAuthentication.getUserId(token);
        //得到userId之后查询数据库所对应的用户信息
        this.user=userMapper.selectById(userId);

        if (this.user!=null){
            users.put(userId,this);
        }else {
            this.session.close();
        }
        System.out.println(users);

    }

    @OnClose
    public void onClose() {
        // 关闭链接

        System.out.println("disconnected!");
        if (this.user!=null){
            users.remove(this.user.getId());
            matchpool.remove(this.user);
        }

    }

    private void startMatching(){
        System.out.println("start-matching");
        matchpool.add(this.user);

        while (matchpool.size()>=2){
            Iterator<User> it=matchpool.iterator();
            User a=it.next(),b=it.next();
            matchpool.remove(a);
            matchpool.remove(b);

            JSONObject respA=new JSONObject();
            respA.put("event","start-matching");
            respA.put("opponent_username",b.getUsername());
            respA.put("opponent_photo",b.getPhoto());
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB=new JSONObject();
            respB.put("event","start-matching");
            respB.put("opponent_username",a.getUsername());
            respB.put("opponent_photo",a.getPhoto());
            users.get(b.getId()).sendMessage(respB.toJSONString());

        }
    }

    private void stopMatching(){
        System.out.println("stop-matching");
        matchpool.remove(this.user);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息, 前端向后端发送消息
        System.out.println("receive message");
        JSONObject data=JSONObject.parseObject(message);
        //将前端发送的start-matching和stop-matching取出来
        String event= data.getString("event");
        if ("start-matching".equals(event)){
            startMatching();
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //从后端向当前链接的前端发送消息, 每次开一个线程建立一个新的链接,链接用Session维护
    public void sendMessage(String message){
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
