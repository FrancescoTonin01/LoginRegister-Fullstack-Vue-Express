package it.unife.ingsw202324.MicroservizioBase.api;

import it.unife.ingsw202324.MicroservizioBase.models.UserUpdateRequest;
import it.unife.ingsw202324.MicroservizioBase.models.User;
import it.unife.ingsw202324.MicroservizioBase.services.UserService;
import it.unife.ingsw202324.MicroservizioBase.services.TemplateRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && userService.checkPassword(user.getPassword(), existingUser.getPassword())) {
            return existingUser;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // Controllo se l'username esiste già
        if (userService.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        // Salva il nuovo utente con password hashata
        userService.save(user);
        return user;
    }

    @PutMapping("/update-user")
    public User updatedata(@RequestBody UserUpdateRequest userUpdateRequest) {
        // Recupera l'utente esistente tramite oldUsername
        User existingUser = userService.findByUsername(userUpdateRequest.getOldUsername());
        if (existingUser != null) {
            // Aggiorna i dettagli dell'utente, incluso l'hashing della password
            existingUser.setUsername(userUpdateRequest.getUsername());
            userService.updateUser(userUpdateRequest);
            existingUser = userService.findByUsername(userUpdateRequest.getUsername());
            return existingUser;
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
