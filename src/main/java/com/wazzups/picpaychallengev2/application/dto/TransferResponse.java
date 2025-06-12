package com.wazzups.picpaychallengev2.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransferResponse {
    private Long transactionId;
    private BigDecimal amount;
    private Long payerId;
    private Long payeeId;
    private LocalDateTime timestamp;
}
