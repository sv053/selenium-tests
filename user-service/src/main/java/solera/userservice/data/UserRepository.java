package solera.userservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import solera.userservice.service.User;

/**
 * A UserRepository abstracts a collection of User objects.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
