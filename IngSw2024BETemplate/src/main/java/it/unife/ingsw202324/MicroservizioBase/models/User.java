package it.unife.ingsw202324.MicroservizioBase.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User")
public class User {

    @Id
    private String username;
    
    private String password;

    // Constructor (puoi aggiungere ulteriori costruttori se necessario)
    public User() {
    }

    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter e Setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
