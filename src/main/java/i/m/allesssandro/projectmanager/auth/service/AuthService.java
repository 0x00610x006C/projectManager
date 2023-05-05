package i.m.allesssandro.projectmanager.auth.service;

import i.m.allesssandro.projectmanager.auth.errors.*;
import i.m.allesssandro.projectmanager.auth.repo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;

@Service
public class AuthService
{
    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final String accessSecret;

    private final String refreshSecret;

    public AuthService(UserRepository userRepository,
                       TokenRepository tokenRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${application.security.access-token-secret}")
                               String accessSecret,
                       @Value("${application.security.refresh-token-secret}")
                               String refreshSecret)
    {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
    }

    public User register(String login, String password, String passwordConfirm, String role)
    {
        if (!Objects.equals(password, passwordConfirm))
        {
            throw new PasswordDoNotMatchError();
        }

        UserRole userRole;
        try
        {
            userRole = UserRole.valueOf(role);
        }
        catch (IllegalArgumentException e)
        {
            throw new UnknownUserRole();
        }

        User user;
        try
        {
            user = userRepository.save(User.of(login, passwordEncoder.encode(password), userRole));
        }
        catch (DbActionExecutionException e)
        {
            throw new UserAlreadyExists();
        }

        return user;
    }

    public Login login(String name, String password)
    {
        User user = userRepository.findByLogin(name)
                .orElseThrow(InvalidCredentialsError::new);

        if (!passwordEncoder.matches(password, user.getPassword()))
        {
            throw new InvalidCredentialsError();
        }

        Login login = Login.of(user.getId(), accessSecret, refreshSecret);
        Jwt refreshJwt = login.getRefreshJwt();

        user.addToken(new Token(refreshJwt.getToken(), refreshJwt.getExpiration(refreshSecret), refreshJwt.getIssuedAt(refreshSecret)));
        userRepository.save(user);

        return login;
    }

    public Boolean logout(String refreshToken)
    {
        Jwt jwt = Jwt.of(refreshToken);

        User user = userRepository.findById(Jwt.from(refreshToken, refreshSecret))
                .orElseThrow(UnauthenticatedError::new);

        boolean tokenIsRemoved = user.removeToken(token1 -> Objects.equals(jwt.getToken(), refreshToken));

        if (tokenIsRemoved)
        {
            userRepository.save(user);
        }

        return tokenIsRemoved;
    }

    public User getUserFromToken(String token)
    {
        User user = userRepository.findById(Jwt.from(token, accessSecret))
                .orElseThrow(UserNotFoundError::new);

        tokenRepository.findByUserName(user.getId())
                .stream()
                .max(Comparator.comparingLong(Token::getId))
                .orElseThrow(UnauthenticatedError::new);

        return user;
    }

}
