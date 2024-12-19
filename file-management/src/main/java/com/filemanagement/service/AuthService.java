package com.filemanagement.service;


import com.filemanagement.config.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private PermissionService permissionService;


    public String authenticate(String email) {
        try {
            List<String> permissionsByGroupPerUser = permissionService.getPermissionsGroupPerUser(email);

            if (CollectionUtils.isEmpty(permissionsByGroupPerUser)) {
                throw new AccessDeniedException("Invalid user email !!");
            }

            // Perform authentication logic (e.g., check username and password)
            UserDetails userDetails = new User(email, email,
                    AuthorityUtils.createAuthorityList(permissionsByGroupPerUser.toArray(new String[0])));

            // Generate JWT token
            String jwtToken = tokenManager.generateToken(email, permissionsByGroupPerUser);

            // Create custom authentication object
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, email, userDetails.getAuthorities());

            // Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            return jwtToken; // Return the generated token
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials", e);
        }
    }

}