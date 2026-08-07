package com.medicare;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        var roles = authentication.getAuthorities();

        if (roles.stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {

            response.sendRedirect("/admin");

        } else if (roles.stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_MEDICO"))) {

            response.sendRedirect("/medico");

        } else if (roles.stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_PACIENTE"))) {

            response.sendRedirect("/citas/historial");

        }

    }

}
