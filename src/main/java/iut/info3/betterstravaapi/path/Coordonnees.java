package iut.info3.betterstravaapi.path;

import java.util.ArrayList;
import java.util.List;

public class Coordonnees {

    /**
     * latitude du point referencer
     */
    private Double latitude;

    /**
     * longitude du point referencer
     */
    private Double longitude;


    /**
     * constructeur d'une coordonnees
     * @param latitude latitude des coordonees
     * @param longitude longitude des coordonees
     */
    public Coordonnees(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
