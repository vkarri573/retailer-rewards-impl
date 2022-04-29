package com.retailer.rewards.impl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entity to represent Transaction.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long custId;

    @NotBlank(message = "Customer Name is required")
    private String custName;

    @NotNull(message = "Transaction Amount is required")
    private Double amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

}
