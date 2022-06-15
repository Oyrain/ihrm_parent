package com.ihrm.common.handle;

import com.ihrm.common.entity.Result;
import com.ihrm.common.exception.CommonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class BaseExceptionHandle {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result error(HttpServletRequest request, HttpServletResponse response,Exception e){
            e.printStackTrace();
            if(e.getClass() == CommonException.class){
                CommonException exception = (CommonException)e;
                return new Result(exception.getResultCode());
            }else {
                return Result.ERROR();
            }
    }
}
