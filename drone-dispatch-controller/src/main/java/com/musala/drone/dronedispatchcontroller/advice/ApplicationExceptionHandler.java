package com.musala.drone.dronedispatchcontroller.advice;

import com.musala.drone.dronedispatchcontroller.exception.ClientErrorResponse;
import com.musala.drone.dronedispatchcontroller.exception.ClientException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.musala.drone.dronedispatchcontroller.util.constants.ConstantUtil.FAILED;
import static com.musala.drone.dronedispatchcontroller.util.constants.ConstantUtil.STATUS;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    //add exception Handlers using exception @Exception Handler Annotation to catch Validation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleInValidArgument(MethodArgumentNotValidException ex){
        Map<String, Object> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error->
                errorMap.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> error = new HashMap<>();
        error.put(STATUS, FAILED);
        error.put("TimeStamp", System.currentTimeMillis());
        error.put("errors:", errorMap);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ClientException.class)
    public ResponseEntity <ClientErrorResponse> handleException(ClientException exc){

        //create a Client Error response
        ClientErrorResponse error = new ClientErrorResponse ();

        error.setStatus(FAILED);
        error.setMessage(exc.getMessage());
        error.setTimestamps(System.currentTimeMillis());

        //return entity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add exception handler to catch any exceptions

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ClientErrorResponse> handleAnyException(Exception exc){
        //create  Error response
        ClientErrorResponse error = new ClientErrorResponse ();

        error.setStatus(FAILED);
        error.setMessage(exc.getMessage());
        error.setTimestamps(System.currentTimeMillis());
        //return entity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
