package org.tn.subscriptiontool.core.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.auth.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    User findByEmail(String email);
}
