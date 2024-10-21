package org.tn.subscriptiontool.core.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.security.models.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}
