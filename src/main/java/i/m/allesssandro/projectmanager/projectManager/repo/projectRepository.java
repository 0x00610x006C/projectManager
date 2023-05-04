package i.m.allesssandro.projectmanager.projectManager.repo;


import i.m.allesssandro.projectmanager.projectManager.repo.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface projectRepository extends CrudRepository<Project, Long>
{
    @Override
    List<Project> findAll();

    List<Project> findAllByParentProjectId(Long id);
}
