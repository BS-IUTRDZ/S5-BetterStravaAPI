package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import iut.info3.betterstravaapi.path.InterestPoint;
import jakarta.validation.constraints.NotNull;

/**
 * Class representing a point of interest obtained by a request to the API.
 */
public class JsonPointInteret {

    /**
     * Coordinates of the point of interest.
     */
    @NotNull
    @JsonProperty("pos")
    private JsonPoint pos;

    /**
     * Name of the point of interest.
     */
    @NotNull
    @JsonProperty("nom")
    private String name;

    /**
     * Description of the point of interest.
     */
    @NotNull
    @JsonProperty("description")
    private String description;

    /**
     * Method toString to display the point of interest's information.
     * @return the point of interest as a string
     */
    @Override
    public String toString() {
        return "PointInteret{"
                + "pos=" + pos
                + ", nom=" + name
                + ", description=" + description
                + '}';
    }

    /**
     * Convert a json point of interest to a point of interest.
     * @return the point of interest
     */
    public InterestPoint toPointInterest() {
        return new InterestPoint(name, description, pos.toCoordinates());
    }
}
