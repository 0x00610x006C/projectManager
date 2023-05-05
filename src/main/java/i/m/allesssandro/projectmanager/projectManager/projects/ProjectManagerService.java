package i.m.allesssandro.projectmanager.projectManager.projects;

import i.m.allesssandro.projectmanager.projectManager.projects.errors.ProjectNotExist;
import i.m.allesssandro.projectmanager.projectManager.tasks.Task;
import i.m.allesssandro.projectmanager.projectManager.tasks.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManagerService
{
    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    public ProjectManagerService(ProjectRepository projectRepository, TaskRepository taskRepository)
    {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public List<Project> getAllProjects()
    {
        return projectRepository.findAll();
    }

    public Project create(String name, Long parentId)
    {
        return projectRepository.save(Project.of(name, parentId));
    }

    public List<DeletedProject> dropProject(Long id)
    {
        DeletedProject deletedProject = new DeletedProject(id, dropTasks(id));

        List<Project> children = projectRepository.findAllByParentProjectId(id);

        List<DeletedProject> deletedSubprojects = new ArrayList<>(List.of(deletedProject));

        for (Project child : children)
        {
            deletedSubprojects.addAll(dropProject(child.getId()));
        }

        projectRepository.deleteById(id);

        return deletedSubprojects;
    }

    private List<Long> dropTasks(Long id)
    {
        List<Task> tasks = taskRepository.findAllByParentProjectId(id);

        List<Long> listOfDeletedTaskId = new ArrayList<>();

        for (Task task : tasks)
        {
            long taskId = task.getId();
            taskRepository.deleteById(taskId);
            listOfDeletedTaskId.add(taskId);
        }

        return listOfDeletedTaskId;
    }

    public Project editProject(Long id, String name)
    {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotExist::new);

        project.setName(name);

        return projectRepository.save(project);
    }
}
