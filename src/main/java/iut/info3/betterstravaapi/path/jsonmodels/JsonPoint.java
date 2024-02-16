package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import iut.info3.betterstravaapi.path.Coordonnees;

public class JsonPoint {

    /**
     * Latitude du point.
     */
    @JsonProperty("lat")
    private double lat;

    /**
     * Longitude du point.
     */
    @JsonProperty("lon")
    private double lon;

    /**
     * Altitude du point.
     */
    @JsonProperty("alt")
    private double alt;

    /**
     * Méthode toString pour afficher les informations du point.
     * @return le point sous le format d'une chaine de caractères
     */
    @Override
    public String toString() {
        return "Point{"
                + "lat=" + lat
                + ", lon=" + lon
                + ", alt=" + alt
                + '}';
    }

    /**
     * Convertit un point en coordonnées.
     * @return les coordonnées du point
     */
    public Coordonnees toCoordonnees() {
        return new Coordonnees(lat, lon, alt);
    }

}
