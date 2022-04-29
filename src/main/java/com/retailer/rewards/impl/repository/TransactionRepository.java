package com.retailer.rewards.impl.repository;

import com.retailer.rewards.impl.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides CRUD transactions over Transaction table.
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    /**
     * Method to get all transactions.
     *
     * @param custId
     * @return all customer transactions.
     */
    @Query("SELECT tr from Transaction tr WHERE tr.custId = ?1 ")
    List<Transaction> getAllTrans(Long custId);

    /**
     * Method to get customer ids.
     *
     * @return list of customer ids.
     */
    @Query("SELECT distinct tr.custId from Transaction tr")
    List<Long> getCustIds();

    /**
     * Method to get customer transactions.
     *
     * @param custId
     * @return all customer transactions.
     */
    List<Transaction> findByCustId(Long custId);
}
