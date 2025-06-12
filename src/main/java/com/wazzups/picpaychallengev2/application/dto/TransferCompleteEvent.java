package com.wazzups.picpaychallengev2.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferCompleteEvent(UUID transactionId, Long payeeId, BigDecimal amount) {
}
