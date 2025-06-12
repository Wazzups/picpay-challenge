package com.wazzups.picpaychallengev2.domain.repository;

import com.wazzups.picpaychallengev2.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
