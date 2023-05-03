package i.m.allesssandro.projectmanager.auth.repo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@ToString
@Table(value = "users")
public class User
{
    @Id
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private UserRole role;


    @MappedCollection(idColumn = "user_name")
    private final Set<Token> tokenCollection = new HashSet<>();

    public static User of(String login, String password, UserRole role)
    {
        return new User(null, login, password, Collections.emptyList(), role);
    }

    @PersistenceConstructor
    private User(Long id, String login, String password, Collection<Token> tokenCollection, UserRole role)
    {
        this.id = id;
        this.login = login;
        this.password = password;
        this.tokenCollection.addAll(tokenCollection);
        this.role = role;
    }

    public void addToken(Token token)
    {
        tokenCollection.add(token);
    }

    public boolean removeToken(Predicate<? super Token> predicate)
    {
        return tokenCollection.removeIf(predicate);
    }

}
