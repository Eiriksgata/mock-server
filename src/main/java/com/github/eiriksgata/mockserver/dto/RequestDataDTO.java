package com.github.eiriksgata.mockserver.dto;

import lombok.Data;

@Data
public class RequestDataDTO {

    private String method;
    private String uri;
    private String requestBody;
    private String requestIp;
    private String url;
    private String parameter;
}
