package com.chinesecz.common.response;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String info;
    private T data;
}
