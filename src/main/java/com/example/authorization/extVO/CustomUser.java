package com.example.authorization.extVO;


import com.example.authorization.entity.UserOAuthEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUser extends User {

    private static final long serialVersionUID = 1L;

    public CustomUser(UserOAuthEntity user, List<GrantedAuthority> grantedAuthoritiesList) {

        super(user.getUsername(), user.getPassword(), grantedAuthoritiesList);
    }
}
