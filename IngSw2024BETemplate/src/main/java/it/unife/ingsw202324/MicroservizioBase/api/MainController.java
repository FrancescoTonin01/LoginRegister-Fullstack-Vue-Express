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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User existingUser = userService.findByUsername(user.getUsername());
            if (existingUser != null && userService.checkPassword(user.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.ok(existingUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il login: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Verifica se l'username esiste già
            if (userService.findByUsername(user.getUsername()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Errore durante la registrazione: Il nome utente è già in uso");
            }
            
            // Verifica se l'email esiste già
            if (userService.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Errore durante la registrazione: L'indirizzo email è già in uso");
            }

            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Errore durante la registrazione: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Errore interno durante la registrazione: " + e.getMessage());
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updatedata(@RequestBody UserUpdateRequest updateRequest) {
        try {
            User existingUser = userService.findByEmail(updateRequest.getEmail());
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
            }

            if (!userService.checkPassword(updateRequest.getCurrentPassword(), existingUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password corrente non valida");
            }

            existingUser.setUsername(updateRequest.getUsername());
            if (updateRequest.getNewPassword() != null && !updateRequest.getNewPassword().isEmpty()) {
                existingUser.setPassword(userService.encodePassword(updateRequest.getNewPassword()));
            }
            existingUser.setBirthdate(updateRequest.getBirthdate());
            existingUser.setGender(updateRequest.getGender());

            userService.updateUser(existingUser);
            return ResponseEntity.ok(existingUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore durante l'aggiornamento: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno durante l'aggiornamento: " + e.getMessage());
        }
    }
}
