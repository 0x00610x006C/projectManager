package i.m.allesssandro.projectmanager.projectManager.projects;

import i.m.allesssandro.projectmanager.auth.repo.User;
import i.m.allesssandro.projectmanager.auth.repo.UserRole;
import i.m.allesssandro.projectmanager.projectManager.errors.RoleHasNoPriveleges;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user/projects")
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

    @GetMapping(value = "/all")
    public GetAllProjectsResponse getAllProjects()
    {
        List<Project> projects = projectManagerService.getAllProjects();

        return new GetAllProjectsResponse(projects);
    }

    record CreateProjectRequest (
        String name,
        Long parentId
    ) {}

    record CreateProjectResponse(
            Project project
    ) {}

    @PostMapping(value = "/new")
    public CreateProjectResponse createProject(HttpServletRequest request, @RequestBody CreateProjectRequest createProjectRequest)
    {
        User user = (User) request.getAttribute("user");

        if (!UserRole.ADMIN.equals(user.getRole()))
        {
            throw new RoleHasNoPriveleges();
        }

        Project project = projectManagerService.create(
                createProjectRequest.name(),
                createProjectRequest.parentId()
        );

        return new CreateProjectResponse(project);
    }

    record DeleteProjectRequest (
            Long id
    ) {}

    record DeleteProjectResponse (
            List<Long> deletedSubprojects
    ) {}

    @DeleteMapping(value = "/drop")
    public DeleteProjectResponse deleteProject(HttpServletRequest request, @RequestBody DeleteProjectRequest deleteProjectRequest)
    {
        User user = (User) request.getAttribute("user");

        if (!UserRole.ADMIN.equals(user.getRole()))
        {
            throw new RoleHasNoPriveleges();
        }

        List<Long> deletedSubprojects = projectManagerService.dropProject(deleteProjectRequest.id());

        return new DeleteProjectResponse(deletedSubprojects);
    }

    record EditProjectRequest(
            Long id,
            String name
    ) {}

    record EditProjectResponse (
            Project project
    ) {}

    @PatchMapping(value = "/edit")
    public EditProjectResponse editProject(HttpServletRequest request, @RequestBody EditProjectRequest editProjectRequest)
    {
        User user = (User) request.getAttribute("user");

        if (!UserRole.ADMIN.equals(user.getRole()))
        {
            throw new RoleHasNoPriveleges();
        }

        Project project = projectManagerService.editProject(editProjectRequest.id, editProjectRequest.name());

        return new EditProjectResponse(project);
    }



}
