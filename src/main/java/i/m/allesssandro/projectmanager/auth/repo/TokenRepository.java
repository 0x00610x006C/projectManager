package i.m.allesssandro.projectmanager.auth.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long>
{
    List<Token> findByUserName(Long userName);
}
