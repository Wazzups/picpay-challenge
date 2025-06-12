package com.wazzups.picpaychallengev2.web.controller;

import com.wazzups.picpaychallengev2.application.dto.TransferRequest;
import com.wazzups.picpaychallengev2.application.dto.TransferResponse;
import com.wazzups.picpaychallengev2.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        TransferResponse transferResponse = transferService.executeTransfer(request.getValue(), request.getPayerId(), request.getPayeeId());

        return ResponseEntity.ok(transferResponse);
    }
}