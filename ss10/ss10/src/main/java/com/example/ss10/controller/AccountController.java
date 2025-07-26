package com.example.ss10.controller;

import com.example.ss10.exception.NotFoundException;
import com.example.ss10.model.dto.response.DataResponse;
import com.example.ss10.model.entity.Account;
import com.example.ss10.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    final public AccountService accountService;

    @PostMapping
    public ResponseEntity<DataResponse<Account>> createAccount(@RequestBody Account request){
        return new  ResponseEntity<>(accountService.createAccount(request), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<Account>> updateAccount(@PathVariable UUID id, @RequestBody Account request) throws NotFoundException{
        return new ResponseEntity<>(accountService.updateAccount(id,request),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id)throws NotFoundException{
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping
    public ResponseEntity<DataResponse<List<Account>>> getAllAccounts(){
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }
    @GetMapping("/cccd")
    public ResponseEntity<DataResponse<Account>> getAccountByCccd(@RequestParam String cccd)throws NotFoundException{
        return new ResponseEntity<>(accountService.getAccountByCccd(cccd),HttpStatus.OK);
    }

}