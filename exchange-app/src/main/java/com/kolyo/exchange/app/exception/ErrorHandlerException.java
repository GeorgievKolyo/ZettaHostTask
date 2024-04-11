package com.kolyo.exchange.app.exception;

import com.kolyo.exchange.app.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidDataException.class})
    protected ResponseEntity<ErrorDTO> handleConflict(InvalidDataException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Invalid data: " + ex.getMessage());
        errorDTO.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        return new ResponseEntity<>(errorDTO,HttpStatus.NOT_ACCEPTABLE );
    }

    @ExceptionHandler(value = {InvalidCurrencyException.class})
    protected ResponseEntity<ErrorDTO> handleBadRequest(InvalidCurrencyException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Invalid currency: " + ex.getMessage());
        errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(value = {ExchangeNotFoundException.class})
    protected ResponseEntity<ErrorDTO> handleNotFoundApi(ExchangeNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Api not found: " + ex.getMessage());
        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDTO,HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(value = {TransactionNotFoundException.class})
    protected ResponseEntity<ErrorDTO> handleNotFoundTransaction(TransactionNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Not found: " + ex.getMessage());
        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDTO,HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(value = {RateLimitExceededException.class})
    protected ResponseEntity<ErrorDTO> handleRateLimit(RateLimitExceededException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Rate limit: " + ex.getMessage());
        errorDTO.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDTO,HttpStatus.CONFLICT );
    }
}
