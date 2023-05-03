package i.m.allesssandro.projectmanager.config;

import i.m.allesssandro.projectmanager.auth.service.AuthService;
import i.m.allesssandro.projectmanager.auth.errors.NoBearerTokenError;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class AutorizationInterceptor implements HandlerInterceptor
{
    private final AuthService authService;

    public AutorizationInterceptor(AuthService authService)
    {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        String authorizationHeader = request.getHeader("Authorization");

        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer "))
            throw new NoBearerTokenError();

        request.setAttribute("user", authService.getUserFromToken(authorizationHeader.substring(7)));

        return true;
    }
}
