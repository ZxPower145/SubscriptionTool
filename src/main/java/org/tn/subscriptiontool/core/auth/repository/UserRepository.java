package org.tn.subscriptiontool.core.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.auth.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
