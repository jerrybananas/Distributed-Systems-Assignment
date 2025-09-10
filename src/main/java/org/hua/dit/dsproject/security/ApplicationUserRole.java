package org.hua.dit.dsproject.security;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author it21542 - Antonis Rouseas
 * User roles for the Pet Registry System
 */
public enum ApplicationUserRole {
    CITIZEN,
    VET,
    ADMIN;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}