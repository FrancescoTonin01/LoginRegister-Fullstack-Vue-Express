package it.unife.ingsw202324.MicroservizioBase.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "User")
public class User {

    @Id
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private LocalDate birthdate;
    
    @Column(nullable = false)
    private String gender;

    // Costruttore vuoto
    public User() {
    }

    // Costruttore completo
    public User(String username, String email, String password, LocalDate birthdate, String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    // Getter e Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender != null && !Arrays.asList("male", "female", "other").contains(gender.toLowerCase())) {
            throw new IllegalArgumentException("Genere non valido. Deve essere male, female o other.");
        }
        this.gender = gender != null ? gender.toLowerCase() : null;
    }
}
