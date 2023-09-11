package com.example.jobsearch.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

public class CustomSimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException {
            Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());

            if (roles.contains("ROLE_APPLICANT")) {
                response.sendRedirect("/vacancy/all/view");
            } else if (roles.contains("ROLE_EMPLOYER")) {
                response.sendRedirect("/resume/all/view");
            } else {
                response.sendRedirect("/");
            }
        }

}
