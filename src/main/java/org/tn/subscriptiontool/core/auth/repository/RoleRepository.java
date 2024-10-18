package org.tn.subscriptiontool.core.auth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.auth.models.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}
