package iut.info3.betterstravaapi.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointInteretTest {

    private InterestPoint pointInteret;

    @BeforeEach
    void setUp() {
        pointInteret = new InterestPoint(
            "Nom du point",
            "Description du point",
            new Coordinates(1, 2, 3)
        );
    }

    @Test
    void getNom() {
        assertEquals("Nom du point", pointInteret.getNom());
    }

    @Test
    void setNom() {
        pointInteret.setName("Nouveau nom");
        assertEquals("Nouveau nom", pointInteret.getNom());
    }

    @Test
    void getDescription() {
        assertEquals("Description du point", pointInteret.getDescription());
    }

    @Test
    void setDescription() {
        pointInteret.setDescription("Nouvelle description");
        assertEquals("Nouvelle description", pointInteret.getDescription());
    }

    @Test
    void getCoordonnees() {
        assertEquals(1, pointInteret.getCoordonnees().getLatitude());
        assertEquals(2, pointInteret.getCoordonnees().getLongitude());
        assertEquals(3, pointInteret.getCoordonnees().getAltitude());
    }

    @Test
    void setCoordonnees() {
        pointInteret.setCoordonnees(new Coordinates(4, 5, 6));
        assertEquals(4, pointInteret.getCoordonnees().getLatitude());
        assertEquals(5, pointInteret.getCoordonnees().getLongitude());
        assertEquals(6, pointInteret.getCoordonnees().getAltitude());
    }
}