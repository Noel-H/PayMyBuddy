package com.noelh.paymybuddy.configuration;

import com.noelh.paymybuddy.model.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom UserDetails Class
 */
public class CustomUserDetails implements UserDetails {

    private UserAccount userAccount;

    /**
     * Construct the class with a UserAccount
     * @param userAccount who will be used to construct CustomUserDetails
     */
    public CustomUserDetails(UserAccount userAccount) {
        super();
        this.userAccount = userAccount;
    }

    /**
     * Get the authorities
     * @return always null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Get the password
     * @return the password
     */
    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    /**
     * Get the username
     * @return a loginMail as username
     */
    @Override
    public String getUsername() {
        return userAccount.getLoginMail();
    }

    /**
     * Check if the account is non expired
     * @return always true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the account is non locked
     * @return always true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the credential is non expired
     * @return always true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check the status of enabled
     * @return always true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
