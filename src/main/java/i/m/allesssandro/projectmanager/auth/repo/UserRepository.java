package i.m.allesssandro.projectmanager.auth.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByLogin(String email);
}
