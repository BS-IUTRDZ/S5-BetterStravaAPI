package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class JsonPointInteretTest {

    private JsonPointInteret jsonPointInteret;
    Field privatePointInteretField;

    @BeforeEach
    void setUp() {

        String pointInteretJson = "{\"pos\":{\"lat\":1,\"lon\":2,\"alt\":3},\"nom\":\"points d'interet\",\"description\":\"description du point d'interet\"}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonPointInteret = mapper.readValue(pointInteretJson, JsonPointInteret.class);
        } catch (Exception e) {
            fail("Impossible de creer le point d'intéret de test");
        }

        try {
            // Accès au champ privé
            privatePointInteretField = JsonPointInteret.class.getDeclaredField("pos");
            privatePointInteretField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            fail("Le champ pos n'existe pas");
        }

    }

    @Test
    void testToString() {
        assertEquals("PointInteret{pos=Point{lat=1.0, lon=2.0, alt=3.0}, nom=points d'interet, description=description du point d'interet}", jsonPointInteret.toString());
    }

    @Test
    void getPos() throws IllegalAccessException {
        assertEquals(1, ((JsonPoint) privatePointInteretField.get(jsonPointInteret)).toCoordonnees().getLatitude());
        assertEquals(2, ((JsonPoint) privatePointInteretField.get(jsonPointInteret)).toCoordonnees().getLongitude());
        assertEquals(3, ((JsonPoint) privatePointInteretField.get(jsonPointInteret)).toCoordonnees().getAltitude());
    }
}