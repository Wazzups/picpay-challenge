package com.wazzups.picpaychallengev2.service;

import com.wazzups.picpaychallengev2.application.dto.TransferCompleteEvent;
import com.wazzups.picpaychallengev2.application.dto.TransferResponse;
import com.wazzups.picpaychallengev2.application.exception.InsufficientBalanceException;
import com.wazzups.picpaychallengev2.application.exception.UnauthorizedTransferException;
import com.wazzups.picpaychallengev2.application.exception.UserNotFoundException;
import com.wazzups.picpaychallengev2.application.exception.WalletNotFoundException;
import com.wazzups.picpaychallengev2.application.mapper.TransactionMapper;
import com.wazzups.picpaychallengev2.domain.model.TransactionRecord;
import com.wazzups.picpaychallengev2.domain.model.TransactionStatus;
import com.wazzups.picpaychallengev2.domain.model.User;
import com.wazzups.picpaychallengev2.domain.model.UserType;
import com.wazzups.picpaychallengev2.domain.model.Wallet;
import com.wazzups.picpaychallengev2.domain.repository.TransactionRepository;
import com.wazzups.picpaychallengev2.domain.repository.UserRepository;
import com.wazzups.picpaychallengev2.domain.repository.WalletRepository;
import com.wazzups.picpaychallengev2.infra.service.AuthorizerClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TransferService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final AuthorizerClient authorizerClient;
    private final ApplicationEventPublisher publisher;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransferResponse executeTransfer(BigDecimal value, Long payerId, Long payeeId){
        User payer = userRepository.findById(payerId).orElseThrow(() -> new UserNotFoundException("Payer User not found"));
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new UserNotFoundException("Payee user not found"));

        if (payer.getUserType() == UserType.MERCHANT)
            throw new UnauthorizedTransferException("Payer user is merchant");

        Wallet payerWallet = walletRepository.findByUserId(payer.getId()).orElseThrow(() -> new WalletNotFoundException("Payer wallet not found"));
        Wallet payeeWallet = walletRepository.findByUserId(payee.getId()).orElseThrow(() -> new WalletNotFoundException("Payee wallet not found"));

        if (payerWallet.getBalance().compareTo(value) < 0)
            throw new InsufficientBalanceException("Payer don't have enough balance");

        authorizerClient.authorize();

        payerWallet.setBalance(payerWallet.getBalance().subtract(value));
        payeeWallet.setBalance(payeeWallet.getBalance().add(value));

        TransactionRecord transactionSaved = transactionRepository.save(new TransactionRecord(payer, payee, value, LocalDateTime.now(), TransactionStatus.COMPLETED));

        publisher.publishEvent(new TransferCompleteEvent(transactionSaved.getId(), payeeId, value));

        return transactionMapper.toResponse(transactionSaved);
    }
}
