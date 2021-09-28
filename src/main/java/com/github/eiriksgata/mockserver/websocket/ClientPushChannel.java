package com.github.eiriksgata.mockserver.websocket;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.mockserver.exception.CommonBaseException;
import com.github.eiriksgata.mockserver.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.mockserver.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/push-channel", configurator = GetHttpSessionConfigurator.class)
@Slf4j
@Controller
public class ClientPushChannel {

    //新：使用map对象，便于根据userId来获取对应的WebSocket
    public static ConcurrentHashMap<Long, ClientPushChannel> channelList = new ConcurrentHashMap<>();

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private Long id;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //Long为暂留字段
        id = System.currentTimeMillis();
        channelList.put(id, this);
        this.session = session;
        addOnlineCount();
        log.info("a link open , on-lien:{}", getOnlineCount());

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (session == null) return;
        try {
            channelList.remove(id);
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new CommonBaseException(CommonBaseExceptionEnum.SOCKET_CLOSE_ERR);
        } catch (Exception e) {
            log.error(e.toString());
            throw new CommonBaseException(CommonBaseExceptionEnum.SOCKET_CLOSE_ERR);
        }
        subOnlineCount();
        log.info("a link close , on-lien:{}", getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        error.printStackTrace();

        log.error("通讯发生错误.操作ID:{},异常原因可能为：nginx的连接时长达到上限，被强迫中断。前端心跳中断的原因。", this.id);
        session.close();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        if (message.equals("")) {
            return;
        }
    }


    /**
     * 实现服务器主动推送
     */
    public void sendMessage(ResponseBean<?> message) {
        try {
            this.session.getBasicRemote().sendText(JSONObject.toJSONString(message));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new CommonBaseException(CommonBaseExceptionEnum.ERROR);
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new CommonBaseException(CommonBaseExceptionEnum.ERROR);
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        ClientPushChannel.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        ClientPushChannel.onlineCount--;
    }

}
