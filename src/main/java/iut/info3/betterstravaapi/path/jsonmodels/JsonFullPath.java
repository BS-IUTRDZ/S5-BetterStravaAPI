package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import iut.info3.betterstravaapi.path.Coordonnees;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class JsonFullPath {

    @NotNull
    @JsonProperty("nom")
    private String nom;

    @NotNull
    @JsonProperty("description")
    private String description;

    @Positive
    @JsonProperty("date")
    private long date;

    @Positive
    @JsonProperty("duree")
    private long duree;

    @NotEmpty
    @JsonProperty("points")
    private List<JsonPoint> jsonPoints;

    @NotEmpty
    @JsonProperty("pointsInterets")
    private List<JsonPointInteret> pointsInterets;

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public long getDate() {
        return date;
    }

    public long getDuree() {
        return duree;
    }

    public List<JsonPoint> getPoints() {
        return jsonPoints;
    }

    public List<Coordonnees> pointsToCoordonnees() {
        List<Coordonnees> coordonnees = new ArrayList<>();
        for (JsonPoint jsonPoint : jsonPoints) {
            coordonnees.add(jsonPoint.toCoordonnees());
        }
        return coordonnees;
    }

    public List<JsonPointInteret> getPointsInteret() {
        return pointsInterets;
    }
}
