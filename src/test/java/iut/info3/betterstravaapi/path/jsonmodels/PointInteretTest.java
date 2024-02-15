package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointInteretTest {

    private PointInteret pointInteret;
    @BeforeEach
    void setUp() {

        String pointInteretJson = "{\"pos\":{\"lat\":1,\"lon\":2,\"alt\":3},\"nom\":\"points d'interet\",\"description\":\"description du point d'interet\"}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            pointInteret = mapper.readValue(pointInteretJson, PointInteret.class);
        } catch (Exception e) {
            fail("Impossible de creer le point d'intéret de test");
        }

    }

    @Test
    void testToString() {
        assertEquals("PointInteret{pos=Point{lat=1.0, lon=2.0, alt=3.0}, nom=points d'interet, description=description du point d'interet}", pointInteret.toString());
    }

    @Test
    void getPos() {
        assertEquals(1, pointInteret.getPos().toCoordonnees().getLatitude());
        assertEquals(2, pointInteret.getPos().toCoordonnees().getLongitude());
        assertEquals(3, pointInteret.getPos().toCoordonnees().getAltitude());
    }
}