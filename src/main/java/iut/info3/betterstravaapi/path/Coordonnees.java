package iut.info3.betterstravaapi.path;

import java.util.ArrayList;
import java.util.List;

public class Coordonnees {

    /**
     * latitude du point referencer
     */
    private double latitude;

    /**
     * longitude du point referencer
     */
    private double longitude;


    /**
     * constructeur d'une coordonnees
     * @param latitude latitude des coordonees
     * @param longitude longitude des coordonees
     */
    public Coordonnees(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
