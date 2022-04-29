package com.retailer.rewards.impl.model;

import lombok.Builder;
import lombok.Data;

/**
 * DTO to represent Customer reward points.
 */
@Data
@Builder
public class CustomerRewardsDto {
    private Long custId;
    private String custName;
    private Long totalRewards;
}
