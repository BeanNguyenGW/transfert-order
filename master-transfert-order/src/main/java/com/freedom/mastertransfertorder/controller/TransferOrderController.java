package com.freedom.mastertransfertorder.controller;

import com.freedom.mastertransfertorder.request.TransferOrderRequest;
import com.freedom.mastertransfertorder.service.TransferOrderProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TransferOrderController {

    private TransferOrderProcessorService transferOrderProcessorService;

    @Autowired
    public TransferOrderController(TransferOrderProcessorService transferOrderProcessorService) {
        this.transferOrderProcessorService = transferOrderProcessorService;
    }

    @PostMapping("/api/transferOrder/receive")
    public ResponseEntity<String> receiveTransferOrder(@Valid @RequestBody TransferOrderRequest request) {
        transferOrderProcessorService.split(request);
        return new ResponseEntity<>("Sucess", HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
