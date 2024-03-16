package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class JsonFullPathTest {

    private JsonFullPath jsonFullPath;
    private Field privateJsonFullPathField;

    @BeforeEach
    void setUp() {

        String pathJson = "{\"nom\":\"parcours de test\",\"description\":\"description du parcours\",\"date\":1708005866,\"points\":[{\"lat\":24.7162,\"lon\":-12.7261,\"alt\":1330.62}],\"pointsInterets\":[{\"pos\":{\"lat\":1,\"lon\":2,\"alt\":3},\"nom\":\"points d'interet\",\"description\":\"description du point d'interet\"}],\"duree\":1996}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonFullPath = mapper.readValue(pathJson, JsonFullPath.class);
        } catch (Exception e) {
            fail("Impossible de creer le parcours de test");
        }

    }

    @Test
    void getNom() {
        assertEquals("parcours de test", jsonFullPath.getNom());
    }

    @Test
    void getDescription() {
        assertEquals("description du parcours", jsonFullPath.getDescription());
    }

    @Test
    void getDate() {
        assertEquals(1708005866, jsonFullPath.getDate());
    }

    @Test
    void getDuree() {
        assertEquals(1996, jsonFullPath.getDuree());
    }

    @Test
    void getPoints() {
        assertEquals(1, jsonFullPath.getPoints().size());
    }

    @Test
    void pointsToCoordonnees() {
        assertEquals(1, jsonFullPath.pointsToCoordonnees().size());

        assertEquals(24.7162, jsonFullPath.pointsToCoordonnees().get(0).getLatitude());
        assertEquals(-12.7261, jsonFullPath.pointsToCoordonnees().get(0).getLongitude());
        assertEquals(1330.62, jsonFullPath.pointsToCoordonnees().get(0).getAltitude());
    }

    @Test
    void getPointsInteret() {
        assertEquals(1, jsonFullPath.getJsonPointsInteret().size());

        Field privatePointInteretField = null;
        try {
            // Accès au champ privé
            privatePointInteretField = JsonPointInteret.class.getDeclaredField("pos");
            privatePointInteretField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            fail("Le champ pos n'existe pas");
        }

        JsonPoint jsonPoint = null;
        try {
            jsonPoint = (JsonPoint) privatePointInteretField.get(jsonFullPath.getJsonPointsInteret().get(0));
        } catch (IllegalAccessException e) {
            fail("Impossible d'accéder au champ privé pos");
        }

        assertEquals(1, jsonPoint.toCoordonnees().getLatitude());
        assertEquals(2, jsonPoint.toCoordonnees().getLongitude());
        assertEquals(3, jsonPoint.toCoordonnees().getAltitude());
    }

    @Test
    void getListPointInteret() {
        assertEquals(1, jsonFullPath.getListPointInteret().size());

        assertEquals(1, jsonFullPath.getListPointInteret().get(0).getCoordonnees().getLatitude());
        assertEquals(2, jsonFullPath.getListPointInteret().get(0).getCoordonnees().getLongitude());
        assertEquals(3, jsonFullPath.getListPointInteret().get(0).getCoordonnees().getAltitude());

        assertEquals("points d'interet", jsonFullPath.getListPointInteret().get(0).getNom());
        assertEquals("description du point d'interet", jsonFullPath.getListPointInteret().get(0).getDescription());
    }
}