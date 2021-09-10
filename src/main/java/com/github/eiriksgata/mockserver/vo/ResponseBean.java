package com.github.eiriksgata.mockserver.vo;

import com.github.eiriksgata.mockserver.exception.CommonBaseExceptionEnum;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 接口返回统一消息
 *
 * author Snake
 * date 2019/06/04
 */
@Data
public class ResponseBean<T> implements Serializable {
    private static final long serialVersionUID = 3022008874106054655L;

    /**
     * 业务响应编号
     */
    private Integer code;

    /**
     * 业务消息
     */
    private String message;

    /**
     * 业务数据
     */
    @Nullable
    private T data;

    private ResponseBean(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseBean(CommonBaseExceptionEnum commonBaseExceptionEnum) {
        this.code = commonBaseExceptionEnum.getErrCode();
        this.message = commonBaseExceptionEnum.getErrMsg();
    }

    private ResponseBean(CommonBaseExceptionEnum commonBaseExceptionEnum, T data) {
        this.code = commonBaseExceptionEnum.getErrCode();
        this.message = commonBaseExceptionEnum.getErrMsg();
        this.data = data;
    }

    private ResponseBean(CommonBaseExceptionEnum commonBaseExceptionEnum, String message, T data) {
        this.code = commonBaseExceptionEnum.getErrCode();
        this.message = message;
        this.data = data;
    }

    public static ResponseBean<Object> success() {
        return new ResponseBean<>(CommonBaseExceptionEnum.SUCCESS);
    }

    public static <T> ResponseBean<T> success(T data) {
        return new ResponseBean<>(CommonBaseExceptionEnum.SUCCESS, data);
    }

    public static <T> ResponseBean<T> success(String message, T data) {
        return new ResponseBean<>(CommonBaseExceptionEnum.SUCCESS, message, data);
    }

    public static ResponseBean<Object> error(Integer code, String message) {
        return new ResponseBean<>(code, message);
    }

    public static <T> ResponseBean<T> error(T data) {
        return new ResponseBean<>(CommonBaseExceptionEnum.ERROR, data);
    }

    public static ResponseBean<Object> error(CommonBaseExceptionEnum commonBaseExceptionEnum) {
        return new ResponseBean<>(commonBaseExceptionEnum);
    }

    public static ResponseBean<String> error(
            CommonBaseExceptionEnum commonBaseExceptionEnum, String message) {
        return new ResponseBean<>(commonBaseExceptionEnum, message);
    }
}
