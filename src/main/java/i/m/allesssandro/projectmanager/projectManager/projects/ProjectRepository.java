package i.m.allesssandro.projectmanager.projectManager.projects;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>
{
    @Override
    List<Project> findAll();

    List<Project> findAllByParentProjectId(Long id);
}
