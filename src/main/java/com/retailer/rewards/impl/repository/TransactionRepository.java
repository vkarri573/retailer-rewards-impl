package com.retailer.rewards.impl.repository;

import com.retailer.rewards.impl.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("SELECT tr from Transaction tr WHERE tr.custId = ?1 ")
    List<Transaction> getAllTrans(Long custId);

    @Query("SELECT distinct tr.custId from Transaction tr")
    List<Long> getCustIds();

    List<Transaction> findByCustId(Long custId);
}
