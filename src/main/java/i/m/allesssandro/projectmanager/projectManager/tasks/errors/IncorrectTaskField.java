package i.m.allesssandro.projectmanager.projectManager.tasks.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectTaskField extends ResponseStatusException
{
    public IncorrectTaskField(String value)
    {
        super(HttpStatus.BAD_REQUEST, "incorrect value " + value + " in task field");
    }
}

