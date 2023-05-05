package i.m.allesssandro.projectmanager.projectManager.tasks;

import i.m.allesssandro.projectmanager.auth.repo.User;
import i.m.allesssandro.projectmanager.projectManager.tasks.errors.IncorrectTaskType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

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

    record GetAllTasksResponse(
            List<Task> tasks
    ){}

    @GetMapping(value = "/all")
    public GetAllTasksResponse getAllProjects()
    {
        List<Task> tasks = taskService.getAllTasks();

        return new GetAllTasksResponse(tasks);
    }

}
