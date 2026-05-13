package com.cookie.springbootstudyweek08.exception;

import com.cookie.springbootstudyweek08.sms.dto.ApiResult;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ApiResult.error(400, message.isEmpty() ? "参数不合法" : message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleHttpMessageNotReadable() {
        return ApiResult.error(400, "请求体格式错误");
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleHandlerMethodValidation(HandlerMethodValidationException exception) {
        String message = exception.getParameterValidationResults()
                .stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(resolvable -> resolvable.getDefaultMessage())
                .collect(Collectors.joining("; "));

        if (message.isEmpty()) {
            message = exception.getCrossParameterValidationResults()
                    .stream()
                    .map(resolvable -> resolvable.getDefaultMessage())
                .collect(Collectors.joining("; "));
        }
        return ApiResult.error(400, message.isEmpty() ? "参数不合法" : message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleConstraintViolation(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining("; "));
        return ApiResult.error(400, message.isEmpty() ? "参数不合法" : message);
    }
}
