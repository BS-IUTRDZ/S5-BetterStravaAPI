package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import iut.info3.betterstravaapi.path.Coordinates;

/**
 * Class representing a point obtained by a request to the API.
 */
public class JsonPoint {

    /**
     * Latitude of the point.
     */
    @JsonProperty("lat")
    private double lat;

    /**
     * Longitude of the point.
     */
    @JsonProperty("lon")
    private double lon;

    /**
     * Altitude of the point.
     */
    @JsonProperty("alt")
    private double alt;

    /**
     * Method toString to display the point's information.
     * @return the point as a string
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
     * Convert a point to coordinates.
     * @return the coordinates of the point
     */
    public Coordinates toCoordinates() {
        return new Coordinates(lat, lon, alt);
    }

}
