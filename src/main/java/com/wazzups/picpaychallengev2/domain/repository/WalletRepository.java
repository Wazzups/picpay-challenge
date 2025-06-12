package com.wazzups.picpaychallengev2.domain.repository;

import com.wazzups.picpaychallengev2.domain.model.Wallet;
import java.rmi.server.UID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UID> {
    Optional<Wallet> findByUserId(Long userId);
}
