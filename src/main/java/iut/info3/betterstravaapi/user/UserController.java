package iut.info3.betterstravaapi.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller de la partie gestion des Utilisateurs de l'API.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private static final String ERROR_MESSAGE_USER_NOT_FOUND = "Utilisateur inconnu(e)";

    /**
     * Repository associé à la table utilisateur de la base MySQL.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Service de gestion d'utilisateur.
     */
    @Autowired
    private UserService userService;


    /**
     * methode d'authefication d'un utilisateur.
     *
     * @param email email entree par l'utilisateur
     * @param password mot de pass entree par l'utilisateur
     * @return une reponse http contenant le token
     * et le code 202 si la connexion est effectuer,
     * un message d'erreur et un code 401
     */
    @GetMapping(path = "/login")
    public ResponseEntity<Object> authenticate(
            @RequestParam("email") final String email,
            @RequestParam("password") final String password) {

        String passwordEncode = DigestUtils.sha256Hex(password);
        List<UserEntity> listUserCo = userService.findByEmailAndPassword(email, passwordEncode);
        Map<String, String> responseBody = new HashMap<>();

        if (listUserCo.size() == 0){
            responseBody.put("erreur",ERROR_MESSAGE_USER_NOT_FOUND);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
        String token = userService.generateToken(listUserCo.get((0)), Instant.now());
        responseBody.put("token",token);
        return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
    }

    /**
     * Route de création d'un utilisateur.
     * @param userBody body de la requête au format JSON contenant les
     *                 informations permettant de créer un utilisateur
     * @return un code de retour :
     * <ul>
     *     <li> 201 si l'utilisateur est créé</li>
     *     <li> 409 si l'email est déjà associé à un compte</li>
     *     <li> 400 si il y a une erreur dans le body reçu</li>
     * </ul>
     */
    @PostMapping (path = "/createAccount")
    public ResponseEntity<Object> createAccount(
            @Validated @RequestBody final UserEntity userBody) {

        String email = userBody.getEmail();
        String nom = userBody.getNom();
        String prenom = userBody.getPrenom();
        String password = userBody.getMotDePasse();

        Map<String, String> responseBody = new HashMap<>();

        if (userService.checkPresenceEmail(email)) {
            responseBody.put("message", "Un utilisateur existe déjà pour "
                    + "cette adresse email");
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        String passwordCrypt = DigestUtils.sha256Hex(password);

        UserEntity user = new UserEntity(email, nom, prenom, passwordCrypt);
        userRepository.save(user);

        responseBody.put("message", "Utilisateur correctement insérer");
        responseBody.put("utilisateur", user.toString());
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    /**
     * Gestion des erreurs de body mal formé dans les requêtes reçues.
     * @param ex exception trigger
     * @return un code de retour 400 avec un message indiquant l'erreur
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            final MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = "erreur";
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }


}
