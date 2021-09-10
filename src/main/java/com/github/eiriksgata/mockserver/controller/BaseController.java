package com.github.eiriksgata.mockserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.mockserver.dto.RequestDataDTO;
import com.github.eiriksgata.mockserver.utils.IpUtil;
import com.github.eiriksgata.mockserver.vo.ResponseBean;
import com.github.eiriksgata.mockserver.websocket.ClientPushChannel;
import com.github.eiriksgata.mockserver.websocket.Event;
import com.github.eiriksgata.mockserver.websocket.EventEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@RestController
@Slf4j
public class BaseController {

    public static String resultJsonData = JSONObject.toJSONString(ResponseBean.success());

    @RequestMapping(value = "/**")
    public JSONObject basePostRequestHandler(HttpServletRequest request) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            sb.append(inputStr);
        }
        if (sb.toString().equals("")) {
            sb.append("{}");
        }
        JSONObject jsonObject = JSON.parseObject(sb.toString());
        log.info("method:{}, request URL:[{}] ,ip:{}", request.getMethod(), request.getRequestURI(), IpUtil.getRequestIp(request));
        log.info("request data:{}", jsonObject.toJSONString());
        RequestDataDTO requestDataDTO = new RequestDataDTO();
        requestDataDTO.setRequestIp(IpUtil.getRequestIp(request));
        requestDataDTO.setMethod(request.getMethod());
        requestDataDTO.setUri(request.getRequestURI());
        requestDataDTO.setRequestBody(jsonObject.toJSONString());
        requestDataDTO.setUrl(request.getRequestURL().toString());
        requestDataDTO.setParameter(request.getQueryString());
        Event<RequestDataDTO> event = new Event<>(EventEnum.PUSH_REQUEST_INFO);
        event.setEventId(UUID.randomUUID().toString());
        event.setSender("server");
        event.setData(requestDataDTO);
        ClientPushChannel.channelList.forEach((key, value) -> {
            value.sendMessage(JSONObject.toJSONString(event));
        });
        return JSON.parseObject(resultJsonData);
    }

}
