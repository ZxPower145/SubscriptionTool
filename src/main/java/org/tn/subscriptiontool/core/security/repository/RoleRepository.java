package org.tn.subscriptiontool.core.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.subscriptiontool.core.security.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
