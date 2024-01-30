package iut.info3.betterstravaapi.path;

import iut.info3.betterstravaapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/path")
public class PathController {

    @Autowired
    private final PathRepository pathRepository;

    @Autowired
    private final UserService userService;


    /**
     * Controlleur permettant d'autowired le pathRepository.
     *
     * @param pathRepository pathRepository a Autowired.
     * @param userService userService a Autowired.
     */
    public PathController(PathRepository pathRepository, UserService userService) {
        this.pathRepository = pathRepository;
        this.userService = userService;
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
    public ResponseEntity<Object> createPath(@RequestBody final PathEntity pathBody){

        Integer idUser =pathBody.getIdUserParcour();
        String description = pathBody.getDescription();
        String nom = pathBody.getNom();
        List<Coordonnees> points = pathBody.getPoints();
        List<PointInteret> pointInteret = pathBody.getPointsInterets();

        Map<String, String> responseBody = new HashMap<>();

        if (!userService.verifierDateExpiration(userService.getTokenBd(idUser))){
            responseBody.put(
                    "Message",
                    "l'utilisateur ne possede pas de token valide");
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }

        try {
            PathEntity path = new PathEntity(idUser, nom, description, points, pointInteret);
            pathRepository.save(path);
            responseBody.put("message", "parcours correctement cree");
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);

        }catch (Exception e){
            responseBody.put("message", "erreur de creation du parcours");
            responseBody.put("erreur",e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    //TODO methode d'ajout d'un point de coordonnees dans la list des points d'un parcours grace a son id


    @GetMapping("/all")
    public List<PathEntity> getAll() {
        return pathRepository.findAll();
    }

}
