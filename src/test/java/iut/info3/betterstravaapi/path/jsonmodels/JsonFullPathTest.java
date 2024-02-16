package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFullPathTest {

    private JsonFullPath path;

    @BeforeEach
    void setUp() {

        String pathJson = "{\"nom\":\"parcours de test\",\"description\":\"description du parcours\",\"date\":1708005866,\"points\":[{\"lat\":24.7162,\"lon\":-12.7261,\"alt\":1330.62}],\"pointsInterets\":[{\"pos\":{\"lat\":1,\"lon\":2,\"alt\":3},\"nom\":\"points d'interet\",\"description\":\"description du point d'interet\"}],\"duree\":1996}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            path = mapper.readValue(pathJson, JsonFullPath.class);
        } catch (Exception e) {
            fail("Impossible de creer le parcours de test");
        }

    }

    @Test
    void getNom() {
        assertEquals("parcours de test", path.getNom());
    }

    @Test
    void getDescription() {
        assertEquals("description du parcours", path.getDescription());
    }

    @Test
    void getDate() {
        assertEquals(1708005866, path.getDate());
    }

    @Test
    void getDuree() {
        assertEquals(1996, path.getDuree());
    }

    @Test
    void getPoints() {
        assertEquals(1, path.getPoints().size());
    }

    @Test
    void pointsToCoordonnees() {
        assertEquals(1, path.pointsToCoordonnees().size());

        assertEquals(24.7162, path.pointsToCoordonnees().get(0).getLatitude());
        assertEquals(-12.7261, path.pointsToCoordonnees().get(0).getLongitude());
        assertEquals(1330.62, path.pointsToCoordonnees().get(0).getAltitude());
    }

    @Test
    void getPointsInteret() {
        assertEquals(1, path.getPointsInteret().size());

        assertEquals(1, path.getPointsInteret().get(0).getPos().toCoordonnees().getLatitude());
        assertEquals(2, path.getPointsInteret().get(0).getPos().toCoordonnees().getLongitude());
        assertEquals(3, path.getPointsInteret().get(0).getPos().toCoordonnees().getAltitude());
    }
}