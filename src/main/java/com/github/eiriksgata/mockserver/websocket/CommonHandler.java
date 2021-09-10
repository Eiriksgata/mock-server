package com.github.eiriksgata.mockserver.websocket;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.eiriksgata.mockserver.controller.BaseController;
import com.github.eiriksgata.mockserver.vo.ServerInfoVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonHandler {

    @Value("${server.port}")
    public String serverPort;

    @Value("${mock.server.version}")
    public String version;


    public void verification(ClientPushChannel clientPushChannel, String message) throws Exception {

        Event<String> event = JSONObject.parseObject(message, new TypeReference<Event<String>>() {
        });
        switch (EventEnum.getEventByCode(event.getCode())) {
            case CLIENT_HEARTBEAT:
                return;
            case GET_SERVER_CONFIG:
                clientPushChannel.sendMessage(getServerInfo(event.getEventId()));
                return;
            case CLIENT_SET_RESPONSE_DATA:
                setServerResponseData(event.getData());
                return;

        }
    }

    private void setServerResponseData(String jsonText) {
        BaseController.resultJsonData = jsonText;
    }


    private String getServerInfo(String eventId) {
        Event<ServerInfoVo> event = new Event<>(EventEnum.GET_SERVER_CONFIG);
        event.setEventId(eventId);
        event.setSender("server");
        ServerInfoVo serverInfoVo = new ServerInfoVo();
        serverInfoVo.setServerPort(serverPort);
        serverInfoVo.setVersion(version);
        event.setData(serverInfoVo);
        return JSONObject.toJSONString(event);
    }


}
