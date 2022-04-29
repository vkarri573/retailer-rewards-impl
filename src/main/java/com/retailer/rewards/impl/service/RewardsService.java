package com.retailer.rewards.impl.service;

import com.retailer.rewards.impl.model.CustomerRewardsDto;
import com.retailer.rewards.impl.model.MonthlyRewardsDto;
import com.retailer.rewards.impl.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

/**
 * Service layer to provide business logic for rewards API.
 */
@Service
@Slf4j
public class RewardsService {

    @Autowired
    private TransactionService transService;

    /**
     * Get customer total reward points.
     *
     * @param custId
     * @return customer reward details.
     */
    public CustomerRewardsDto getCustTotalRewardPoints(Long custId) {
        List<Transaction> allCustTrans = transService.getAllTrans(custId);
        log.debug("Total no. of customer transactions: {}", allCustTrans.size());

        CustomerRewardsDto customerRewardsDtls = CustomerRewardsDto.builder()
                .custId(custId)
                .custName(allCustTrans.get(0).getCustName())
                .totalRewards(calculateRewards(allCustTrans)).build();
        return customerRewardsDtls;
    }

    /**
     * Get customer monthly reward points.
     *
     * @param custId
     * @return customer monthly reward points.
     */
    public MonthlyRewardsDto getCustMonthlyRewardPoints(Long custId) {
        List<Transaction> allCustTrans = transService.getAllTrans(custId);
        log.debug("Total no. of customer transactions: {}", allCustTrans.size());

        MonthlyRewardsDto monthlyRewardsDto = MonthlyRewardsDto.builder()
                .custId(custId)
                .custName(allCustTrans.get(0).getCustName())
                .monthlyRewards(new HashMap<>()).build();

        Long totalRewards = 0L;
        for (Map.Entry<Integer, List<Transaction>> monthReport : groupMonthlyTrans(allCustTrans).entrySet()) {
            Long monthlyTotalRewards = calculateRewards(monthReport.getValue());
            monthlyRewardsDto.getMonthlyRewards().put(Month.of(monthReport.getKey()).toString(), monthlyTotalRewards);
            totalRewards = totalRewards + monthlyTotalRewards;
        }

        monthlyRewardsDto.setTotalRewards(totalRewards);
        return monthlyRewardsDto;
    }

    /**
     * Fetch all customers total rewards.
     *
     * @return list of all customers total reward points.
     */
    public List<CustomerRewardsDto> fetchAllCustTotalRewardPts() {
        List<Long> custIdList = transService.getCustIds();
        List<CustomerRewardsDto> allCustRewardsList = new ArrayList<>();

        custIdList.forEach(custId -> allCustRewardsList.add(getCustTotalRewardPoints(custId)));

        return allCustRewardsList;
    }

    /**
     * Fetch all customers monthly reward points.
     *
     * @return list of all customers monthly reward points.
     */
    public List<MonthlyRewardsDto> fetchAllCustMonthlyRewardReport() {
        List<Long> custIdList = transService.getCustIds();
        List<MonthlyRewardsDto> allCustMonthlyRewardsList = new ArrayList<>();

        custIdList.forEach(custId -> allCustMonthlyRewardsList.add(getCustMonthlyRewardPoints(custId)));
        return allCustMonthlyRewardsList;
    }

    /**
     * Utility method to group monthly transactions.
     *
     * @param allCustTrans
     * @return map of month and corresponding transactions.
     */
    private Map<Integer, List<Transaction>> groupMonthlyTrans(List<Transaction> allCustTrans) {
        Map<Integer, List<Transaction>> monthlyMap = new HashMap<>();

        allCustTrans.forEach(trans -> {
            int month = trans.getDate().getMonthValue();

            if (monthlyMap.containsKey(month))
                monthlyMap.get(month).add(trans);
            else
                monthlyMap.put(month, new ArrayList<>(Arrays.asList(trans)));
        });
        return monthlyMap;
    }

    /**
     * Utility method to calculate reward points.
     *
     * @param transList
     * @return reward points.
     */
    private Long calculateRewards(List<Transaction> transList) {
        return transList.stream().mapToLong(t -> Math.round(calculateRewards(t.getAmount()))).sum();
    }

    /**
     * Utility method to calculate reward points.
     *
     * @param transAmt
     * @return reward points.
     */
    private Double calculateRewards(Double transAmt) {
        if (transAmt > 50 && transAmt <= 100) {
            return transAmt - 50;
        } else if (transAmt > 100) {
            return (2 * (transAmt - 100) + 50);
        } else
            return 0d;
    }
}
