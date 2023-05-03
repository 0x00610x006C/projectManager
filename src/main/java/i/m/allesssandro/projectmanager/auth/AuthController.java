package i.m.allesssandro.projectmanager.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import i.m.allesssandro.projectmanager.auth.repo.User;
import i.m.allesssandro.projectmanager.auth.service.AuthService;
import i.m.allesssandro.projectmanager.auth.service.Login;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api")
public class AuthController
{
    private final AuthService authService;

    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }


    record RegisterRequest(
            String login,
            String password,
            @JsonProperty("password_confirm")
            String passwordConfirm,
            String role
    ) {}

    record RegisterResponse(
            Long id,
            String login
    ) {}

    @PostMapping(value = "/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest)
    {
        User user = authService.register(
                registerRequest.login(),
                registerRequest.password(),
                registerRequest.passwordConfirm(),
                registerRequest.role()
        );

        return new RegisterResponse(user.getId(), user.getLogin());
    }

    record LoginRequest(
            String login,
            String password
    ) {}

    record LoginResponse(
            String token
    ) {}

    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
    {
        Login login = authService.login(loginRequest.login(), loginRequest.password());

        Cookie cookie = new Cookie("refresh_token", login.getRefreshJwt().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");

        response.addCookie(cookie);

        return new LoginResponse(login.getAccessJwt().getToken());
    }

    record LogoutResponse(String message){}

    @PostMapping(value = "/logout")
    public LogoutResponse logout(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response)
    {
        authService.logout(refreshToken);

        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new LogoutResponse("success");
    }

    record UserResponse(
            Long id,
            String login,
            String role
    ) {}

    @GetMapping(value = "/user")
    public UserResponse user(HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");

        return new UserResponse(user.getId(), user.getLogin(), user.getRole().toString());
    }

}
