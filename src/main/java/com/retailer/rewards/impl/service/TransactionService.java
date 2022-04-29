package com.retailer.rewards.impl.service;

import com.retailer.rewards.impl.exception.DataNotFoundException;
import com.retailer.rewards.impl.model.Transaction;
import com.retailer.rewards.impl.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer to provide business logic for transaction API.
 */
@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    /**
     * Method to perform transaction.
     *
     * @param transaction
     * @return transaction.
     */
    public Transaction performTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    /**
     * Method to get all transactions.
     *
     * @return list of transactions.
     */
    public List<Transaction> getAllTransactions() {
        List<Transaction> allTrans = new ArrayList<>();
        repository.findAll().forEach(trans -> allTrans.add(trans));
        return allTrans;
    }

    /**
     * Method to get all customer transactions.
     *
     * @param custId
     * @return list of customer transactions.
     */
    public List<Transaction> getAllTrans(Long custId) {
        List<Transaction> allTransList = repository.getAllTrans(custId);
        if (allTransList == null || allTransList.isEmpty())
            throw new DataNotFoundException("No Transactions found for customer");
        return allTransList;
    }

    /**
     * Method to get all customer ids.
     *
     * @return list of customer ids.
     */
    public List<Long> getCustIds() {
        List<Long> custIdList = repository.getCustIds();
        if (custIdList == null || custIdList.isEmpty())
            throw new DataNotFoundException("No Transactions found");
        return custIdList;
    }
}
