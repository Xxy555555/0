package com.example.demo.enums;




public enum MyExcptionEnum {

    OPERATE_FAIL(210001, "操作失败"),
    PASSWORD_IS_NOT_TRUE(210002,"两次密码不一致"),
    USERINFO_EXIST(210003,"当前用户的个人信息已经存在"),
    ADMIN_NOT_PERMISSIONS(210004,"管理员不能上传个人信息"),
    PASSWORD_NOT_TRUE(210005,"密码错误"),
    ADMIN_NOT_CASES(210006,"管理员不能上传个人病例" ),
    /**
     * 验证码
     */
    CODE_GET_ERROR(240001, "验证码获取失败"),
    CODE_LIMITED(240002, "验证码发送过快"),
    CODE_ERROR(240003, "验证码错误"),
    CODE_CAPTCHA_ERROR(240101, "图形验证码错误"),


    /**
     * 账号
     */
    ACCOUNT_REPEAT(250001, "账号已经存在"),
    ACCOUNT_UNREGISTER(250002, "账号不存在"),
    ACCOUNT_PWD_ERROR(250003, "账号或者密码错误"),
    ACCOUNT_UN_LOGIN(250004, "账号未登录"),
    ACCOUNT_DISABLE(250005, "账号已禁用"),


    /**
     * 通用操作码
     */

    OPS_REPEAT(260001, "重复操作"),
    OPS_NETWORK_ADDRESS_ERROR(260002, "网络地址错误"),


    /**
     * 文件相关
     */
    FILE_UPLOAD_USER_IMG_FAIL(270001, "用户文件上传失败");
    private final int code;
    private final String message;
    private MyExcptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }


}
