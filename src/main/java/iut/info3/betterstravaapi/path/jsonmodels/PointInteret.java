package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class PointInteret {

    @NotNull
    @JsonProperty("pos")
    private Point pos;

    @NotNull
    @JsonProperty("nom")
    private String nom;

    @NotNull
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

    public Point getPos() {
        return pos;
    }

}
