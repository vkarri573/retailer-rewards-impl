package com.retailer.rewards.impl;

import com.retailer.rewards.impl.controller.RewardsController;
import com.retailer.rewards.impl.model.CustomerRewardsDto;
import com.retailer.rewards.impl.model.MonthlyRewardsDto;
import com.retailer.rewards.impl.service.RewardsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(RewardsController.class)
@Slf4j
public class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService service;

    @Test
    public void testGetCustomerTotalRewardPoints() throws Exception {
        CustomerRewardsDto mockCustomerRewardsRes = buildMockCustomerRewardsResponse();
        when(service.getCustTotalRewardPoints(any(Long.class))).thenReturn(mockCustomerRewardsRes);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rewards/customers/1");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String responsePayload = mvcResult.getResponse().getContentAsString();

        String expectedPayload = "{\n" +
                "    \"custId\": 1,\n" +
                "    \"custName\": \"Venu\",\n" +
                "    \"totalRewards\": 40\n" +
                "}";

        JSONAssert.assertEquals(expectedPayload, responsePayload, false);
    }

    @Test
    public void testGetCustomerMonthlyRewardPoints() throws Exception {
        MonthlyRewardsDto monthlyRewardsDto = buildMockCustomerMonthlyRewardsRes();
        when(service.getCustMonthlyRewardPoints(any(Long.class))).thenReturn(monthlyRewardsDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rewards/customers/1/month");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String responsePayload = mvcResult.getResponse().getContentAsString();

        String expectedPayload = "{\n" +
                "    \"custId\": 1,\n" +
                "    \"custName\": \"Venu\",\n" +
                "    \"totalRewards\": 40,\n" +
                "    \"monthlyRewards\": {\n" +
                "        \"APRIL\": 40\n" +
                "    }\n" +
                "}";

        JSONAssert.assertEquals(expectedPayload, responsePayload, false);
    }

    @Test
    public void testGetAllCustomerTotalRewardPts() throws Exception {
        CustomerRewardsDto mockCustomerRewardsRes = buildMockCustomerRewardsResponse();
        List<CustomerRewardsDto> mockAllCustTotalRwdPtsRes = List.of(mockCustomerRewardsRes);

        when(service.fetchAllCustTotalRewardPts()).thenReturn(mockAllCustTotalRwdPtsRes);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rewards/customers");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String responsePayload = mvcResult.getResponse().getContentAsString();

        String expectedPayload = "[\n" +
                "    {\n" +
                "        \"custId\": 1,\n" +
                "        \"custName\": \"Venu\",\n" +
                "        \"totalRewards\": 40\n" +
                "    }\n" +
                "]";
        JSONAssert.assertEquals(expectedPayload, responsePayload, false);
    }

    @Test
    public void testGetAllCustomerMonthlyRewardPoints() throws Exception {
        MonthlyRewardsDto monthlyRewardsDto = buildMockCustomerMonthlyRewardsRes();
        List<MonthlyRewardsDto> mockAllCustMonthlyRwdRes = List.of(monthlyRewardsDto);

        when(service.fetchAllCustMonthlyRewardReport()).thenReturn(mockAllCustMonthlyRwdRes);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rewards/customers/month");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String responsePayload = mvcResult.getResponse().getContentAsString();

        String expectedPayload = "[\n" +
                "    {\n" +
                "        \"custId\": 1,\n" +
                "        \"custName\": \"Venu\",\n" +
                "        \"totalRewards\": 40,\n" +
                "        \"monthlyRewards\": {\n" +
                "            \"APRIL\": 40\n" +
                "        }\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expectedPayload, responsePayload, false);
    }

    private CustomerRewardsDto buildMockCustomerRewardsResponse() {
        return CustomerRewardsDto.builder()
                .custId(1l)
                .custName("Venu")
                .totalRewards(40l).build();
    }

    private MonthlyRewardsDto buildMockCustomerMonthlyRewardsRes() {
        return MonthlyRewardsDto.builder()
                .custId(1l)
                .custName("Venu")
                .totalRewards(40l)
                .monthlyRewards(Map.of("APRIL", 40L)).build();
    }
}
