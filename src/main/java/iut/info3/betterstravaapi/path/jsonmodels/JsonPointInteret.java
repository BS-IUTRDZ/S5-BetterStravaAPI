package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class JsonPointInteret {

    @NotNull
    @JsonProperty("pos")
    private JsonPoint pos;

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

    public JsonPoint getPos() {
        return pos;
    }

}
