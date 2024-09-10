package it.unife.ingsw202324.MicroservizioBase.repositories;

import it.unife.ingsw202324.MicroservizioBase.models.User;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
   @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("INSERT INTO User (username, email, password, birthdate, gender) VALUES (?1, ?2, ?3, ?4, ?5)")
    void saveUser(String username, String email, String password, LocalDate birthdate, String gender);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = ?1, u.email = ?2, u.password = ?3, u.birthdate = ?4, u.gender = ?5 WHERE u.email = ?2")
    void updateUser(String username, String email, String password, LocalDate birthdate, String gender);

}
