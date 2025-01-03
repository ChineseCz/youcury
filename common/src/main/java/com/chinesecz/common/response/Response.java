package com.chinesecz.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String info;
    private T data;
}
