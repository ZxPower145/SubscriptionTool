package org.tn.subscriptiontool.core.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.security.models.User;

public interface AddressRepository extends JpaRepository<User, Long> {
}
