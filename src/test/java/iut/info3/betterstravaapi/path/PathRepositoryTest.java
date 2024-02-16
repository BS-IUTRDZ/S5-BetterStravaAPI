package iut.info3.betterstravaapi.path;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Ces tests on besoin de la base mongodb pour passer
 */
@SpringBootTest
class PathRepositoryTest {

    @Autowired
    private PathRepository pathRepository;

    @Test
    void findPathByDateAndNameNoPathCorrect() {
        // Given Une requête d'une application android avec des paramètres qui ne
        // correspondent a aucun parcour
        String nom = "vieux";
        long dateMin = 1676023910010L;
        long dateMax = 1676023910010L;

        // When la fonction du repository est appeler avec ce paramètre
        List<PathEntity> entities = pathRepository.findEntitiesByDateAndName(dateMin, dateMax, nom, 1,false, any());
        // Then on obtient aucun parcour
        assertEquals(0,entities.size());

    }


    @Test
    void findPathByDateAndName() {
        // Given Une requête d'une application android avec des paramètres
        // qui devrait retourner un seul parcour
        String nom = "vieux";
        long dateMin = 1676023810010L;
        long dateMax = 1676023910010L;

        // When la fonction du repository est appeler avec ce paramètre
        List<PathEntity> entities = pathRepository.findEntitiesByDateAndName(dateMin, dateMax, nom, 1, false,any());
        // Then on obtient le parcours rechercher
        assertEquals(1,entities.size());
        PathEntity entity = entities.get(0);
        assertEquals(1676023810010L, entity.getDate());
        assertEquals("vieux", entity.getNom());

    }


}