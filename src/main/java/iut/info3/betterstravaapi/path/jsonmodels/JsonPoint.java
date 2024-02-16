package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import iut.info3.betterstravaapi.path.Coordonnees;

public class JsonPoint {

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lon")
    private double lon;

    @JsonProperty("alt")
    private double alt;

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", alt=" + alt +
                '}';
    }

    public Coordonnees toCoordonnees() {
        return new Coordonnees(lat, lon, alt);
    }

}
