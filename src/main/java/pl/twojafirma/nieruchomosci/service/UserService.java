package pl.twojafirma.nieruchomosci.service;

import pl.twojafirma.nieruchomosci.dto.UserRegistrationDto;
import pl.twojafirma.nieruchomosci.model.User;
import java.util.List;

public interface UserService {
    void saveUser(UserRegistrationDto registrationDto);
    User findByUsername(String username);
    List<User> findAllUsers();
    User findUserById(Long id);
    void updateUser(Long id, User userDto);
    void deleteUserById(Long id);
}