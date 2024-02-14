package iut.info3.betterstravaapi.path.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PointInteret {

    @JsonProperty("pos")
    private Point pos;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("description")
    private String description;

    @Override
    public String toString() {
        return "PointInteret{" +
                "pos=" + pos +
                ", nom=" + nom +
                ", description=" + description +
                '}';
    }

}
