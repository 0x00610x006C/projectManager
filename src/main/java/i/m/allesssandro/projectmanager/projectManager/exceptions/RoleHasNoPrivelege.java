package i.m.allesssandro.projectmanager.projectManager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleHasNoPrivelege extends ResponseStatusException
{
    public RoleHasNoPrivelege()
    {
        super(HttpStatus.BAD_REQUEST, "invalid role");
    }
}
