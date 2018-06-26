package com.acme2.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AnonAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -2306265548704129024L;

	public AnonAuthentication() {
        super( null );
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        else if ( obj == null ) {
            return false;
        }
        else if ( getClass() != obj.getClass() ) {
            return false;
        }
        return true;
    }


}
