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
     * rayon de la terre en metre.
     */
    public static final double EARTH_RADIUS = 6371000.0;


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

    /**
     * Calcul de la distance entre deux coordonnees.
     * @param coordonnees coordonnees a comparer.
     * @return la distance entre les deux coordonnees en metre.
     */
    public double distanceTo(final Coordonnees coordonnees) {
        double dLat = Math.toRadians(coordonnees.getLatitude()
                                     - this.getLatitude());
        double dLng = Math.toRadians(coordonnees.getLongitude()
                                     - this.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                   + Math.cos(Math.toRadians(this.getLatitude()))
                   * Math.cos(Math.toRadians(coordonnees.getLatitude()))
                   * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
