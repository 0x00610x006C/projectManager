package i.m.allesssandro.projectmanager.projectManager.projects.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProjectNotExist extends ResponseStatusException
{
    public ProjectNotExist()
    {
        super(HttpStatus.BAD_REQUEST, "this project not exist");
    }
}
