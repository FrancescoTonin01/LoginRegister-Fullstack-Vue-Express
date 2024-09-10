package it.unife.ingsw202324.MicroservizioBase.services;
import it.unife.ingsw202324.MicroservizioBase.models.User;
import it.unife.ingsw202324.MicroservizioBase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        userRepository.saveUser(user.getUsername(), user.getEmail(), hashedPassword, user.getBirthdate(), user.getGender());
    }

    public void updateUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        userRepository.updateUser(user.getUsername(), user.getEmail(), hashedPassword, user.getBirthdate(), user.getGender());
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
