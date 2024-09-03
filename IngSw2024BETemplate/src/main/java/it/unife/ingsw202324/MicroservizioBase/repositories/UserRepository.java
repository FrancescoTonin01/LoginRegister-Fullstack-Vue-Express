package it.unife.ingsw202324.MicroservizioBase.repositories;

import it.unife.ingsw202324.MicroservizioBase.models.User;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
   @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("INSERT INTO User (username, password) VALUES (?1, ?2)")
    void saveUser(String username, String password);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = ?1, u.password = ?2 WHERE u.username = ?3")
    void updateUser(String username, String password, String oldusername);


}
