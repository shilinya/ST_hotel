package com.lin.lease.common.exception;

import com.lin.lease.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }
    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result handleException(LeaseException e) {
        e.printStackTrace();
        return Result.fail(e.getCode(),e.getMessage());
    }
}
