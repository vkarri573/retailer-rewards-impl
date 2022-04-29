package com.retailer.rewards.impl.controller;

import com.retailer.rewards.impl.model.CustomerRewardsDto;
import com.retailer.rewards.impl.model.MonthlyRewardsDto;
import com.retailer.rewards.impl.service.RewardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Controller to Serve below Reward requests.
 * <p>
 * 1. Get Customer total reward points.
 * 2. Get customer monthly reward points.
 * 3. Get all customers total reward points.
 * 4. Get all customers monthly reward points.
 */
@RestController
@RequestMapping("/rewards")
@Slf4j
public class RewardsController {

    @Autowired
    private RewardsService service;

    /**
     * Serves request to get Customer total reward points.
     *
     * @param custId
     * @return customer total reward points.
     */
    @GetMapping("/customers/{id}")
    public CustomerRewardsDto getCustomerTotalRewardPoints(@PathVariable("id") @NotNull Long custId) {
        log.debug("Get Total reward points of customer : {}", custId);
        return service.getCustTotalRewardPoints(custId);
    }

    /**
     * Serves request to get customer monthly reward points.
     *
     * @param custId
     * @return customer monthly reward points.
     */
    @GetMapping("/customers/{id}/month")
    public MonthlyRewardsDto getCustomerMonthlyRewardPoints(@PathVariable("id") @NotNull Long custId) {
        log.debug("Get Monthly reward points of customer : {}", custId);
        return service.getCustMonthlyRewardPoints(custId);
    }

    /**
     * Serves request to get all customers total reward points.
     *
     * @return customers total reward points.
     */
    @GetMapping("/customers")
    public List<CustomerRewardsDto> getAllCustomersTotalRewardPts() {
        log.debug("Get all customer total reward points");
        return service.fetchAllCustTotalRewardPts();
    }

    /**
     * Serves request to get all customers monthly reward points.
     *
     * @return all customers monthly reward points.
     */
    @GetMapping("/customers/month")
    public List<MonthlyRewardsDto> fetchAllCustMonthlyRewardReport() {
        log.debug("Get all customers monthly reward points ");
        return service.fetchAllCustMonthlyRewardReport();
    }
}
