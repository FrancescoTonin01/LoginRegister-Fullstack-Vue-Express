package it.unife.ingsw202324.MicroservizioBase.services;
import it.unife.ingsw202324.MicroservizioBase.models.UserUpdateRequest;
import  it.unife.ingsw202324.MicroservizioBase.models.User;
import  it.unife.ingsw202324.MicroservizioBase.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Usa BCrypt per l'hashing delle password
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        // Hashing della password prima di salvarla
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        userRepository.saveUser(user.getUsername(), hashedPassword);
    }

    public void updateUser(UserUpdateRequest user) {
        // Hashing della nuova password se fornita
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        userRepository.updateUser(user.getUsername(), hashedPassword, user.getOldUsername());
    }

    // Metodo per validare la password durante il login
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
