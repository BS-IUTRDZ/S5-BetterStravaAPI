package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    private Point point;
    @BeforeEach
    void setUp() {

        String pointJson = "{\"lat\":24.7162,\"lon\":-12.7261,\"alt\":1330.62}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            point = mapper.readValue(pointJson, Point.class);
        } catch (Exception e) {
            fail("Impossible de creer le point de test");
        }

    }

    @Test
    void testToString() {
        assertEquals("Point{lat=24.7162, lon=-12.7261, alt=1330.62}", point.toString());
    }

    @Test
    void toCoordonnees() {
        assertEquals(24.7162, point.toCoordonnees().getLatitude());
        assertEquals(-12.7261, point.toCoordonnees().getLongitude());
        assertEquals(1330.62, point.toCoordonnees().getAltitude());
    }
}