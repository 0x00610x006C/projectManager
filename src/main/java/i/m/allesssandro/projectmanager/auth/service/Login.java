package i.m.allesssandro.projectmanager.auth.service;

import lombok.Getter;

public class Login
{
    @Getter
    private final Jwt accessJwt;

    @Getter
    private final Jwt refreshJwt;

    private final static Long ACCESS_TOKEN_VALIDITY = 100L;

    private final static Long REFRESH_TOKEN_VALIDITY = 1440L;

    private Login(Jwt accessJwt, Jwt refreshJwt)
    {
        this.accessJwt = accessJwt;
        this.refreshJwt = refreshJwt;
    }

    public static Login of(Long userId, String accessSecret, String refreshSecret)
    {
        return new Login(
                Jwt.of(userId, ACCESS_TOKEN_VALIDITY, accessSecret),
                Jwt.of(userId, REFRESH_TOKEN_VALIDITY, refreshSecret)
        );
    }

    public static Login of(Long userId, String accessSecret, Jwt refreshJwt)
    {
        return new Login(
                Jwt.of(userId, ACCESS_TOKEN_VALIDITY, accessSecret),
                refreshJwt
        );
    }
}
