package i.m.allesssandro.projectmanager.auth.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnknownUserRole extends ResponseStatusException
{
    public UnknownUserRole()
    {
        super(HttpStatus.BAD_REQUEST, "unknown user role");
    }
}
