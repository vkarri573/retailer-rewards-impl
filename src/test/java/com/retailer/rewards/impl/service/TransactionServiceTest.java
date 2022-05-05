package com.retailer.rewards.impl.service;

import com.retailer.rewards.impl.exception.DataNotFoundException;
import com.retailer.rewards.impl.model.Transaction;
import com.retailer.rewards.impl.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transRepo;

    @InjectMocks
    private TransactionService transService;

    private Transaction transaction;
    private List<Transaction> listOfTrans;

    @BeforeEach
    public void setup() {
        transaction = Transaction.builder()
                .id(1L)
                .custId(1L)
                .amount(120.00)
                .custName("Venu")
                .date(LocalDate.now())
                .build();

        listOfTrans = Arrays.asList(transaction);
    }

    @Test
    @DisplayName("Unit test for performTransaction method")
    public void testPerformTransaction() {
        when(transRepo.save(any(Transaction.class))).thenReturn(transaction);
        Transaction savedTrans = transService.performTransaction(transaction);

        assertNotNull(savedTrans, "Returned transaction shouldn't be null");
        assertEquals(1, savedTrans.getId(), "Transaction ID is not as expected");
    }

    @Test
    @DisplayName("Unit test for getAllTransactions method")
    public void testGetAllTransactions() {
        when(transRepo.findAll()).thenReturn(listOfTrans);
        assertEquals(1, transService.getAllTransactions().size(), "Transactions list size should match");
    }

    @Test
    @DisplayName("Unit test for getAllTransactions by customer method")
    public void testGetAllTransByCustomer() {
        when(transRepo.getAllTrans(any(Long.class))).thenReturn(listOfTrans);
        assertEquals(1, transService.getAllTrans(1L).size(), "Transactions list size should match");
    }

    @Test
    @DisplayName("Unit test for getAllTransactions by customer in case of empty results")
    public void test_emptyGetAllTransByCustomer() {
        when(transRepo.getAllTrans(any(Long.class))).thenReturn(null);
        assertThrowsExactly(DataNotFoundException.class, () -> transService.getAllTrans(1l), "Should return DataNotFoundException");
    }

    @Test
    @DisplayName("Unit test for testGetCustIds method")
    public void testGetCustIds() {
        when(transRepo.getCustIds()).thenReturn(List.of(1l, 2l));
        assertEquals(2, transService.getCustIds().size(), "Customer Id list size should match");
    }

    @Test
    @DisplayName("Unit test for testGetCustIds method in case of empty results")
    public void test_emptyGetCustIds() {
        when(transRepo.getCustIds()).thenReturn(null);
        assertThrowsExactly(DataNotFoundException.class, transService::getCustIds, "Should return DataNotFoundException");
    }
}
