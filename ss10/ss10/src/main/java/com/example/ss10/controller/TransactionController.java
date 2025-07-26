package com.example.ss10.controller;

import com.example.ss10.exception.BadRequestException;
import com.example.ss10.exception.NotFoundException;
import com.example.ss10.model.dto.response.DataResponse;
import com.example.ss10.model.entity.Transaction;
import com.example.ss10.model.entity.TransferRequest;
import com.example.ss10.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<DataResponse<Transaction>> transferMoney(@RequestBody TransferRequest request)
            throws NotFoundException, BadRequestException {
        DataResponse<Transaction> response = transactionService.transferMoney(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount(),
                request.getNote()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}