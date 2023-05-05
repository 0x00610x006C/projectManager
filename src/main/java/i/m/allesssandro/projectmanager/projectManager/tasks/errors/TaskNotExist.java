package i.m.allesssandro.projectmanager.projectManager.tasks.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaskNotExist extends ResponseStatusException
{
    public TaskNotExist()
    {
        super(HttpStatus.BAD_REQUEST, "this task not exist");
    }
}
