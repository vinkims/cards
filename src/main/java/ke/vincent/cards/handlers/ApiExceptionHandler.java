package ke.vincent.cards.handlers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ke.vincent.cards.exceptions.InvalidInputException;
import ke.vincent.cards.exceptions.NotFoundException;
import ke.vincent.cards.responses.InvalidArgumentResponse;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    
    Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * Handles exceptions due to invalid input
     */
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String message = "Invalid input field value in request body";

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, Object> errors = new HashMap<>();
        if (bindingResult.hasFieldErrors()) {
            for (FieldError err : ex.getBindingResult().getFieldErrors()) {
                String key = err.getField();
                String msg = err.getDefaultMessage();
                errors.put(key, msg);
            }
        }

        if (bindingResult.hasGlobalErrors()) {
            errors.put("generalError", bindingResult.getGlobalError().getDefaultMessage());
        }

        InvalidArgumentResponse exceptionResponse = new InvalidArgumentResponse(new Date().toString(), message, errors, status.value());

        logger.error(String.format("(%s) --[MSG] %s || [ERRORS] %s", status.value(), message, errors));

        return new ResponseEntity<Object>(exceptionResponse, headers, status);
    }

    @ExceptionHandler({ InvalidInputException.class })
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Invalid value provided";
        Map<String, Object> errors = new HashMap<>();
        errors.put(ex.getInvalidField(), ex.getExceptionMsg());

        InvalidArgumentResponse invalidArgumentResp = new InvalidArgumentResponse(new Date().toString(), 
            message, errors, status.value());

        logger.error("{}: [MSG] {} -> {}", status.value(), message, errors);

        return new ResponseEntity<Object>(invalidArgumentResp, new HttpHeaders(), status);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundExceptions(NotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Invalid value provided";
        Map<String, Object> errors = new HashMap<>();
        errors.put(ex.getInvalidField(), ex.getExceptionMsg());

        InvalidArgumentResponse invalidArgumentResp = new InvalidArgumentResponse(new Date().toString(), 
            message, errors, status.value());

        logger.error("{}: [MSG] {} -> {}", status.value(), message, errors);

        return new ResponseEntity<Object>(invalidArgumentResp, new HttpHeaders(), status);
    }
}
