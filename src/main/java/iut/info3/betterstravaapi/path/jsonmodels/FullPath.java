package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import iut.info3.betterstravaapi.path.Coordonnees;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class FullPath {

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
    private List<Point> points;

    @NotEmpty
    @JsonProperty("pointsInterets")
    private List<PointInteret> pointsInterets;

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

    public List<Point> getPoints() {
        return points;
    }

    public List<Coordonnees> pointsToCoordonnees() {
        List<Coordonnees> coordonnees = new ArrayList<>();
        for (Point point : points) {
            coordonnees.add(point.toCoordonnees());
        }
        return coordonnees;
    }

    public List<PointInteret> getPointsInteret() {
        return pointsInterets;
    }
}
