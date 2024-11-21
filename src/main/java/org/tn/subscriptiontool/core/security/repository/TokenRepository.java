package org.tn.subscriptiontool.core.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.security.models.Token;
import org.tn.subscriptiontool.core.security.models.User;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    void deleteAllByUser(User user);
}
