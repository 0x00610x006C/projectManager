package i.m.allesssandro.projectmanager.projectManager.tasks;

import i.m.allesssandro.projectmanager.projectManager.projects.Project;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@ToString
@Table(value = "tasks")
public class Task
{
    @Id
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private TaskType type;

    @Getter
    @Setter
    private TaskStatus status;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private LocalDateTime changedAt;

    @Getter
    @Setter
    private Long author;

    @Getter
    @Setter
    @Column("parent_project")
    private Long parentProjectId;

    public static Task of(String name,
                          TaskType type,
                          TaskStatus status,
                          LocalDateTime createdAt,
                          LocalDateTime changedAt,
                          Long author,
                          Long parentProjectId)
    {
        return new Task(null, name, type, status, createdAt, changedAt, author, parentProjectId);
    }

    @PersistenceConstructor
    public Task(Long id,
                String name,
                TaskType type,
                TaskStatus status,
                LocalDateTime createdAt,
                LocalDateTime changedAt,
                Long author,
                Long parentProjectId)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.changedAt = changedAt;
        this.author = author;
        this.parentProjectId = parentProjectId;
    }
}
