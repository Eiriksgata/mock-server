package com.github.eiriksgata.mockserver.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务统一异常
 *
 * author Snake
 * date 2019/11/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonBaseException extends RuntimeException {

    private Integer errCode;
    private String errMsg;

    public CommonBaseException(String errMsg) {
        this.errCode = CommonBaseExceptionEnum.UNKNOWN.getErrCode();
        this.errMsg = errMsg;
    }

    public CommonBaseException(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CommonBaseException(Integer errCode, String errMsg, Throwable cause) {
        super(cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CommonBaseException(CommonBaseExceptionEnum commonBaseExceptionEnum) {
        this.errCode = commonBaseExceptionEnum.getErrCode();
        this.errMsg = commonBaseExceptionEnum.getErrMsg();
    }

    public CommonBaseException(CommonBaseExceptionEnum commonBaseExceptionEnum, Throwable cause) {
        super(cause);
        this.errCode = commonBaseExceptionEnum.getErrCode();
        this.errMsg = commonBaseExceptionEnum.getErrMsg();
    }
}
