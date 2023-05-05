package i.m.allesssandro.projectmanager.projectManager.tasks;

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
}
