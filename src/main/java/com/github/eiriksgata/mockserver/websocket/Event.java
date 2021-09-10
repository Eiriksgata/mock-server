package com.github.eiriksgata.mockserver.websocket;

import lombok.Data;

/**
 * 事件消息
 * <p>
 * author Snake
 * date 20/06/12
 */
@Data
public class Event<T> {

    /**
     * 事件ID
     */
    private String eventId;

    /**
     * 编号
     */
    private Integer code;

    /**
     * 名称
     */
    private String event;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 发送者标识
     */
    private String sender;

    /**
     * 接收者标识
     */
    private String recipient;

    /**
     * 事件数据
     */
    private T data;

    private Long timestamp = System.currentTimeMillis();

    public Event() {
    }

    public Event(EventEnum eventEnum) {
        this.code = eventEnum.getCode();
        this.event = eventEnum.getEvent();
        this.description = eventEnum.getDescription();
    }
}
