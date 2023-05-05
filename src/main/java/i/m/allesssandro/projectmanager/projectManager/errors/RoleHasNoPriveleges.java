package i.m.allesssandro.projectmanager.projectManager.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleHasNoPriveleges extends ResponseStatusException
{
    public RoleHasNoPriveleges()
    {
        super(HttpStatus.BAD_REQUEST, "invalid role");
    }
}
