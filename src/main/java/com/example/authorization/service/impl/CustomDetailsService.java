package com.example.authorization.service.impl;

import com.example.authorization.entity.UserOAuthEntity;
import com.example.authorization.extVO.CustomUser;
import com.example.authorization.service.IUserOAuth2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    IUserOAuth2Service userOAuth2Service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthoritiesList.add(grantedAuthority);
        UserOAuthEntity userOAuthEntity = null;
        try {
            userOAuthEntity = userOAuth2Service.getUserOAuth2(username);
            CustomUser customUser = new CustomUser(userOAuthEntity, grantedAuthoritiesList);
            return customUser;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}
