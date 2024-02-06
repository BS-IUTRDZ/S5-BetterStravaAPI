package iut.info3.betterstravaapi.path;

import iut.info3.betterstravaapi.user.UserEntity;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

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

    /**
     * Route de recherche d'un parcour.
     * @param nom nom du parcour rechercher
     * @param dateInf TODO Définir le format
     * @param dateSup TODO Définir le format
     * @param token token d'identification de l'utilisateur
     * @return un code de retour :
     * <ul>
     *     <li> 200 si les champs sont correctement renseigné</li>
     *     <li> 400 sinon </li>
     * </ul>
     */
    @GetMapping("/findPath")
    public ResponseEntity<List<PathEntity>> findPath(
            @RequestParam("nom") final String nom,
            @RequestParam("dateInf") final String dateInf,
            @RequestParam("dateSup") final String dateSup,
            @RequestHeader("token") final String token) {

        if (dateSup.isEmpty() || dateInf.isEmpty() || nom.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int userId = userService.findUserByToken(token).getId();
        List<PathEntity> entities = pathService
                .findParcourByDateAndName(nom, dateInf, dateSup, userId);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }


    /**
     * Route de récupération du dernier parcours au format Json.
     * @param token token d'identification de l'utilisateur
     * @return un code de retour :
     * <ul>
     *     <li> 200 si les champs sont correctement renseigné</li>
     *     <li> 401 si token de l'utilisateur inconnu </li>
     *     <li> 401 si token en parametre incorrect </li>
     * </ul>
     */
    @PostMapping("/lastPath")
    public ResponseEntity<Object> getLastPath(
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();

        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur", "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        PathEntity dernierParcours =
                pathService.recupDernierParcour(user.getId());
        JSONObject pathJson = pathService.getPathInfos(dernierParcours);
        return new ResponseEntity<>(pathJson.toMap(), HttpStatus.OK);
    }

    /**
     * Route de modification de la description d'un parcours.
     * @param pathBody body de la requête au format JSON contenant les
     *                 informations permettant de modifier un parcour.
     * @param token token d'identification de l'utilisateur
     * @return un code de retour :
     * <ul>
     *     <li> 200 si le parcour a été modifié </li>
     *     <li> 401 si le token de l'utilisateur est inconnu / invalide </li>
     *     <li> 400 si l'id du parcours est invalide </li>
     *     <li> 500 si une erreur interne est survenue
     *     lors de la modification </li>
     * </ul>
     */
    @PostMapping("/modifyDescription")
    public ResponseEntity<Object> modifyDescription(
            @RequestBody final PathEntity pathBody,
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();
        ObjectId id;
        String description;

        // Authentification de l'utilisateur
        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur", "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        try {
            id = pathBody.getId();
            description = pathBody.getDescription();
        } catch (Exception e) {
            response.put("erreur", e.getMessage());
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.BAD_REQUEST);
        }

        // Récupération du parcours designé par l'id
        PathEntity path;
        try {
            path = pathRepository.findById(id).orElse(null);
        } catch (Exception e) {
            path = null;
        }

        if (path == null) {
            response.put("erreur", "Aucun parcours ne correspond à cet id");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.BAD_REQUEST);
        }

        // Modification de la description
        try {
            path.setDescription(description);
            pathRepository.save(path);
        } catch (Exception e) {
            response.put("erreur", e.getMessage());
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
    }

}
