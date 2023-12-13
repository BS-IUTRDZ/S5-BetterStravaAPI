package iut.info3.betterstravaapi.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping(path="/all")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        // This returns a JSON or XML with the users
        return userService.findAll();
    }

    @GetMapping(path = "/login")
    public String authenticate(@RequestParam String email,@RequestParam String password) {
        String passwordEncode = userService.encryptPassword(password);
        List<UserEntity> listUserCo = userService.findByEmailAndPassword(email, passwordEncode);

        if (listUserCo.size() == 0){
            return ERROR_MESSAGE_USER_NOT_FOUND;
        }
        return userService.generateToken(listUserCo.get((0)));
    }

    @PostMapping (path="/createAccount")
    public ResponseEntity<Object> getAllUsers(@Validated @RequestBody UserEntity userBody) {

        String email = userBody.getEmail();
        String nom = userBody.getNom();
        String prenom = userBody.getPrenom();
        String password = userBody.getMotDePasse();

        Map<String, String> responseBody = new HashMap<>();

        if (userService.checkPresenceEmail(email)) {
            responseBody.put("Message", "Un utilisateur existe déjà pour cette adresse email");
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        String passwordCrypt = userService.encryptPassword(password);

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
