package org.tn.subscriptiontool.core.auth.authorization.tokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EmailAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final String token;

    public EmailAuthenticationToken(Object principal, String token) {
        super(null);
        this.principal = principal;
        this.token = token;
        setAuthenticated(false);
    }

    public EmailAuthenticationToken(Object principal, String token,
                                    Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
