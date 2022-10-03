/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.core.security;

import com.moises.todo.todorestapi.domain.model.Role;
import com.moises.todo.todorestapi.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AppUserDetails implements UserDetails {

    private User user;

    public AppUserDetails(User user) {
        this.user = user;
    }

    public AppUserDetails(String username, String password, boolean isAccountNonExpired, boolean isAccountNonLocked,
                          boolean isCredentialsNonExpired, boolean isEnabled, Collection<? extends GrantedAuthority> authorities) {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        Set<Role> roles = user.getRoles();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
//        }
//
//        return authorities;

        return this.user.getRoles();

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
