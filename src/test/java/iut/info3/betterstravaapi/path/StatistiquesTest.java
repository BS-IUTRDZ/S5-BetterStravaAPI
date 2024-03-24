package iut.info3.betterstravaapi.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesTest {

    private Statistics stat;

    @BeforeEach
    void setUp() {
        stat = new Statistics(
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

    @Test
    void calculStatistiques() {
        stat = new Statistics();
        stat.setDuree(120);
        List<Coordinates> coordonnees = new ArrayList<>();
        coordonnees.add(new Coordinates(44.36204454087336, 2.565200426862144,0));
        coordonnees.add(new Coordinates(44.36149999669016, 2.5698567263639505,10));
        coordonnees.add(new Coordinates(44.36204454087336, 2.565200426862144,0));


        stat.computeStatistics(coordonnees);
        assertEquals(0.75016, stat.getDistance(), 0.0005);
        assertEquals(120, stat.getDuree());
        assertEquals(22.5, stat.getVitesseMoyenne(), 0.05);
        assertEquals(10, stat.getDenivPos());
        assertEquals(10, stat.getDenivNeg());
    }
}