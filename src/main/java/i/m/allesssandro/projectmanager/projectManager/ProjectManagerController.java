package i.m.allesssandro.projectmanager.projectManager;

import i.m.allesssandro.projectmanager.projectManager.repo.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class ProjectManagerController
{
    private final ProjectManagerService projectManagerService;

    public ProjectManagerController(ProjectManagerService projectManagerService)
    {
        this.projectManagerService = projectManagerService;
    }

    record GetAllProjectsResponse(
            List<Project> projects
    ){}

    @GetMapping(value = "/projects")
    public GetAllProjectsResponse getAllProjects()
    {
        List<Project> projects = projectManagerService.getAllProjects();

        return new GetAllProjectsResponse(projects);
    }
}
