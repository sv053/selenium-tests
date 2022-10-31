package air.userservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import air.userservice.service.User;

/**
 * A UserRepository abstracts a collection of User objects.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
