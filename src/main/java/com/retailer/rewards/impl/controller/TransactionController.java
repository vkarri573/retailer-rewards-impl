package com.retailer.rewards.impl.controller;

import com.retailer.rewards.impl.model.Transaction;
import com.retailer.rewards.impl.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Controller to serve below reward transaction requests.
 * <p>
 * 1. Perform transaction.
 * 2. Get all transactions.
 * 3. Get all customer transactions.
 */
@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService service;

    /**
     * Serves request to perform transaction.
     *
     * @param transaction
     * @return transaction
     */
    @PostMapping
    public Transaction performTransaction(@Valid @RequestBody Transaction transaction) {
        log.debug("Perform transaction");
        return service.performTransaction(transaction);
    }

    /**
     * Serves request to get all transactions.
     *
     * @return list of transactions.
     */
    @GetMapping
    public List<Transaction> getAllTrans() {
        return service.getAllTransactions();
    }

    /**
     * Serves request to get all customer transactions.
     *
     * @param custId
     * @return list of customer transactions.
     */
    @GetMapping("/customers/{id}")
    public List<Transaction> getAllTrans(@PathVariable("id") @NotNull Long custId) {
        log.debug("Get all transactions of a customer: {}", custId);
        return service.getAllTrans(custId);
    }
}
