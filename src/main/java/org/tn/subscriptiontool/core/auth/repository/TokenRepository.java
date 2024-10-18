package org.tn.subscriptiontool.core.auth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.auth.models.Token;
import org.tn.subscriptiontool.core.auth.models.User;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    void deleteAllByUser(User user);
}
