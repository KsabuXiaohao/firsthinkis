package com.thinkis.common.web;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result implements Serializable{
    private int code;
    private String msg;
    private long count;
    private Object data;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
		return msg;
	}

	public Result setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public long getCount() {
		return count;
	}

	public Result setCount(long count) {
		this.count = count;
		return this;
	}

	public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
