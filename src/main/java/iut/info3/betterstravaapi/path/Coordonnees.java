package iut.info3.betterstravaapi.path;

/**
 * Class des coordonnées des points.
 */
public class Coordonnees {

    /**
     * latitude du point referencer.
     */
    private double latitude;

    /**
     * longitude du point referencer.
     */
    private double longitude;

    /**
     * altitude du point referencer.
     */
    private double altitude;

    /**
     * constructeur par default pour la compilation.
     */
    public Coordonnees() { }

    /**
     * constructeur d'une coordonnees.
     * @param lati latitude des coordonees.
     * @param longi longitude des coordonees.
     * @param alti altitude des coordonees.
     */
    public Coordonnees(final double lati,
                       final double longi,
                       final double alti) {
        this.latitude = lati;
        this.longitude = longi;
        this.altitude = alti;
    }

    /**
     * getter de la lattitude.
     * @return la lattitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * getter de la longitude.
     * @return la longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * getter de l'altitude.
     * @return l'altitude
     */
    public double getAltitude() {
        return altitude;
    }

}
