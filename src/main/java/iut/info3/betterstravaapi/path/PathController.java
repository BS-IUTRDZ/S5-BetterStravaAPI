package iut.info3.betterstravaapi.path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/path")
public class PathController {

    @Autowired
    private final PathRepository pathRepository;

    public PathController(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }


    @PostMapping("/createPath")
    public ResponseEntity<Object> createPath(@RequestBody final PathEntity pathBody){

        //TODO methode de création du parcours et return de l'id du parcours

        return null; // bouchon
    }

    //TODO methode d'ajout d'un point de coordonnees dans la list des points d'un parcours grace a son id


    @GetMapping("/all")
    public List<PathEntity> getAll() {
        return pathRepository.findAll();
    }

}
