package com.thertno.xiaohuli.bean;

/**
 * 返回结果Bean
 * 
 * @author zhanjie.zhang
 * @date 2016-1-5
 * 
 * @author chenchao
 * @date 2016-05-20
 *       <p>
 *       1、去除多余类的引用<br>
 *       2、去除成员变量的默认属性
 */
public class ResponseResult<T> {

    /**
     * 成功：0 失败：其它
     */
    private int code;
    /**
     * 成功："ok" 失败：其它
     */
    private String msg;
    /**
     * 数据体
     */
    private T body;

    public ResponseResult(T body) {
        super();
        this.body = body;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public ResponseResult<T> success() {
        this.code = 0;
        this.msg = "ok";
        return this;
    }

    public ResponseResult<T> fail(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }
}
