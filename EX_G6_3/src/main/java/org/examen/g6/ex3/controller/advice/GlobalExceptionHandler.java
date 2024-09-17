package org.examen.g6.ex3.controller.advice;


import lombok.extern.log4j.Log4j2;
import org.examen.g6.ex3.aggregates.constants.Constants;
import org.examen.g6.ex3.aggregates.response.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.examen.g6.ex3.controller.personalizado.ApiException;

import java.io.IOException;
import java.util.Optional;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UsuarioResponse> controlandoException(Exception exception){
        UsuarioResponse response = new UsuarioResponse();
        response.setCode(Constants.ERROR_TRX_CODE);
        response.setMessage(Constants.ERROR_TRX_MESS + exception.getMessage());
        response.setData(Optional.empty());
        log.error("EXCEPTION CAPUTRADA EN:. . . controlandoException");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<UsuarioResponse> controlandoIoException(IOException ioException){
        UsuarioResponse response = new UsuarioResponse();
        response.setCode(Constants.ERROR_TRX_CODE);
        response.setMessage(Constants.ERROR_TRX_MESS + ioException.getMessage());
        response.setData(Optional.empty());
        log.error("EXCEPTION CAPUTRADA EN:. . . controlandoIoException");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<UsuarioResponse> controlandoApiException(ApiException apiException){
        UsuarioResponse response = new UsuarioResponse();
        response.setCode(Constants.ERROR_TRX_API_EXCEPTION_CODE);
        response.setMessage(Constants.ERROR_TRX_API_EXCEPTION_MESS + apiException.getMessage());
        response.setData(Optional.empty());
        log.error("EXCEPTION CAPUTRADA EN:. . . controlandoApiException");
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
