package com.example.project.controller;

import com.example.project.exceptions.InputDataException;
import com.example.project.model.ConfirmOperationRequest;
import com.example.project.model.TransferRequest;
import com.example.project.model.TransferResponse;
import com.example.project.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class Controller {
    private final TransferService service;

    @PostMapping("/transfer")
    public TransferResponse transfer (@RequestBody TransferRequest request) throws InputDataException {
        return service.response(request);
    }

    @PostMapping("/confirmOperation")
    public TransferResponse confirmation (@RequestBody ConfirmOperationRequest request) throws InputDataException {
        return service.confirmOperation(request);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler (InputDataException.class)
    public String HandlerInvalidCredentials (InputDataException e) {
        return e.getMessage();
    }
}
