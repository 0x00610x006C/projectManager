package i.m.allesssandro.projectmanager.projectManager.tasks;

import i.m.allesssandro.projectmanager.auth.repo.User;
import i.m.allesssandro.projectmanager.auth.repo.UserRole;
import i.m.allesssandro.projectmanager.projectManager.errors.RoleHasNoPriveleges;
import i.m.allesssandro.projectmanager.projectManager.tasks.errors.IncorrectTaskField;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

        checkEnumValue(TaskType.values(), TaskType::toString, createTaskRequest.type());

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

    record EditTaskStatusRequest(
            String status,
            Long id
    ) {}

    record EditTaskStatusResponse (
            Task task
    ) {}


    @PatchMapping(value = "/editStatus")
    public EditTaskStatusResponse editProject(@RequestBody EditTaskStatusRequest editTaskStatusRequest)
    {
        checkEnumValue(TaskStatus.values(), TaskStatus::toString, editTaskStatusRequest.status());

        Task task = taskService.editStatus(
                editTaskStatusRequest.status(),
                editTaskStatusRequest.id()
        );

        return new EditTaskStatusResponse(task);
    }

    record EditTaskRequest(
            Long id,
            String name,
            String type,
            String status
    ) {}

    record EditTaskResponse (
            Task task
    ) {}

    @PatchMapping(value = "/edit")
    public EditTaskResponse editProject(HttpServletRequest request, @RequestBody EditTaskRequest editTaskRequest)
    {
        User user = (User) request.getAttribute("user");

        if (!UserRole.ADMIN.equals(user.getRole()))
        {
            throw new RoleHasNoPriveleges();
        }

        checkEnumValue(TaskType.values(), TaskType::toString, editTaskRequest.type());

        checkEnumValue(TaskStatus.values(), TaskStatus::toString, editTaskRequest.status());

        Task task = taskService.editTask(editTaskRequest.id(),
                editTaskRequest.name(),
                editTaskRequest.type(),
                editTaskRequest.status());

        return new EditTaskResponse(task);
    }

    record DeleteTaskRequest (
            Long id
    ) {}

    record DeleteTaskResponse (
            Long id
    ) {}


    @DeleteMapping(value = "/drop")
    public DeleteTaskResponse deleteProject(HttpServletRequest request, @RequestBody DeleteTaskRequest deleteTaskRequest)
    {
        User user = (User) request.getAttribute("user");

        if (!UserRole.ADMIN.equals(user.getRole()) || !taskService.getAuthor(deleteTaskRequest.id()).equals(user.getId()))
        {
            throw new RoleHasNoPriveleges();
        }

        Long id = taskService.dropProject(deleteTaskRequest.id());

        return new DeleteTaskResponse(id);
    }


    private <T extends Enum<T>> void checkEnumValue(T[] enumValues, Function<T, String> mapFunc, String value)
    {
        if (Arrays.stream(enumValues)
                .map(mapFunc)
                .noneMatch(value::equals))
        {
            throw new IncorrectTaskField(value);
        }
    }
}
