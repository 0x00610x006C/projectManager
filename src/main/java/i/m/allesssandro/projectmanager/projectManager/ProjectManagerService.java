package i.m.allesssandro.projectmanager.projectManager;

import i.m.allesssandro.projectmanager.projectManager.exceptions.ProjectNotExist;
import i.m.allesssandro.projectmanager.projectManager.repo.Project;
import i.m.allesssandro.projectmanager.projectManager.repo.projectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectManagerService
{
    private final projectRepository repository;

    public ProjectManagerService(projectRepository repository)
    {
        this.repository = repository;
    }

    public List<Project> getAllProjects()
    {
        return repository.findAll();
    }

    public Project create(String name, Long parentId)
    {
        return repository.save(Project.of(name, parentId));
    }

    public String dropProject(Long id)
    {
        List<Project> children = repository.findAllByParentProjectId(id);

        StringBuilder stringBuilder = new StringBuilder();

        for (Project project : children)
        {
            long childrenId = project.getId();
            stringBuilder.append(dropProject(childrenId));
            stringBuilder.append(childrenId).append(", ");
        }

        repository.deleteById(id);

        return stringBuilder.toString();
    }

    public Project editProject(Long id, String name)
    {
        Project project = repository.findById(id).orElseThrow(ProjectNotExist::new);

        project.setName(name);

        return repository.save(project);
    }
}
