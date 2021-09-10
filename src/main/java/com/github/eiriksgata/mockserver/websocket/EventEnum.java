package com.github.eiriksgata.mockserver.websocket;

import lombok.Getter;

/**
 * 事件消息
 * <p>
 * author Snake
 * date 20/05/11
 */
@Getter
public enum EventEnum {

    CLIENT_HEARTBEAT(1000, "心跳信息", ""),
    GET_SERVER_CONFIG(10001, "获取服务配置信息", ""),

    PUSH_REQUEST_INFO(10002, "服务推送API请求信息给客户端", ""),
    CLIENT_SET_RESPONSE_DATA(10003, "客户端响应信息", ""),
    CREATE_API_INTERFACE(11001, "客户端创建监听url API请求", ""),



    UNKNOWN(-1, "unknown", "unknown");




    /**
     * 事件类型
     */
    private final Integer code;
    /**
     * 事件名称
     */
    private final String event;
    /**
     * 事件描述
     */
    private final String description;

    /**
     * @param code        业务模块（2bit）+ 事件类型（2bit）+ 事件编号（2bit）
     * @param event       事件名称（与枚举名相同）
     * @param description 事件描述
     */
    EventEnum(Integer code, String event, String description) {
        this.code = code;
        this.event = event;
        this.description = description;
    }

    public static EventEnum getEventByCode(Integer code) {
        for (EventEnum eventEnum : EventEnum.values()) {
            if (eventEnum.getCode().equals(code)) {
                return eventEnum;
            }
        }
        return UNKNOWN;
    }
}
