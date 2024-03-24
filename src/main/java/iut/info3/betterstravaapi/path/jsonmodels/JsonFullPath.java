package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import iut.info3.betterstravaapi.path.Coordinates;
import iut.info3.betterstravaapi.path.InterestPoint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a path obtained by a request to the API.
 */
public class JsonFullPath {

    /**
     * Name of the path.
     */
    @NotNull
    @JsonProperty("nom")
    private String name;

    /**
     * Description of the path.
     */
    @NotNull
    @JsonProperty("description")
    private String description;

    /**
     * Date of the path.
     */
    @Positive
    @JsonProperty("date")
    private long date;

    /**
     * Duration of the path in seconds.
     */
    @Positive
    @JsonProperty("duree")
    private long duration;

    /**
     * List of points composing the path.
     */
    @NotEmpty
    @JsonProperty("points")
    private List<JsonPoint> jsonPoints;

    /**
     * List of points of interest.
     */
    @NotNull
    @JsonProperty("pointsInterets")
    private List<JsonPointInteret> pointsInterests;

    /**
     * Getter of the name of the path.
     * @return the name of the path
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the description of the path.
     * @return the description of the path
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter of the date of the path.
     * @return the date of the path in the UNIX format
     */
    public long getDate() {
        return date;
    }

    /**
     * Getter of the duration of the path.
     * @return the duration of the path in seconds
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Getter of the list of points.
     * @return the list of points
     */
    public List<JsonPoint> getPoints() {
        // Deep copy to avoid modifications
        Type type = new TypeToken<List<JsonPoint>>() { }.getType();
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(jsonPoints), type);
    }

    /**
     * Convert the points to coordinates.
     * @return the list of coordinates
     */
    public List<Coordinates> pointsToCoordinates() {
        List<Coordinates> coordinates = new ArrayList<>();
        for (JsonPoint jsonPoint : jsonPoints) {
            coordinates.add(jsonPoint.toCoordinates());
        }
        return coordinates;
    }

    /**
     * Getter of the list of points of interest.
     * @return the list of points of interest
     */
    public List<JsonPointInteret> getJsonPointsInterests() {
        Type type = new TypeToken<List<JsonPointInteret>>() { }.getType();
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(pointsInterests), type);
    }

    /**
     * Convert the json points of interest to a list of points of interest.
     * @return the list of points of interest
     */
    public List<InterestPoint> getListPointInterest() {
        List<InterestPoint> pointInterests = new ArrayList<>();
        for (JsonPointInteret jsonPointInteret : pointsInterests) {
            pointInterests.add(jsonPointInteret.toPointInterest());
        }
        return pointInterests;
    }
}
