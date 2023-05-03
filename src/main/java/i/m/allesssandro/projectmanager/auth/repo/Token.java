package i.m.allesssandro.projectmanager.auth.repo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@ToString
@Table(value = "token")
public class Token
{
    @Id
    private Long id;

    @Getter
    @Setter
    private String refreshToken;

    @Getter
    @Setter
    private LocalDateTime expiredAt;

    @Getter
    @Setter
    private LocalDateTime issuedAt;

    @Column("user_name")
    @Getter
    @Setter
    private Long userName;

    @PersistenceConstructor
    private Token(Long id, String refreshToken, LocalDateTime expiredAt, LocalDateTime issuedAt, Long userName)
    {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
        this.issuedAt = issuedAt;
        this.userName = userName;
    }

    public Token(String refreshToken, LocalDateTime expiredAt, LocalDateTime issuedAt)
    {
        this.id = null;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
        this.issuedAt = issuedAt;
        this.userName = null;
    }
}

