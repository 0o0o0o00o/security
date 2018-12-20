package cn.wangjie.security.enums;


public enum CommonResponseEnum implements ResponseEnum {

    UNAUTHORIZED("true", "需要认证", "301"),
    SUCCESS("true", "成功", "200"),
    FAIL("true", "失败", "500"),
    NEED_SIGN_UP("true", "首次登录请注册或绑定该平台账号", "200");



    private String success;
    private String message;
    private String code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getSuccess() {
        return success;
    }

    @Override
    public String getCode() {
        return code;
    }

    CommonResponseEnum(String success, String message, String code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

}
