package cn.wangjie.security.entity;


import cn.wangjie.security.enums.ResponseEnum;
import lombok.Data;



@Data

public class ResponseVO<T> {

    /** 结果状态 */
    private String success;

    /** 结果信息 */
    private String message;

    /** 结果码 */
    private String code;

    /** 返回数据 */
    private T data;

    public ResponseVO() {
    }

    //返回结果不包含数据，返回信息不包含变量
    public ResponseVO(ResponseEnum responseEnum) {
        this.message = responseEnum.getMessage();
        this.success = responseEnum.getSuccess();
        this.code = responseEnum.getCode();
    }
    //返回结果不包含数据，返回信息不包含变量
    public ResponseVO(ResponseEnum responseEnum, String message) {
        this.message = message;
        this.success = responseEnum.getSuccess();
        this.code = responseEnum.getCode();
    }


    //返回结果包含数据，返回信息不包含变量
    public ResponseVO(ResponseEnum responseEnum, T data) {
        this.message = responseEnum.getMessage();
        this.success = responseEnum.getSuccess();
        this.code = responseEnum.getCode();
        this.data = data;
    }



}