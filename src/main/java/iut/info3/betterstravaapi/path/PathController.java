package iut.info3.betterstravaapi.path;

import iut.info3.betterstravaapi.user.UserEntity;
import iut.info3.betterstravaapi.user.UserService;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import java.text.ParseException;

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
     * @param pathServ pathService a Autowired.
     */
    public PathController(final PathRepository pathRepo,
                          final UserService userServ,
                          final PathService pathServ) {
        this.pathRepository = pathRepo;
        this.userService = userServ;
        this.pathService = pathServ;
    }


    /**
     * Route de création d'un utilisateur.
     * @param pathBody body de la requête au format JSON contenant les
     *                 informations permettant de créer un parcour.
     * @param token token d'accès de l'utilisateur.
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
            @RequestHeader("token") final String token) {

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
                    description, date, new ArrayList<>(), new Statistiques());
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
     * Route d'ajout d'un point.
     * @param token token d'accès de l'utilisateur.
     * @param pointEtId string avec id du parcours,
     *                  longitude et latitude.
     * @return message point ajouté.
     */
    @PostMapping("/addPoint")
    public ResponseEntity<Object> addPoint(
            @RequestBody final String pointEtId,
            @RequestHeader("token") final String token) {

        System.out.println("addpoint " + pointEtId);

        String id = "";
        double longitude;
        double latitude;

        Map<String, String> responseBody = new HashMap<>();

        JSONObject response = new JSONObject();

        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur",
                    "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        try {
            JSONObject jsonObject = new JSONObject(pointEtId);

            id = jsonObject.getString("id");
            longitude = jsonObject.getDouble("longitude");
            latitude = jsonObject.getDouble("latitude");
        } catch (Exception e) {
            response.put("erreur", e.getMessage());
            return new ResponseEntity<>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PathEntity parcoursVise =
                pathService.recupParcoursParId(new ObjectId(id), user.getId());

        Coordonnees aAjouter = new Coordonnees(latitude, longitude);

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
     * @param dateInf date minimale du parcour au format jj/mm/aaaa
     * @param dateSup date maximale du parcour au format jj/mm/aaaa
     * @param token token d'identification de l'utilisateur
     * @param distanceMin distance minimale du parcours rechercher
     * @param distanceMax distance maximale du parcours rechercher
     * @throws ParseException si les dates ne sont pas au bon format
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
    @RequestParam("distanceMin") final int distanceMin,
    @RequestParam("distanceMax") final int distanceMax,
    @RequestHeader("token") final String token) throws ParseException {

        if (!userService.isTokenNotExpired(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        int userId = userService.findUserByToken(token).getId();
        List<PathEntity> entities;
        if (distanceMax != 0 || distanceMin != 0) {
            entities = pathService
                    .findParcourByDateAndNameAndDistance(nom, dateInf,
                            dateSup, distanceMin, distanceMax, userId);
        } else {
            entities = pathService.findParcourByDateAndName(
                    nom, dateInf, dateSup, userId);
        }

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
    @GetMapping("/lastPath")
    public ResponseEntity<Object> getLastPath(
    @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();

        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur",
                    "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        PathEntity dernierParcours =
                pathService.recupDernierParcour(user.getId());

        return new ResponseEntity<>(dernierParcours, HttpStatus.OK);
    }

    /**
     * Route d'archivage d'un parcours.
     * @param pathBody body de la requête au format JSON contenant
     *                l'id d'un parcours.
     * @param token token d'identification de l'utilisateur
     * @return un code de retour :
     * <ul>
     *     <li> 200 si le parcour a été archivé </li>
     *     <li> 401 si le token de l'utilisateur est inconnu/invalide </li>
     *     <li> 400 si l'id du parcours est invalide </li>
     *     <li> 500 si une erreur interne est survenue </li>
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
            response.put("erreur",
                    "Aucun utilisateur correspond à ce token");
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

    /**
     * Route d'archivage d'un parcours.
     * @param id id d'un parcours.
     * @param token token d'identification de l'utilisateur
     * @return un code de retour :
     * <ul>
     *     <li> 200 si le parcour a été archivé </li>
     *     <li> 401 si le token de l'utilisateur est inconnu/invalide </li>
     *     <li> 400 si l'id du parcours est invalide </li>
     *     <li> 500 si une erreur interne est survenue </li>
     * </ul>
     */
    @PostMapping("/archivingPath")
    public ResponseEntity<Object> archivingPath(
            @RequestBody final String id,
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();

        // Authentification de l'utilisateur
        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur",
                    "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        // Archivage du parcours
        try {
            PathEntity parcoursVise =
                    pathService.recupParcoursParId(new ObjectId(id),
                            user.getId());
            parcoursVise.setArchive(true);
            pathRepository.save(parcoursVise);

        } catch (Exception e) {
            response.put("erreur", e.getMessage());
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
    }
    /**
     * Récupération d'un parcours par son id.
     * @param id id du parcours
     * @return le parcours au format Json
     * Code de retour :
     * <ul>
     *  <li> 200 si le parcours existe </li>
     *  <li> 401 si le token de l'utilisateur est inconnu/invalide </li>
     *  <li> 404 si l'id du parcours est introuvable </li>
     *  <li> 500 si une erreur interne est survenue </li>
     * </ul>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPath(
            @PathVariable(name="id") String id,
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();
        // Authentification de l'utilisateur
        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur", "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                                        HttpStatus.UNAUTHORIZED);
        }

        PathEntity path = pathService.recupParcoursParId(new ObjectId(id), user.getId());

        if (path == null) {
            response.put("erreur", "Aucun parcours correspondant à cet id");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(path, HttpStatus.OK);
    }
}
