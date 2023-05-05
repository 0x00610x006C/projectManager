package i.m.allesssandro.projectmanager.projectManager.tasks;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
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
    @Column("parent_project")
    private Long parentProjectId;
}
