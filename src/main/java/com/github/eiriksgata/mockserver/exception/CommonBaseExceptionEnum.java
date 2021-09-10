package com.github.eiriksgata.mockserver.exception;

import lombok.Getter;

/**
 * 统一异常码
 *
 * author Snake
 * date 2019/11/14
 */
@Getter
public enum CommonBaseExceptionEnum {

    //设备业务错误
    DEVICE_DEL_FACE_ERROR(20201, "设备删除人脸失败"),
    DEVICE_ADD_FACE_ERROR(20202, "设备增加人脸失败"),
    DEVICE_CARD_ERROR(20203, "设备更新卡数据错误"),
    DEVICE_OPEN_DOOR_ERROR(20204, "设备开门失败"),

    //websocket error
    SOCKET_CLOSE_ERR(30101,"WebSocket close error"),


    DEVICE_FACE_PICTURE_MAX_ERROR(20204, "图片数据过大"),

    //解析错误
    DEVICE_DATA_PARSING_ERR(10100, "Device data parsing error"),

    //请求
    DEVICE_TCP_LINK_ERR(10101, "Connection device timeout"),

    DEVICE_REGISTER_AUTHORIZATION_LENGTH_ERR(30001, "The length of data submitted by device registration is incorrect"),

    AUTH_INPUT_DIEVCE_CODE_ERR(30500, "Credential registration failed. The custom encoding of the device is incorrect"),

    AUTH_ID_NOT_FOUNT(30501, "Registration credential id not found"),

    AUTH_COMMNUTITY_NUMBER_NOT_FOUNT(30502, "Cell number not found"),

    //plateform-server
    DEVICE_ID_NOT_FOUNT_ERR(30601, "The corresponding device information was not found"),

    SUCCESS(0, "Operation Success"),

    ERROR(-1, "System Error"),

    UNKNOWN(-2, "Unknown Exception"),

    UPLOAD(1, "Upload File Stored Error");

    private final Integer errCode;
    private final String errMsg;

    /**
     * @param errCode 业务模块(2bit) + 异常分类（2bit）+ 自定义编号（2bit）
     * @param errMsg  异常消息
     */
    CommonBaseExceptionEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * find com.ajb.sdk.exception enum by err code
     *
     * @param errCode error code
     * @return com.ajb.sdk.exception enum
     */
    public static CommonBaseExceptionEnum getExceptionEnumByCode(Integer errCode) {
        for (CommonBaseExceptionEnum commonBaseExceptionEnum : CommonBaseExceptionEnum.values()) {
            if (commonBaseExceptionEnum.getErrCode().equals(errCode)) {
                return commonBaseExceptionEnum;
            }
        }
        return UNKNOWN;
    }
}
