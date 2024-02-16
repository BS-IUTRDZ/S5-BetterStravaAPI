package iut.info3.betterstravaapi.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesTest {

    private Statistiques stat;

    @BeforeEach
    void setUp() {
        stat = new Statistiques(
            1956,
            14.6,
            7.5,
            100,
            50
        );

    }

    @Test
    void getDuree() {
        assertEquals(1956, stat.getDuree());
    }

    @Test
    void setDuree() {
        stat.setDuree(2000);
        assertEquals(2000, stat.getDuree());
    }

    @Test
    void getDistance() {
        assertEquals(14.6, stat.getDistance());
    }

    @Test
    void setDistance() {
        stat.setDistance(15.0);
        assertEquals(15.0, stat.getDistance());
    }

    @Test
    void getVitesseMoyenne() {
        assertEquals(7.5, stat.getVitesseMoyenne());
    }

    @Test
    void setVitesseMoyenne() {
        stat.setVitesseMoyenne(8.0);
        assertEquals(8.0, stat.getVitesseMoyenne());
    }

    @Test
    void getDenivPos() {
        assertEquals(100, stat.getDenivPos());
    }

    @Test
    void setDenivPos() {
        stat.setDenivPos(200);
        assertEquals(200, stat.getDenivPos());
    }

    @Test
    void getDenivNeg() {
        assertEquals(50, stat.getDenivNeg());
    }

    @Test
    void setDenivNeg() {
        stat.setDenivNeg(60);
        assertEquals(60, stat.getDenivNeg());
    }
}