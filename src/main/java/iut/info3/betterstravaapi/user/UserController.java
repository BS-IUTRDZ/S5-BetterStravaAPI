package iut.info3.betterstravaapi.user;

import iut.info3.betterstravaapi.path.PathService;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller of user related routes.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    /** Error message when user is not found. */
    private static final String ERROR_MESSAGE_USER_NOT_FOUND
            = "Utilisateur inconnu(e)";

    /**
     * Service to manage paths.
     */
    @Autowired
    private PathService pathService;

    /**
     * Repository associated to the user table in the MySQL database.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Service to manage users.
     */
    @Autowired
    private UserService userService;


    /**
     * Method to authenticate a user.
     *
     * @param email email entered by the user
     * @param password password entered by the user
     * @return the new token of the user. Response code :
     * <ul>
     *     <li> 202 if the connection is successful</li>
     *     <li> 401 if the user is not found</li>
     * </ul>
     */
    @GetMapping(path = "/login")
    public ResponseEntity<Object> authenticate(
            @RequestParam("email") final String email,
            @RequestParam("password") final String password) {

        String passwordEncode = DigestUtils.sha256Hex(password);
        UserEntity user =
                userService.findByEmailAndPassword(email, passwordEncode);
        Map<String, String> responseBody = new HashMap<>();

        if (user == null) {
            responseBody.put("erreur", ERROR_MESSAGE_USER_NOT_FOUND);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
        String token = userService.generateToken(user,
                Instant.now());
        responseBody.put("token", token);
        user.setJwtToken(token);
        userRepository.save(user);

        return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
    }

    /**
     * Method to create a user.
     * @param userBody the body of the request containing the user information
     * @return a response code :
     * <ul>
     *     <li> 201 if the user is created</li>
     *     <li> 409 if the email is already used</li>
     *     <li> 400 if the body is not valid</li>
     * </ul>
     */
    @PostMapping (path = "/createAccount")
    public ResponseEntity<Object> createAccount(
            @Validated @RequestBody final UserEntity userBody) {

        String email = userBody.getEmail();
        String lastname = userBody.getNom();
        String firstname = userBody.getPrenom();
        String password = userBody.getMotDePasse();

        Map<String, String> responseBody = new HashMap<>();

        if (userService.emailExists(email)) {
            responseBody.put(
                    "Message",
                    "Un utilisateur existe déjà pour cette adresse email");
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        String encryptedPassword = DigestUtils.sha256Hex(password);

        UserEntity user = new UserEntity(email, lastname, firstname,
                                        encryptedPassword);
        userRepository.save(user);

        responseBody.put("message", "Utilisateur correctement insérer");
        responseBody.put("utilisateur", user.toString());
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    /**
     * Error management for malformed body in received requests.
     * @param ex exception trigger
     * @return a 400 return code with the error message
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


    /**
     * Method to get the necessary information for the home page.
     * @param body the body of the request containing the token
     * @return the information of the user. Response code :
     * <ul>
     *     <li>200 if the information is returned</li>
     *     <li>401 if the token is not valid</li>
     *     <li>400 if the body is not valid</li>
     * </ul>
     */
    @PostMapping (path = "/getInfo")
    public ResponseEntity<Object> recupInfo(
            @RequestBody final String body) {

        String token = "";
        HashMap<String, HashMap> reponse = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(body.toString());

            // Get the token from the body
            token = jsonObject.getString("token");
        } catch (Exception e) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("erreur", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = userService.findUserByToken(token);
        HashMap<String, String> infoUser = new HashMap<>();
        infoUser.put("nom", user.getNom());
        infoUser.put("prenom", user.getPrenom());
        infoUser.put("email", user.getEmail());

        HashMap<String, String> statsLastMonth =
                userService.calculerPerformance(
                        pathService.getPathsLastMonth(user.getId()));

        HashMap<String, String> statsGlobal = userService.calculerPerformance(
                pathService.getAllPaths(user.getId()));

        reponse.put("user", infoUser);
        reponse.put("30jours", statsLastMonth);
        reponse.put("global", statsGlobal);

        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }

}
