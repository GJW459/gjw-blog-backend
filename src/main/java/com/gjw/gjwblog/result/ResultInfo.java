package com.gjw.gjwblog.result;

public enum ResultInfo {

    NOT_FOUND("404", "没有找到"),
    SUCCESS("20000", "操作成功");
    String code;
    String message;

    ResultInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
