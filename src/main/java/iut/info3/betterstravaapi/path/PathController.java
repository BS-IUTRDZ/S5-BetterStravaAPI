package iut.info3.betterstravaapi.path;

import iut.info3.betterstravaapi.path.jsonmodels.JsonFullPath;
import iut.info3.betterstravaapi.user.UserEntity;
import iut.info3.betterstravaapi.user.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

import java.text.ParseException;

/**
 * Controller of path related routes.
 */
@RestController
@RequestMapping(value = "/api/path")
public class PathController {

    /**
     * Access service to the noSQL database.
     */
    @Autowired
    private final PathService pathService;

    /**
     * Repository associated with the noSQL database.
     */
    @Autowired
    private final PathRepository pathRepository;

    /**
     * Access service to the SQL database.
     */
    @Autowired
    private final UserService userService;

    /**
     * Controller allowing to autowire the pathRepository.
     * @param pathRepo pathRepository to Autowire.
     * @param userServ userService to Autowire.
     * @param pathServ pathService to Autowire.
     */
    public PathController(final PathRepository pathRepo,
                          final UserService userServ,
                          final PathService pathServ) {
        this.pathRepository = pathRepo;
        this.userService = userServ;
        this.pathService = pathServ;
    }


    /**
     * Route to create a path.
     * @param pathBody body of the request in JSON format containing the
     *                 information to create a path.
     * @param token access token of the user
     * @return a return code :
     * <ul>
     *     <li> 201 if the path has been created </li>
     *     <li> 401 if the path cannot be created </li>
     *     <li> 401 if the token is invalid </li>
     * </ul>
     */
    @PostMapping("/createPath")
    public ResponseEntity<Object> createPath(
            @RequestBody @Valid final JsonFullPath pathBody,
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();

        // Auth the user
        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur", "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        // Creating and saving the path in the database.
        PathEntity entity = new PathEntity(user.getId(), pathBody);
        entity.computeStatistics();
        pathRepository.save(entity);

        response.put("message", "parcours correctement cree");

        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
    }

    /**
     * Route to search for a path.
     * @param nom name of the searched path
     * @param dateInf minimal date of the path in the format dd/mm/yyyy
     * @param dateSup maximal date of the path in the format dd/mm/yyyy
     * @param token access token of the user
     * @param distanceMin minimal distance of the searched path
     * @param distanceMax maximal distance of the searched path
     * @param nbPathAlreadyLoaded number of paths already loaded
     *                            (used for pagination)
     * @throws ParseException if the date are not in the correct format
     * @return The list of matched path in JSON. Return code :
     * <ul>
     *     <li> 200 if the fields are correctly filled </li>
     *     <li> 400 otherwise </li>
     * </ul>
     */
    @GetMapping("/findPath")
    public ResponseEntity<List<PathEntity>> findPath(
            @RequestParam("nom") final String nom,
            @RequestParam("dateInf") final String dateInf,
            @RequestParam("dateSup") final String dateSup,
            @RequestParam("distanceMin") final int distanceMin,
            @RequestParam("distanceMax") final int distanceMax,
            @RequestHeader(value = "nbPathAlreadyLoaded")
            final int nbPathAlreadyLoaded,
            @RequestHeader("token") final String token) throws ParseException {

        if (!userService.isTokenNotExpired(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        int userId = userService.findUserByToken(token).getId();
        List<PathEntity> entities;
        if (distanceMax != 0 || distanceMin != 0) {
            entities = pathService
                    .findParcourByDateAndNameAndDistance(nom, dateInf,
                            dateSup, distanceMin, distanceMax, userId,
                            nbPathAlreadyLoaded);
        } else {
            entities = pathService.findPathsByDateAndName(
                    nom, dateInf, dateSup, userId, nbPathAlreadyLoaded);
        }

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }


    /**
     * Route for getting the last path of the user in Json format.
     * @param token token of the user
     * @return The last path in JSON. Return code :
     * <ul>
     *     <li> 200 if the fields are correctly filled </li>
     *     <li> 401 if the user token is unknown / invalid </li>
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

        PathEntity lastPath =
                pathService.getLastPath(user.getId());

        return new ResponseEntity<>(lastPath, HttpStatus.OK);
    }

    /**
     * Route edit the description of a path.
     * @param pathBody body of the request in JSON format containing the
     *                 id of the path
     * @param token access token of the user
     * @return a return code :
     * <ul>
     *     <li> 200 if the description has been modified </li>
     *     <li> 401 if the user token is unknown / invalid </li>
     *     <li> 400 if the path id is invalid </li>
     *     <li> 500 if an internal error occurred </li>
     * </ul>
     */
    @PostMapping("/modifyDescription")
    public ResponseEntity<Object> modifyDescription(
            @RequestBody final PathEntity pathBody,
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();
        ObjectId id;
        String description;

        // Auth the user
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

        // Getting the path id to modify
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

        // Modifying the description
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
     * Route to archive a path.
     * @param pathEntity shortened version of a pathEntity with its id
     * @param token access token of the user
     * @return a return code :
     * <ul>
     *     <li> 200 if the path has been archived </li>
     *     <li> 401 if the user token is unknown / invalid </li>
     *     <li> 400 if the path id is invalid </li>
     *     <li> 500 if an internal error occurred </li>
     * </ul>
     */
    @PutMapping("/archivingPath")
    public ResponseEntity<Object> archivingPath(
            @RequestBody final PathEntity pathEntity,
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();

        // Auth the user
        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur",
                    "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                    HttpStatus.UNAUTHORIZED);
        }

        // Archiving the path
        try {
            PathEntity parcoursVise =
                    pathService.recupParcoursParId(pathEntity.getId(),
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
     * Getting a path by its id.
     * @param id id of the path
     * @param token token of the user
     * @return the path in JSON format. return code :
     * <ul>
     *  <li> 200 if the path has been found </li>
     *  <li> 401 if the user token is unknown / invalid </li>
     *  <li> 404 if no path corresponds to this id </li>
     *  <li> 500 if an internal error occurred </li>
     * </ul>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPath(
            @PathVariable(name = "id") final String id,
            @RequestHeader("token") final String token) {

        JSONObject response = new JSONObject();
        // Auth the user
        UserEntity user = userService.findUserByToken(token);
        if (user == null) {
            response.put("erreur", "Aucun utilisateur correspond à ce token");
            return new ResponseEntity<>(response.toMap(),
                                        HttpStatus.UNAUTHORIZED);
        }

        PathEntity path = pathService.recupParcoursParId(new ObjectId(id),
                                                         user.getId());

        if (path == null) {
            response.put("erreur", "Aucun parcours correspondant à cet id");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(path, HttpStatus.OK);
    }
}
