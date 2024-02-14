package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Point {

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lon")
    private double lng;

    @JsonProperty("alt")
    private double alt;

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lon=" + lng +
                ", alt=" + alt +
                '}';
    }

}
