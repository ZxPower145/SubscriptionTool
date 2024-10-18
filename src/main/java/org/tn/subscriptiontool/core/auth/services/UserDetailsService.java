package org.tn.subscriptiontool.core.auth.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tn.subscriptiontool.core.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository repository;


    /**
     * Loads a user by their email address.
     *
     * <p>This method is called during the authentication process to fetch user
     * details from the database. If the user is not found, a
     * {@link UsernameNotFoundException} is thrown.</p>
     *
     * @param email the email address of the user to be loaded.
     * @return the {@link UserDetails} object representing the user.
     * @throws UsernameNotFoundException if no user with the given email is found.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("There is no User associated to the email address '" + email + "'"));
    }
}
