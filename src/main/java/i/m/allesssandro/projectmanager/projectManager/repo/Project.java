package i.m.allesssandro.projectmanager.projectManager.repo;

import i.m.allesssandro.projectmanager.auth.repo.Token;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

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
}
