package com.example.common;

import lombok.Data;

@Data
public class Result {
    private String code;
    private String msg;
    private Object data;

    private final String success_code = "200";
    private final String success_msg = "请求成功";
    private final String error_code = "-1";

    public Result success(){
        Result result = new Result();
        result.setCode(success_code);
        result.setMsg(success_msg);
        return result;
    }

    public Result success(Object data){
        Result result = success();
        result.setData(data);
        return result;
    }

    public Result error(String data){
        Result result = new Result();
        result.setCode(error_code);
        result.setMsg(data);
        return result;
    }
}
