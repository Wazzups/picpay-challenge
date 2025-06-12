package com.wazzups.picpaychallengev2.application.mapper;

import com.wazzups.picpaychallengev2.application.dto.TransferResponse;
import com.wazzups.picpaychallengev2.domain.model.TransactionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "payer.id", target = "payerId")
    @Mapping(source = "payee.id", target = "payeeId")
    @Mapping(source = "timestamp", target = "timestamp")
    TransferResponse toResponse(TransactionRecord transactionRecord);
}
