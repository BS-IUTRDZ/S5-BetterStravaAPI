package iut.info3.betterstravaapi.user;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "utilisateurs")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Le champ \"email\" est obligatoire et ne doit pas être vide")
    @Size(min = 2, max = 100, message = "L' \"email\" doit faire entre 2 et 100 caractères")
    private String email;

    @NotEmpty(message = "Le champ \"nom\" est obligatoire et ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le \"nom\" doit faire entre 2 et 50 caractères")
    private String nom;

    @NotEmpty(message = "Le champ \"prenom\" est obligatoire et ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le \"prenom\" doit faire entre 2 et 50 caractères")
    private String prenom;

    @NotEmpty(message = "Le champ \"mot de passe\" est obligatoire et ne doit pas être vide")
    @Size(min = 8, max = 80, message = "Le \"mot de passe\" doit faire entre 8 et 80 caractères")
    private String motDePasse;

    /**
     * Constructeur par défaut pour permettre la compilation.
     */
    public UserEntity() { }

    /**
     *
     * @param email
     * @param nom
     * @param prenom
     * @param motDePasse
     */
    public UserEntity(String email, String nom, String prenom, String motDePasse) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
