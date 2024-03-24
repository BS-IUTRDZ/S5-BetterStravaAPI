package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonPointTest {

    private JsonPoint jsonPoint;
    @BeforeEach
    void setUp() {

        String pointJson = "{\"lat\":24.7162,\"lon\":-12.7261,\"alt\":1330.62}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonPoint = mapper.readValue(pointJson, JsonPoint.class);
        } catch (Exception e) {
            fail("Impossible de creer le point de test");
        }

    }

    @Test
    void testToString() {
        assertEquals("Point{lat=24.7162, lon=-12.7261, alt=1330.62}", jsonPoint.toString());
    }

    @Test
    void toCoordonnees() {
        assertEquals(24.7162, jsonPoint.toCoordinates().getLatitude());
        assertEquals(-12.7261, jsonPoint.toCoordinates().getLongitude());
        assertEquals(1330.62, jsonPoint.toCoordinates().getAltitude());
    }
}