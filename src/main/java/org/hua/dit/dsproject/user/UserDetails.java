package org.hua.dit.dsproject.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    //User credentials, this will be populated by the User class, coming from the db
    public String idNumber;
    public String userName;
    public String password;
    public boolean active;
    public List<SimpleGrantedAuthority> grantedAuthorities;


    //Constructors
    public UserDetails() {
    }

    public UserDetails(User user) {
        this.idNumber = user.getIdNumber();
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.grantedAuthorities = Arrays.stream(user.getRole().split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                .collect(Collectors.toList());

    }

    //Getters and Setters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}