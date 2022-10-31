package authservice.data;

import authservice.service.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository abstracts a collection of User objects.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
