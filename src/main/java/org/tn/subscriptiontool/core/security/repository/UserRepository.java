package org.tn.subscriptiontool.core.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.security.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
