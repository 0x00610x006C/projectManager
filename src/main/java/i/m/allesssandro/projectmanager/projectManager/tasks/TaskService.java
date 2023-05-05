package i.m.allesssandro.projectmanager.projectManager.tasks;

import i.m.allesssandro.projectmanager.projectManager.tasks.errors.TaskNotExist;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService
{
    private final TaskRepository repository;

    public TaskService(TaskRepository repository)
    {
        this.repository = repository;
    }


    public Task create(String name, String type, Long projectId, Long authorId)
    {
        Task task = Task.of(name,
                TaskType.valueOf(type),
                TaskStatus.NEW,
                LocalDateTime.now(),
                LocalDateTime.now(),
                authorId,
                projectId);

        return repository.save(task);
    }

    public List<Task> getAllTasks()
    {
        return repository.findAll();
    }

    public Task editStatus(String newStatus, Long id)
    {
        Task task = repository.findById(id)
                .orElseThrow(TaskNotExist::new);

        task.setStatus(TaskStatus.valueOf(newStatus));
        task.setChangedAt(LocalDateTime.now());

        return repository.save(task);
    }

    public Task editTask(Long id, String name, String newType, String newStatus)
    {
        Task task = repository.findById(id)
                .orElseThrow(TaskNotExist::new);

        task.setName(name);
        task.setStatus(TaskStatus.valueOf(newStatus));
        task.setType(TaskType.valueOf(newType));
        task.setChangedAt(LocalDateTime.now());

        return repository.save(task);
    }

    public Long getAuthor(Long taskId)
    {
        Task task = repository.findById(taskId)
                .orElseThrow(TaskNotExist::new);

        return task.getAuthor();
    }

    public Long dropProject(Long id)
    {
        repository.deleteById(id);

        return id;
    }
}
