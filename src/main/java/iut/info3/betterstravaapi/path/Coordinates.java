package iut.info3.betterstravaapi.path;

/**
 * Class of the coordinates of the points.
 */
public class Coordinates {

    /**
     * Latitude of the point.
     */
    private double latitude;

    /**
     * Longitude of the point.
     */
    private double longitude;

    /**
     * Altitude of the point.
     */
    private double altitude;

    /**
     * Earth radius in meters.
     */
    public static final double EARTH_RADIUS = 6371000.0;


    /**
     * Default constructor.
     */
    public Coordinates() { }

    /**
     * Constructor of the coordinates.
     * @param lati latitude of the coordinates.
     * @param longi longitude of the coordinates.
     * @param alti altitude of the coordinates.
     */
    public Coordinates(final double lati,
                       final double longi,
                       final double alti) {
        this.latitude = lati;
        this.longitude = longi;
        this.altitude = alti;
    }

    /**
     * Getter of the latitude.
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter of the longitude.
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Getter of the altitude.
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Compute the distance between two coordinates.
     * @param coordinates coordinates to compute the distance with.
     * @return the distance between the two coordinates in meters.
     */
    public double distanceTo(final Coordinates coordinates) {
        double dLat = Math.toRadians(coordinates.getLatitude()
                                     - this.getLatitude());
        double dLng = Math.toRadians(coordinates.getLongitude()
                                     - this.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                   + Math.cos(Math.toRadians(this.getLatitude()))
                   * Math.cos(Math.toRadians(coordinates.getLatitude()))
                   * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
