package air.userservice.service;

import air.userservice.service.exception.IllegalModificationException;
import air.userservice.service.exception.RemoteResourceException;

import java.util.Optional;

/**
 * Provides user business logic.
 */
public interface UserService {

    /**
     * Looks for a user with the specified email in the remote user repository.
     *
     * @param email email of the user to get
     *
     * @return the user with the specified email in the remote user repository
     * or Optional#empty() if none found
     *
     * @throws RemoteResourceException if there is any problem with the remote user repository
     */
    Optional<User> findByEmail(String email);

    /**
     * Saves the specified user in the remote user repository.
     * Use the returned user for further operations as the save operation
     * might have changed the user instance completely.
     *
     * @param user user to save
     *
     * @return the saved user
     *
     * @throws IllegalModificationException either if a user has invalid data or already exists
     * @throws RemoteResourceException if there is any problem with the remote user repository
     */
    User save(User user);

    /**
     * Updates the user with the specified email in the remote user repository.
     * Use the returned user for further operations as the update operation
     * might have changed the user instance completely.
     *
     * @param user user to update
     *
     * @return the updated user
     *
     * @throws IllegalModificationException either if a user has invalid data or does not exist
     * @throws RemoteResourceException if there is any problem with the remote user repository
     */
    User update(User user);

    /**
     * Deletes the user with the specified email in the remote user repository.
     *
     * @param email the email of the user to be deleted
     *
     * @throws IllegalModificationException if such a user does not exist
     * @throws RemoteResourceException if there is any problem with the remote user repository
     */
    void deleteByEmail(String email);
}
