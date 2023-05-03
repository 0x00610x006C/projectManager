package i.m.allesssandro.projectmanager.auth.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExists extends ResponseStatusException
{
    public UserAlreadyExists()
    {
        super(HttpStatus.BAD_REQUEST, "user already exist");
    }
}
