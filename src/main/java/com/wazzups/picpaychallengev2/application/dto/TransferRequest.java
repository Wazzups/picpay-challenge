package com.wazzups.picpaychallengev2.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    @NotNull(message = "The value can't be null")
    @Positive(message = "The value should be bigger than zero")
    private BigDecimal value;

    @NotNull(message = "Payer ID can't be null")
    private Long payerId;

    @NotNull(message = "Payee ID can't be null")
    private Long payeeId;
}
