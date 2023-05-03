package i.m.allesssandro.projectmanager.auth.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundError extends ResponseStatusException
{
    public UserNotFoundError()
    {
        super(HttpStatus.BAD_REQUEST, "user not found");
    }
}
