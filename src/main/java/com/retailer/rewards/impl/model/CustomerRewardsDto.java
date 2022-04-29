package com.retailer.rewards.impl.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRewardsDto {
  private Long custId;
  private String custName;
  private Long totalRewards;
}
