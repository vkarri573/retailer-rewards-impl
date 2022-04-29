package com.retailer.rewards.impl.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MonthlyRewardsDto {
    private Long custId;
    private String custName;
    private Long totalRewards;
    private Map<String, Long> monthlyRewards;
}
