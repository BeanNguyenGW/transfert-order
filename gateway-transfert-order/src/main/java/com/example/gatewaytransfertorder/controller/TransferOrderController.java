package com.example.gatewaytransfertorder.controller;

import com.example.gatewaytransfertorder.model.TransferOrder;
import com.example.gatewaytransfertorder.request.TransferOrderRequest;
import com.example.gatewaytransfertorder.service.TransferOrderProcessorService;
import lombok.extern.java.Log;
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
import java.util.logging.Level;

@RestController
@Log
public class TransferOrderController {
    private TransferOrderProcessorService transferOrderProcessorService;

    @Autowired
    public TransferOrderController(TransferOrderProcessorService transferOrderProcessorService) {
        this.transferOrderProcessorService = transferOrderProcessorService;
    }

    @PostMapping("/api/transferOrder/receive")
    public ResponseEntity<String> receiveTransferOrder(@Valid @RequestBody TransferOrderRequest request) {
        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setOrderId(request.getOrderId());
        transferOrder.setSourceUrl("http://localhost:9093/branch/order/transfert");
        transferOrderProcessorService.process(transferOrder);
        log.log(Level.INFO, "Receive transfert order request", request);
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
