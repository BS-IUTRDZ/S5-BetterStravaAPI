package iut.info3.betterstravaapi.path;

import iut.info3.betterstravaapi.user.UserEntity;
import iut.info3.betterstravaapi.user.UserService;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller du parcours.
 */
@RestController
@RequestMapping(value = "/api/path")
public class PathController {

    /**
     * service d'acces a la base nosql.
     */
    @Autowired
    private final PathService pathService;

    /**
     * repository associer a la base noSQL.
     */
    @Autowired
    private final PathRepository pathRepository;

    /**
     * service d'acces a la base mysql.
     */
    @Autowired
    private final UserService userService;


    /**
     * Controlleur permettant d'autowired le pathRepository.
     *
     * @param pathRepo pathRepository a Autowired.
     * @param userServ userService a Autowired.
     * @param pathService pathService a Autowired.
     */
    public PathController(PathService pathService, final PathRepository pathRepo,
                          final UserService userServ) {
        this.pathService = pathService;
        this.pathRepository = pathRepo;
        this.userService = userServ;
    }


    /**
     * Route de création d'un utilisateur.
     * @param pathBody body de la requête au format JSON contenant les
     *                 informations permettant de créer un parcour.
     * @return un code de retour :
     * <ul>
     *     <li> 201 si le parcour est créé </li>
     *     <li> 401 si le parcours n'a pas pus etre cree </li>
     *     <li> 401 si le token est inconnue / invalide </li>
     * </ul>
     */
    @PostMapping("/createPath")
    public ResponseEntity<Object> createPath(
            @RequestBody final String pathBody,
            @RequestHeader("token") final String token) {//TODO String to PathEntity a faire

        JSONObject response = new JSONObject();

        // Authentification de l'utilisateur
        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur", "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        // Récupération de l'id de l'utilisateur via le token
        int idUser = user.getId();

        JSONObject requestBody = new JSONObject(pathBody);
        String nom = requestBody.getString("nom");
        String description = requestBody.getString("description");
        long date = requestBody.getLong("date");

        // Création du parcours
        try {
            PathEntity path = new PathEntity(idUser, nom,
                    description, date, new ArrayList<>());
            pathRepository.save(path);
            response.put("message", "parcours correctement cree");
            response.put("id", path.getId().toString());
            return new ResponseEntity<>(response.toMap(), HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("message", "erreur de creation du parcours");
            response.put("erreur", e.getMessage());
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * TODO faire la javadoc
     */
    @PostMapping("/addPoint")
    public ResponseEntity<Object> addPoint(
            @RequestBody final String pointEtId) {

        System.out.println("addpoint "+ pointEtId);

        String id = "";
        double longitude;
        double latitude;

        Map<String, String> responseBody = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(pointEtId);

            id = jsonObject.getString("id");
            longitude = jsonObject.getDouble("longitude");
            latitude = jsonObject.getDouble("latitude");
        } catch (Exception e) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("erreur", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        PathEntity parcoursVise = pathService.recupParcoursParId(new ObjectId(id));

        Coordonnees aAjouter = new Coordonnees(latitude,longitude);

        PathEntity parcoursComplet = parcoursVise.addPoint(aAjouter);
        pathRepository.save(parcoursComplet);

        try {
            responseBody.put("message", "point ajouté");
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);

        } catch (Exception e) {
            responseBody.put("message", "erreur d'ajout du point'");
            responseBody.put("erreur", e.getMessage());
            return new ResponseEntity<>(responseBody,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
