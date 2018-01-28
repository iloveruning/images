package com.cll.images.utils;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/1/5
 */
public class Result implements Serializable{

    private  boolean status;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(boolean status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("Result{status=%s, msg='%s', data=%s}", status, msg, data);
    }
}
