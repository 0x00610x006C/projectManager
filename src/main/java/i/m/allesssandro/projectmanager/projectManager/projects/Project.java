package i.m.allesssandro.projectmanager.projectManager.projects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Table(value = "projects")
public class Project
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
    @Column("parent_project")
    private Long parentProjectId;

    public static Project of(String name, Long parentProjectId)
    {
        return new Project(null, name, parentProjectId);
    }

    @PersistenceConstructor
    public Project(Long id, String name, Long parentProjectId)
    {
        this.id = id;
        this.name = name;
        this.parentProjectId = parentProjectId;
    }

}
