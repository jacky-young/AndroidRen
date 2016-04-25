package com.jack.androidren.entitys;

/**
 * Created by jack on 4/12/16.
 */
public class ResponseObject<T> {
    private T result;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public T getResult() {

        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
