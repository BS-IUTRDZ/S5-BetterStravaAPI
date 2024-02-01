package iut.info3.betterstravaapi.path;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CoordonneesTest {

    @Test
    public void testGetLat() {
        Coordonnees coord = new Coordonnees(15.5,15.2);
        assertEquals("",15.5,coord.getLatitude());
    }

    @Test
    public void testGetLong() {
        Coordonnees coord = new Coordonnees(15.5,15.2);
        assertEquals("",15.2,coord.getLongitude());
    }

    @Test
    public void testSetLat() {
        Coordonnees coord = new Coordonnees(15.5,15.2);
        coord.setLatitude(10.2);
        assertEquals("",10.2,coord.getLatitude());
    }

    @Test
    public void testSetLong() {
        Coordonnees coord = new Coordonnees(15.5,15.2);
        coord.setLongitude(48.5);
        assertEquals("",48.5,coord.getLongitude());
    }

}
