package iut.info3.betterstravaapi.user;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Entité représentant un utilisateur.
 * Un utilisateur est caractérisé par :
 * <ul>
 *     <li>un identifiant,</li>
 *     <li>un email,</li>
 *     <li>un nom,</li>
 *     <li>un prénom,</li>
 *     <li>et un mot de passe</li>
 * </ul>
 */
@Entity
@Table(name = "utilisateurs")
public class UserEntity {

    /**
     * Taille minimale d'un mot de passe saisi par l'utilisateur.
     */
    private static final int MIN_CHAMP_MDP = 8;
    /**
     * Taille maximale d'un mot de passe saisi par l'utilisateur.
     */
    private static final int MAX_CHAMP_MDP = 80;
    /**
     * Taille maximale du champ (nom, prénom).
     */
    private static final int MAX_CHAMP = 50;
    /**
     * Taille maximale du champ email.
     */
    private static final int MAX_CHAMP_EMAIL = 100;

    /**
     * Id de l'utilisateur en base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Email d'un utilisateur.
     */
    @NotEmpty(message = "Le champ \"email\" est obligatoire et "
            + "ne doit pas être vide")
    @Size(min = 2, max = MAX_CHAMP_EMAIL, message = "L' \"email\" doit faire "
            + "entre 2 et 100 caractères")
    private String email;

    /**
     * Nom de l'utilisateur.
     */
    @NotEmpty(message = "Le champ \"nom\" est obligatoire et "
            + "ne doit pas être vide")
    @Size(min = 2, max = MAX_CHAMP, message = "Le \"nom\" doit faire "
            + "entre 2 et 50 caractères")
    private String nom;

    /**
     * Prénom de l'utilisateur.
     */
    @NotEmpty(message = "Le champ \"prenom\" est obligatoire "
            + "et ne doit pas être vide")
    @Size(min = 2, max = MAX_CHAMP, message = "Le \"prenom\" doit faire "
            + "entre 2 et 50 caractères")
    private String prenom;

    /**
     * Mot de passe de l'utilisateur.
     */
    @NotEmpty(message = "Le champ \"mot de passe\" est obligatoire et "
            + "ne doit pas être vide")
    @Size(min = MIN_CHAMP_MDP, max = MAX_CHAMP_MDP,
            message = "Le \"mot de passe\" doit faire entre 8 et 80 caractères")
    private String motDePasse;

    /**
     * Token de validite du compte.
     */
    private String jwtToken;

    /**
     * Constructeur par défaut pour permettre la compilation.
     */
    public UserEntity() { }

    /**
     * Création d'un utilisateur.
     * @param adresseEmail adresse email de l'utilisateur à créer
     * @param nomUtil nom de l'utilisateur à créer
     * @param prenomUtil prénom de l'utilisateur à créer
     * @param motDePasseUtil mot de passe de l'utilisateur à créer
     */
    public UserEntity(final String adresseEmail, final String nomUtil,
                      final String prenomUtil, final String motDePasseUtil) {
        this.email = adresseEmail;
        this.nom = nomUtil;
        this.prenom = prenomUtil;
        this.motDePasse = motDePasseUtil;

    }

    /**
     * Création d'un utilisateur avec un token.
     * @param adresseEmail adresse email de l'utilisateur à créer
     * @param nomUtil nom de l'utilisateur à créer
     * @param prenomUtil prénom de l'utilisateur à créer
     * @param motDePasseUtil mot de passe de l'utilisateur à créer
     * @param token token de connexion.
     */
    public UserEntity(final String adresseEmail, final String nomUtil,
                      final String prenomUtil, final String motDePasseUtil,
                      final String token) {
        this.email = adresseEmail;
        this.nom = nomUtil;
        this.prenom = prenomUtil;
        this.motDePasse = motDePasseUtil;
        this.jwtToken = token;
    }

    /** @return l'id de l'utilisateur */
    public Integer getId() {
        return id;
    }

    /**
     * Associe un id à l'utilisateur.
     * @param idUtil le nouvel id
     */
    public void setId(final Integer idUtil) {
        this.id = idUtil;
    }

    /** @return l'email de l'utilisateur */
    public String getEmail() {
        return email;
    }

    /**
     * Associe un email à l'utilisateur.
     * @param emailUtil le nouvel email
     */
    public void setEmail(final String emailUtil) {
        this.email = emailUtil;
    }

    /** @return le nom de l'utilisateur */
    public String getNom() {
        return nom;
    }

    /**
     * Associe un nom à l'utilisateur.
     * @param nomUtil le nouveau nom
     */
    public void setNom(final String nomUtil) {
        this.nom = nomUtil;
    }
    /** @return le prénom de l'utilisateur */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Associe un prénom à l'utilisateur.
     * @param prenomUtil le nouveau prénom
     */
    public void setPrenom(final String prenomUtil) {
        this.prenom = prenomUtil;
    }

    /** @return le mot de passe (SHA256) de l'utilisateur */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Associe un email à l'utilisateur.
     * @param motDePasseUtil le nouveau mot de passe
     */
    public void setMotDePasse(final String motDePasseUtil) {
        this.motDePasse = motDePasseUtil;
    }

    /** @return le token de l'utilisateur */
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Associe un token à l'utilisateur.
     * @param token le nouveau token
     */
    public void setJwtToken(final String token) {
        this.jwtToken = token;
    }

    /**
     * to string d'un UserEntity.
     * @return un string du UsrEntity
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
