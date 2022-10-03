/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.core.security;

import com.moises.todo.todorestapi.domain.model.User;
import com.moises.todo.todorestapi.domain.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        AppUserDetails userDetails = new AppUserDetails(user);

        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(), userDetails.getPassword(),
                true, true, true, true, userDetails.getAuthorities());

    }

}
