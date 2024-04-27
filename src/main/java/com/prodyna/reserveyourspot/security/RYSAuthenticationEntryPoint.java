package com.prodyna.reserveyourspot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.exception.ErrorResponseDTO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class RYSAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        final ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        final ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(authException.getMessage(), Objects.toString(errorCode.getStatus().value()),
                Objects.toString(System.currentTimeMillis()), errorCode.getStatus().getReasonPhrase() + " - " + errorCode.getMessage(),
                authException.getClass().getName(), request.getServletPath());
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponseDTO));
    }
}
