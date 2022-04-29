package com.retailer.rewards.impl;

import com.retailer.rewards.impl.controller.RewardsController;
import com.retailer.rewards.impl.controller.TransactionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RetailerRewardsImplApplicationTests {

	@Autowired
	private RewardsController rewardsController;

	@Autowired
	private TransactionController transactionController;

	@Test
	void contextLoads() {
		assertThat(rewardsController).isNotNull();
		assertThat(transactionController).isNotNull();
	}
}
