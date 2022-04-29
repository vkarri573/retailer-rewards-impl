package com.retailer.rewards.impl.service;

import com.retailer.rewards.impl.model.CustomerRewardsDto;
import com.retailer.rewards.impl.model.MonthlyRewardsDto;
import com.retailer.rewards.impl.model.Transaction;
import com.retailer.rewards.impl.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class RewardsService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService transService;

    public CustomerRewardsDto getCustTotalRewardPoints(Long custId) {
        List<Transaction> allCustTrans = transService.getAllTrans(custId);
       CustomerRewardsDto customerRewardsDtls =  CustomerRewardsDto.builder()
                .custId(custId)
                .custName(allCustTrans.get(0).getCustName())
                .totalRewards(calculateRewards(allCustTrans)).build();
        return customerRewardsDtls;
    }

    public MonthlyRewardsDto getCustMonthlyRewardPoints(Long custId) {
        List<Transaction> allCustTrans = transService.getAllTrans(custId);

        MonthlyRewardsDto monthlyRewardsDto = MonthlyRewardsDto.builder()
                .custId(custId)
                .custName(allCustTrans.get(0).getCustName())
                .monthlyRewards(new HashMap<>()).build();

        Long totalRewards = 0l;
        for(Map.Entry<Integer, List<Transaction>> monthReport : groupMonthlyTrans(allCustTrans).entrySet()) {
            Long monthlyTotalRewards = calculateRewards(monthReport.getValue());
            monthlyRewardsDto.getMonthlyRewards().put(Month.of(monthReport.getKey()).toString(), monthlyTotalRewards);
            totalRewards = totalRewards + monthlyTotalRewards;
        }

        monthlyRewardsDto.setTotalRewards(totalRewards);
        return monthlyRewardsDto;
    }

    public List<CustomerRewardsDto> fetchAllCustTotalRewardPts() {
        List<Long> custIdList = transService.getCustIds();
        List<CustomerRewardsDto> allCustRewardsList = new ArrayList<>();

        custIdList.forEach(custId -> allCustRewardsList.add(getCustTotalRewardPoints(custId)));

        return allCustRewardsList;
    }

    public List<MonthlyRewardsDto> fetchAllCustMonthlyRewardReport() {
        List<Long> custIdList = transService.getCustIds();
        List<MonthlyRewardsDto> allCustMonthlyRewardsList = new ArrayList<>();

        custIdList.forEach(custId -> allCustMonthlyRewardsList.add(getCustMonthlyRewardPoints(custId)));
        return allCustMonthlyRewardsList;
    }

    private Map<Long, List<Transaction>> grpTransactionsByCust( List<Transaction> allTransList) {
        Map<Long, List<Transaction>> custTransMap = new HashMap<>();

        allTransList.forEach(trans -> {
            long custId = trans.getCustId();

            if(custTransMap.containsKey(custId))
                custTransMap.get(custId).add(trans);
            else
                custTransMap.put(custId, new ArrayList<>(Arrays.asList(trans)));
        });
        return custTransMap;
    }

    private Map<Integer, List<Transaction>> groupMonthlyTrans(List<Transaction> allCustTrans) {
        Map<Integer, List<Transaction>> monthlyMap = new HashMap<>();

        allCustTrans.forEach(trans -> {
            int month = trans.getDate().getMonthValue();

            if(monthlyMap.containsKey(month))
                monthlyMap.get(month).add(trans);
            else
                monthlyMap.put(month, new ArrayList<>(Arrays.asList(trans)));
        });
        return monthlyMap;
    }

    private Long calculateRewards(List<Transaction> transList) {
        return transList.stream().mapToLong(t -> Math.round(calculateRewards(t.getAmount()))).sum();
    }

    private Double calculateRewards(Double transAmt) {
        if(transAmt > 50 && transAmt <= 100) {
            return transAmt - 50;
        } else if(transAmt > 100) {
            return (2*(transAmt-100) + 50);
        } else
            return 0d;
    }
}
