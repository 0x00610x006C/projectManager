package i.m.allesssandro.projectmanager.projectManager.tasks;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>
{
    @Override
    List<Task> findAll();

    List<Task> findAllByParentProjectId(Long parentProjectId);
}
