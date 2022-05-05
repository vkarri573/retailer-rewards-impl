package com.retailer.rewards.impl.service;

import com.retailer.rewards.impl.model.CustomerRewardsDto;
import com.retailer.rewards.impl.model.MonthlyRewardsDto;
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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

    @Mock
    private TransactionService transService;

    @Mock
    private TransactionRepository transRepo;

    @InjectMocks
    private RewardsService rewardsService;

    private List<Transaction> CUST1_TRANS_LIST;
    private List<Transaction> CUST2_TRANS_LIST;
    private List<Transaction> ALL_CUST_TRANS_LIST;

    @BeforeEach
    public void setup() {
        Transaction transaction_1 = Transaction.builder()
                .id(1L)
                .custId(1L)
                .amount(120.00)
                .custName("Venu")
                .date(LocalDate.parse("2022-04-28"))
                .build();

        Transaction transaction_2 = new Transaction(2L, 1L, "Venu", 200.90, LocalDate.parse("2022-04-27"));
        Transaction transaction_3 = new Transaction(3L, 1L, "Venu", 315.45, LocalDate.parse("2022-03-26"));

        Transaction transaction_4 = new Transaction(4L, 2L, "Babu", 48.00, LocalDate.parse("2022-04-27"));
        Transaction transaction_5 = new Transaction(5L, 2L, "Babu", 97.56, LocalDate.parse("2022-03-26"));
        Transaction transaction_6 = new Transaction(6L, 2L, "Babu", 765.04, LocalDate.parse("2022-03-26"));

        CUST1_TRANS_LIST = Arrays.asList(transaction_1, transaction_2, transaction_3);
        CUST2_TRANS_LIST = Arrays.asList(transaction_4, transaction_5, transaction_6);

        ALL_CUST_TRANS_LIST = Stream.concat(CUST1_TRANS_LIST.stream(), CUST2_TRANS_LIST.stream()).collect(Collectors.toList());
    }

    @Test
    @DisplayName("Unit test for getCustTotalRewardPoints method")
    public void testGetCustTotalRewardPoints() {
        when(transService.getAllTrans(any(Long.class))).thenReturn(CUST1_TRANS_LIST);
        CustomerRewardsDto customerRewardsDtls = rewardsService.getCustTotalRewardPoints(1l);

        assertEquals(1, customerRewardsDtls.getCustId(), "Cust id should be as expected");
        assertEquals("Venu", customerRewardsDtls.getCustName(), "Cust name should be as expected");
        assertEquals(823, customerRewardsDtls.getTotalRewards(), "Total rewards should match");
    }

    @Test
    @DisplayName("Unit test for getCustTotalRewardPoints method in case of empty results")
    public void test_emptyGetCustTotalRewardPoints() {
        //when(transRepo.getAllTrans(any(Long.class))).thenReturn(null);
        when(transService.getAllTrans(any(Long.class))).thenReturn(List.of());
        assertThrows(Exception.class, () -> rewardsService.getCustTotalRewardPoints(1l), "Should return an exception");
    }

    @Test
    @DisplayName("Unit test for getCustMonthlyRewardPoints method")
    public void testGetCustMonthlyRewardPoints() {
        when(transService.getAllTrans(any(Long.class))).thenReturn(CUST1_TRANS_LIST);
        MonthlyRewardsDto monthlyRewardsDto = rewardsService.getCustMonthlyRewardPoints(1l);

        assertEquals(1, monthlyRewardsDto.getCustId(), "Cust id should be as expected");
        assertEquals("Venu", monthlyRewardsDto.getCustName(), "Cust name should be as expected");
        assertEquals(823, monthlyRewardsDto.getTotalRewards(), "Total rewards should match");

        assertThat(monthlyRewardsDto.getMonthlyRewards()).isNotEmpty().containsAllEntriesOf(Map.of("MARCH", 481L, "APRIL", 342L));
    }

    @Test
    @DisplayName("Unit test for fetchAllCustTotalRewardPts method")
    public void testFetchAllCustTotalRewardPts() {
        when(transService.getCustIds()).thenReturn(List.of(1l, 2l));
        when(transService.getAllTrans(1l)).thenReturn(CUST1_TRANS_LIST);
        when(transService.getAllTrans(2l)).thenReturn(CUST2_TRANS_LIST);

        List<CustomerRewardsDto> allCustRewardsList = rewardsService.fetchAllCustTotalRewardPts();

        assertEquals(2, allCustRewardsList.size(), "Size should be zero");
        assertEquals(823, allCustRewardsList.get(0).getTotalRewards(), "Total rewards should match");
        assertEquals(1428, allCustRewardsList.get(1).getTotalRewards(), "Total rewards should match");
    }

    @Test
    @DisplayName("Unit test for fetchAllCustMonthlyRewardReport method")
    public void testFetchAllCustMonthlyRewardReport() {
        when(transService.getCustIds()).thenReturn(List.of(1l, 2l));
        when(transService.getAllTrans(1l)).thenReturn(CUST1_TRANS_LIST);
        when(transService.getAllTrans(2l)).thenReturn(CUST2_TRANS_LIST);

        List<MonthlyRewardsDto> allCustMonthlyRewardsList = rewardsService.fetchAllCustMonthlyRewardReport();

        assertThat(allCustMonthlyRewardsList.get(1).getMonthlyRewards()).isNotEmpty().containsAllEntriesOf(Map.of("MARCH", 1428L, "APRIL", 0L));
    }
}
