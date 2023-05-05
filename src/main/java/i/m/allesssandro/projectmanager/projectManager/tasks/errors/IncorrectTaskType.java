package i.m.allesssandro.projectmanager.projectManager.tasks.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectTaskType extends ResponseStatusException
{
    public IncorrectTaskType()
    {
        super(HttpStatus.BAD_REQUEST, "incorrect task type");
    }
}

