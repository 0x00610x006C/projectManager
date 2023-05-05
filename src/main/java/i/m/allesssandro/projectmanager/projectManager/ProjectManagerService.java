package i.m.allesssandro.projectmanager.projectManager;

import i.m.allesssandro.projectmanager.projectManager.exceptions.ProjectNotExist;
import i.m.allesssandro.projectmanager.projectManager.repo.Project;
import i.m.allesssandro.projectmanager.projectManager.repo.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManagerService
{
    private final ProjectRepository repository;

    public ProjectManagerService(ProjectRepository repository)
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

    public List<Long> dropProject(Long id)
    {
        List<Project> children = repository.findAllByParentProjectId(id);

        List<Long> subprojectIndexes = new ArrayList<>();

        for (Project project : children)
        {
            //todo не забыть про удаление задач которые находятся в удаляемом проекте
            long childrenId = project.getId();
            subprojectIndexes.addAll(dropProject(childrenId));
            subprojectIndexes.add(childrenId);
        }

        repository.deleteById(id);

        return subprojectIndexes;
    }

    public Project editProject(Long id, String name)
    {
        Project project = repository.findById(id).orElseThrow(ProjectNotExist::new);

        project.setName(name);

        return repository.save(project);
    }
}
