package i.m.allesssandro.projectmanager.projectManager.tasks;

import i.m.allesssandro.projectmanager.auth.repo.User;
import i.m.allesssandro.projectmanager.auth.repo.UserRole;
import i.m.allesssandro.projectmanager.projectManager.errors.RoleHasNoPriveleges;
import i.m.allesssandro.projectmanager.projectManager.projects.Project;
import i.m.allesssandro.projectmanager.projectManager.projects.ProjectManagerController;
import i.m.allesssandro.projectmanager.projectManager.tasks.errors.IncorrectTaskType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/user/tasks")
public class TaskController
{
    private final TaskService taskService;

    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    record CreateTaskRequest (
            String name,
            String type,
            Long projectId
    ) {}

    record CreateTaskResponse(
            Task task
    ) {}

    @PostMapping(value = "/new")
    public CreateTaskResponse createProject(HttpServletRequest request, @RequestBody CreateTaskRequest createTaskRequest)
    {
        User user = (User) request.getAttribute("user");

        if (Arrays.stream(TaskType.values())
                .map(TaskType::toString)
                .noneMatch(createTaskRequest.type()::equals))
        {
            throw new IncorrectTaskType();
        }

        Task task = taskService.create(
                createTaskRequest.name(),
                createTaskRequest.type(),
                createTaskRequest.projectId(),
                user.getId()
        );

        return new CreateTaskResponse(task);
    }

}
