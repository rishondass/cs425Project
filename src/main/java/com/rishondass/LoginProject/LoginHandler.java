package com.rishondass.loginSystem;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();

        authorities.forEach(grantedAuthority -> {
            if(grantedAuthority.getAuthority().equals("ROLE_USER")){
                try {
                    redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/user");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                try {
                    redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/admin");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(grantedAuthority.getAuthority().equals("ROLE_MANAGER")){
                try {
                    redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/manager");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                throw new IllegalStateException();
            }
        });

    }
}
