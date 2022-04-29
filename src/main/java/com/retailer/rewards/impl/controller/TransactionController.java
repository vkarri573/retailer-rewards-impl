package com.retailer.rewards.impl.controller;

import com.retailer.rewards.impl.model.Transaction;
import com.retailer.rewards.impl.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public Transaction performTransaction(@Valid @RequestBody Transaction transaction) {
        return service.performTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTrans() {
        return service.getAllTransactions();
    }

    @GetMapping("/customers/{id}")
    public List<Transaction> getAllTrans(@PathVariable("id") @NotNull Long custId) {
        return service.getAllTrans(custId);
    }
}
