package com.thinkis.common.web;

import org.apache.poi.ss.formula.functions.T;

import com.thinkis.common.persistence.Page;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
	
    private static final String DEFAULT_SUCCESS_MESSAGE = "保存成功";
    private static final String GET_DATA_SUCCESS_MESSAGE = "获取数据成功";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE);
    }
    
    public static Result genSuccessResult(String message) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(message);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genSuccessResult(String message,Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(message)
                .setData(data);
    }
    
    public static Result genSuccessResult(long count,Object dataList) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(GET_DATA_SUCCESS_MESSAGE)
                .setCount(count)
                .setData(dataList);
    }
    
    public static Result genSuccessResult(String message,Page<T> page) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(message)
                .setCount(page.getCount())
                .setData(page.getList());
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMsg(message);
    }
}
