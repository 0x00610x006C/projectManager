package i.m.allesssandro.projectmanager.projectManager.tasks;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>
{

}
