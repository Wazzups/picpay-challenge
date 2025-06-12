package com.wazzups.picpaychallengev2.domain.repository;

import com.wazzups.picpaychallengev2.domain.model.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionRecord, Long> {
}
