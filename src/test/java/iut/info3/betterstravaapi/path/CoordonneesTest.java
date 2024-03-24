package iut.info3.betterstravaapi.path;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CoordonneesTest {

    @Test
    public void testGetLat() {
        Coordinates coord = new Coordinates(15.5,15.2,0);
        assertEquals("",15.5,coord.getLatitude());
    }

    @Test
    public void testGetLong() {
        Coordinates coord = new Coordinates(15.5,15.2,0);
        assertEquals("",15.2,coord.getLongitude());
    }

    @Test
    public void testGetAlt() {
        Coordinates coord = new Coordinates(15.5,15.2,0);
        assertEquals("",0.0,coord.getAltitude());
    }

    @Test
    public void testCalculDistance() {
        Coordinates coord1 = new Coordinates(15.5,15.2,0);
        Coordinates coord2 = new Coordinates(15.5,15.2,0);
        assertEquals("",0.0,coord1.distanceTo(coord2));

        coord1 = new Coordinates(44.36204454087336, 2.565200426862144,0);
        coord2 = new Coordinates(44.36149999669016, 2.5698567263639505,0);
        org.junit.jupiter.api.Assertions.assertEquals(375.39, coord1.distanceTo(coord2), 0.5);
    }

}
