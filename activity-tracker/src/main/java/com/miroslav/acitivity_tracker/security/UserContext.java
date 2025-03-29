package com.miroslav.acitivity_tracker.security;

import com.miroslav.acitivity_tracker.user.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContext {

    public User getAuthenticatedUser(){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(user.isAccountNonLocked() && user.isEnabled())
//            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        else
//            return null;
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
