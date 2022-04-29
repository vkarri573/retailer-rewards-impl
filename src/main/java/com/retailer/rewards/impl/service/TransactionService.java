package com.retailer.rewards.impl.service;

import com.retailer.rewards.impl.exception.DataNotFoundException;
import com.retailer.rewards.impl.model.Transaction;
import com.retailer.rewards.impl.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public Transaction performTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> allTrans = new ArrayList<>();
        repository.findAll().forEach(trans -> allTrans.add(trans));
        return allTrans;
    }

    public List<Transaction> getAllTrans(Long custId) {
        List<Transaction> allTransList = repository.getAllTrans(custId);
        if(allTransList == null || allTransList.isEmpty())
            throw new DataNotFoundException("No Transactions found for customer");
        return allTransList;
    }

    public List<Long> getCustIds() {
        List<Long> custIdList = repository.getCustIds();
        if(custIdList == null || custIdList.isEmpty())
            throw new DataNotFoundException("No Transactions found");
        return custIdList;
    }
}
