package i.m.allesssandro.projectmanager.projectManager;

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
}
