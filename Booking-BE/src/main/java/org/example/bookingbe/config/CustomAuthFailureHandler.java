package org.example.bookingbe.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng!";

        if (exception instanceof LockedException) {
            errorMessage = "Tài khoản đã bị khóa!";
        } else if (exception instanceof DisabledException) {
            errorMessage = "Tài khoản chưa được kích hoạt!";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "Mật khẩu đã hết hạn!";
        }

        response.sendRedirect("/login?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
    }
}
