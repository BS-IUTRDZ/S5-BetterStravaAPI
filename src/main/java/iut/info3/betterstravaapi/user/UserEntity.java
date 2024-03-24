package iut.info3.betterstravaapi.user;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Entity representing an user.
 * A user is defined by :
 * <ul>
 *     <li>an id,</li>
 *     <li>an email,</li>
 *     <li>an last name,</li>
 *     <li>an first name,</li>
 *     <li>and a password.</li>
 * </ul>
 */
@Entity
@Table(name = "utilisateurs")
public class UserEntity {

    /**
     * Minimum size of a password entered by the user.
     */
    private static final int PASSWORD_MIN_SIZE = 8;
    /**
     * Maximum size of a password entered by the user.
     */
    private static final int PASSWORD_MAX_SIZE = 80;
    /**
     * Maximum size of a field (first name, last name).
     */
    private static final int MAX_FIELD_SIZE = 50;
    /**
     * Maximum size of an email.
     */
    private static final int EMAIL_MAX_SIZE = 100;

    /**
     * User id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Email of the user.
     */
    @NotEmpty(message = "Le champ \"email\" est obligatoire et "
            + "ne doit pas être vide")
    @Size(min = 2, max = EMAIL_MAX_SIZE, message = "L' \"email\" doit faire "
            + "entre 2 et 100 caractères")
    private String email;

    /**
     * Last name of the user.
     */
    @NotEmpty(message = "Le champ \"nom\" est obligatoire et "
            + "ne doit pas être vide")
    @Size(min = 2, max = MAX_FIELD_SIZE, message = "Le \"nom\" doit faire "
            + "entre 2 et 50 caractères")
    private String nom;

    /**
     * Fist name of the user.
     */
    @NotEmpty(message = "Le champ \"prenom\" est obligatoire "
            + "et ne doit pas être vide")
    @Size(min = 2, max = MAX_FIELD_SIZE, message = "Le \"prenom\" doit faire "
            + "entre 2 et 50 caractères")
    private String prenom;

    /**
     * Password of the user.
     */
    @NotEmpty(message = "Le champ \"mot de passe\" est obligatoire et "
            + "ne doit pas être vide")
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE,
            message = "Le \"mot de passe\" doit faire entre 8 et 80 caractères")
    private String motDePasse;

    /**
     * Token of the user.
     */
    private String jwtToken;

    /**
     * Default constructor.
     */
    public UserEntity() { }

    /**
     * Constructor of the user.
     * @param userEmail email of the user to create
     * @param userLastName last name of the user to create
     * @param userFirstName first name of the user to create
     * @param userPassword password of the user to create
     */
    public UserEntity(final String userEmail, final String userLastName,
                      final String userFirstName, final String userPassword) {
        this.email = userEmail;
        this.nom = userLastName;
        this.prenom = userFirstName;
        this.motDePasse = userPassword;

    }

    /**
     * Constructor of the user with a token.
     * @param userEmail email of the user to create
     * @param userLastName last name of the user to create
     * @param userFirstName first name of the user to create
     * @param userPassword password of the user to create
     * @param token token of the user to create
     */
    public UserEntity(final String userEmail, final String userLastName,
                      final String userFirstName, final String userPassword,
                      final String token) {
        this.email = userEmail;
        this.nom = userLastName;
        this.prenom = userFirstName;
        this.motDePasse = userPassword;
        this.jwtToken = token;
    }

    /** @return if of the user */
    public Integer getId() {
        return id;
    }

    /**
     * Set the id of the user.
     * @param newId the new id
     */
    public void setId(final Integer newId) {
        this.id = newId;
    }

    /** @return the email of the user */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user.
     * @param newEmail the new email
     */
    public void setEmail(final String newEmail) {
        this.email = newEmail;
    }

    /** @return the last name of the user */
    public String getNom() {
        return nom;
    }

    /**
     * Set the last name of the user.
     * @param newLastName the new last name
     */
    public void setNom(final String newLastName) {
        this.nom = newLastName;
    }
    /** @return the first name of the user */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Set the first name of the user.
     * @param newFirstName the new first name
     */
    public void setPrenom(final String newFirstName) {
        this.prenom = newFirstName;
    }

    /** @return the password of the user (SHA256) */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Set the password of the user (SHA256).
     * @param newPassword the new password
     */
    public void setMotDePasse(final String newPassword) {
        this.motDePasse = newPassword;
    }

    /** @return the token of the user */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Set the token of the user.
     * @param token le nouveau token
     */
    public void setJwtToken(final String token) {
        this.jwtToken = token;
    }

    /**
     * to string method.
     * @return the string representation of the user
     */
    @Override
    public String toString() {
        return "UserEntity{"
                + "id=" + id
                + ", email='" + email + '\''
                + ", nom='" + nom + '\''
                + ", prenom='" + prenom + '\''
                + ", motDePasse='" + motDePasse + '\''
                + '}';
    }

}
