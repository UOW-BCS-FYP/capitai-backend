package com.example.springboot.dto;

import java.util.Date;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinancialGoalCreateDTO {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Type is mandatory")
    @Pattern(regexp = "^(capital building|debt payment|long term expense)$", message = "Invalid type")
    private String type;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double amount;

    @NotNull(message = "Priority is mandatory")
    @Min(1)
    private Integer priority;

    private Boolean completed;

    private Date deadline;

    @DecimalMin(value = "0.0", inclusive = true)
    private Double interest;
    
    @Min(1)
    private Integer paymentInterval;

    @Min(1)
    private Integer frequency;

    @AssertTrue(message = "Deadline is mandatory for 'capital building' type")
    private boolean isDeadlineValid() {
        return !("capital building".equals(type) && deadline == null);
    }

    @AssertTrue(message = "Payment interval is mandatory for 'debt payment' type")
    private boolean isPaymentIntervalValid() {
        return !("debt payment".equals(type) && paymentInterval == null);
    }

    @AssertTrue(message = "Frequency is mandatory for 'long term expense' type")
    private boolean isFrequencyValid() {
        return !("long term expense".equals(type) && frequency == null);
    }

}
