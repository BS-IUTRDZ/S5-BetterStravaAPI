package iut.info3.betterstravaapi.path;

import iut.info3.betterstravaapi.user.UserService;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     *     <li> 201 si le parcour est créé</li>
     *     <li> 401 si le parcours n'a pas pus etre cree</li>
     * </ul>
     */
    @PostMapping("/createPath")
    public ResponseEntity<Object> createPath(
            @RequestBody final String pathBody) {//TODO String to PathEntity a faire

        System.out.println(pathBody);
        JSONObject object = new JSONObject(pathBody);
        Integer idUser = object.getInt("idUtilisateur");
        String description = object.getString("description");
        String nom = object.getString("nom");
        List<Coordonnees> points = new ArrayList<>();
        JSONArray array = object.optJSONArray("points");
        points.add(new Coordonnees(array.getDouble(0), array.getDouble(1)));

        List<PointInteret> pointInteret = new ArrayList<>();
        array = object.optJSONArray("pointsInterets");
        pointInteret.add(new PointInteret("TODO", "TODO", new Coordonnees(array.getDouble(0), array.getDouble(1))));

        Map<String, String> responseBody = new HashMap<>();

        if (!userService.verifierDateExpiration(
                userService.getTokenBd(idUser))) {
            responseBody.put(
                    "Message",
                    "l'utilisateur ne possede pas de token valide");
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }

        try {
            PathEntity path = new PathEntity(idUser, nom,
                    description, points, pointInteret);
            pathRepository.save(path);
            path = pathService.recupDernierParcour(idUser);
            responseBody.put("message", "parcours correctement cree");
            responseBody.put("id", path.getId().toString());
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);

        } catch (Exception e) {
            responseBody.put("message", "erreur de creation du parcours");
            responseBody.put("erreur", e.getMessage());
            return new ResponseEntity<>(responseBody,
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
