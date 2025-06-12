package com.wazzups.picpaychallengev2.unit.integration;

import com.wazzups.picpaychallengev2.application.dto.TransferRequest;
import com.wazzups.picpaychallengev2.application.dto.TransferResponse;
import com.wazzups.picpaychallengev2.domain.model.User;
import com.wazzups.picpaychallengev2.domain.model.UserType;
import com.wazzups.picpaychallengev2.domain.model.Wallet;
import com.wazzups.picpaychallengev2.domain.repository.UserRepository;
import com.wazzups.picpaychallengev2.domain.repository.WalletRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransferIntegrationTest {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private UserRepository userRepo;
    @Autowired private WalletRepository walletRepo;

    @BeforeEach
    void setup() {
        userRepo.deleteAll();
        walletRepo.deleteAll();

        User u1 = new User();
        u1.setFullName("Alice Silva");
        u1.setCpf("12345678900");
        u1.setEmail("alice@gmail.com");
        u1.setUserType(UserType.COMMON);
        userRepo.save(u1);

        Wallet w1 = new Wallet();
        w1.setUser(u1);
        w1.setBalance(BigDecimal.valueOf(500));
        walletRepo.save(w1);

        User u2 = new User();
        u2.setFullName("Shoppy");
        u2.setCpf("99988877700");
        u2.setEmail("shop@gmail.com");
        u2.setUserType(UserType.MERCHANT);
        userRepo.save(u2);

        Wallet w2 = new Wallet();
        w2.setUser(u2);
        w2.setBalance(BigDecimal.valueOf(300));
        walletRepo.save(w2);
    }

    @Test
    void whenSuccessTransferShouldReturnStatusCode200() {
        Long payerId = userRepo.findByCpf("12345678900").get().getId();
        Long payeeId = userRepo.findByCpf("99988877700").get().getId();

        TransferRequest req = new TransferRequest();
        req.setValue(BigDecimal.valueOf(100));
        req.setPayerId(payerId);
        req.setPayeeId(payeeId);

        ResponseEntity<TransferResponse> resp = restTemplate.postForEntity(
            "/transfer", req, TransferResponse.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);

        Wallet payerWallet = walletRepo.findByUserId(payerId).get();
        Wallet payeeWallet = walletRepo.findByUserId(payeeId).get();

        assertThat(payerWallet.getBalance()).isEqualByComparingTo("400.00");
        assertThat(payeeWallet.getBalance()).isEqualByComparingTo("400.00");
    }
}
