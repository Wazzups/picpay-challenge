package com.wazzups.picpaychallengev2.unit;

import com.wazzups.picpaychallengev2.application.exception.InsufficientBalanceException;
import com.wazzups.picpaychallengev2.domain.model.User;
import com.wazzups.picpaychallengev2.domain.model.UserType;
import com.wazzups.picpaychallengev2.domain.model.Wallet;
import com.wazzups.picpaychallengev2.domain.repository.TransactionRepository;
import com.wazzups.picpaychallengev2.domain.repository.UserRepository;
import com.wazzups.picpaychallengev2.domain.repository.WalletRepository;
import com.wazzups.picpaychallengev2.infra.service.AuthorizerClient;
import com.wazzups.picpaychallengev2.service.TransferService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private WalletRepository walletRepository;
    @Mock private TransactionRepository transactionRepository;
    @Mock private AuthorizerClient authorizerClient;
    @InjectMocks private TransferService transferService;

    @Test
    void notEnoughBalanceShouldThrowInsufficientBalanceException() {
        Long payerId = 1L;
        Long payeeId = 2L;
        BigDecimal value = BigDecimal.valueOf(200);

        User payer = new User();
        payer.setId(payerId);
        payer.setUserType(UserType.COMMON);

        User payee = new User();
        payee.setId(payeeId);
        payee.setUserType(UserType.MERCHANT);

        Wallet payerWallet = new Wallet();
        payerWallet.setUser(payer);
        payerWallet.setBalance(BigDecimal.valueOf(100));

        Wallet payeeWallet = new Wallet();
        payeeWallet.setUser(payee);
        payeeWallet.setBalance(BigDecimal.valueOf(100));

        Mockito.when(userRepository.findById(payerId)).thenReturn(Optional.of(payer));
        Mockito.when(userRepository.findById(payeeId)).thenReturn(Optional.of(payee));
        Mockito.when(walletRepository.findByUserId(payerId)).thenReturn(Optional.of(payerWallet));
        Mockito.when(walletRepository.findByUserId(payeeId)).thenReturn(Optional.of(payeeWallet));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> transferService.executeTransfer(value, payerId, payeeId));
    }
}