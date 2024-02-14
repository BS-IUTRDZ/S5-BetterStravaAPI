package iut.info3.betterstravaapi.path.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FullPath {
    private String nom;
    private String description;
    private long date;
    private long duree;
    private List<Point> points;

    @JsonProperty("points_interets")
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

    public List<PointInteret> getPointsInteret() {
        return pointsInterets;
    }
}
