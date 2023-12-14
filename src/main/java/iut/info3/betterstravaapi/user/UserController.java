package iut.info3.betterstravaapi.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.catalina.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private static final String ERROR_MESSAGE_USER_NOT_FOUND = "Utilisateur inconnu(e)";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    /**
     * methode d'authefication d'un utilisateur
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
        String token = userService.generateToken(listUserCo.get((0)),Instant.now());
        responseBody.put("token",token);
        return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
    }

    @PostMapping (path="/createAccount")
    public ResponseEntity<Object> createAccount(@Validated @RequestBody UserEntity userBody) {

        String email = userBody.getEmail();
        String nom = userBody.getNom();
        String prenom = userBody.getPrenom();
        String password = userBody.getMotDePasse();

        Map<String, String> responseBody = new HashMap<>();

        if (userService.checkPresenceEmail(email)) {
            responseBody.put("Message", "Un utilisateur existe déjà pour cette adresse email");
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        String passwordCrypt = DigestUtils.sha256Hex(password);

        UserEntity user = new UserEntity(email, nom, prenom, passwordCrypt);
        userRepository.save(user);

        responseBody.put("Message", "Utilisateur correctement insérer");
        responseBody.put("Utilisateur", user.toString());
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
