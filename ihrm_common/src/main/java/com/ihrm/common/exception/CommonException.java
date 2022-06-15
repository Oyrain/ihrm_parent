package com.ihrm.common.exception;

import com.ihrm.common.entity.ResultCode;
import lombok.Getter;

/**
 * 自定义异常处理
 */
@Getter
public class CommonException extends RuntimeException {

    private ResultCode  resultCode;

    public CommonException(){}

    public CommonException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
}
