package i.m.allesssandro.projectmanager.projectManager.projects;

import java.util.List;

public record DeletedProject(Long id, List<Long> tasksId)
{
}
