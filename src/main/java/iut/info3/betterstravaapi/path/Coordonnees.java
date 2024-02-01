package iut.info3.betterstravaapi.path;

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
     * constructeur par default pour la compilation.
     */
    public Coordonnees() { }

    /**
     * constructeur d'une coordonnees.
     * @param lati latitude des coordonees.
     * @param longi longitude des coordonees.
     */
    public Coordonnees(final double lati, final double longi) {
        this.latitude = lati;
        this.longitude = longi;
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
     * setter de la latitude.
     * @param lati la latitude a modifier
     */
    public void setLatitude(final double lati) {
        this.latitude = lati;
    }

    /**
     * setter de la longitude.
     * @param longi la longitude a modifier
     */
    public void setLongitude(final double longi) {
        this.longitude = longi;
    }
}
