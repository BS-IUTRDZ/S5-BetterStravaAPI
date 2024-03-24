package iut.info3.betterstravaapi.path;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.gson.Gson;
import iut.info3.betterstravaapi.path.jsonmodels.JsonFullPath;
import jakarta.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a path.
 * A path is characterized by:
 * <ul>
 *     <li>an id,</li>
 *     <li>the id of the user who created the path,</li>
 *     <li>an description</li>
 *     <li>a list of points making the path,</li>
 *     <li>une liste de point d'interet caracterisé par leur nom
 *     <li>a list of points of interest characterized by their name,
 *     their description and their coordinates,</li>
 *     <li>statistics on the path</li>
 * </ul>
 */

@Document(collection = "parcours")
public class PathEntity {

    /**
     * Id of the path.
     */
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    /**
     * Id of the user who created the path.
     */
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private int idUtilisateur;

    /**
     * Name of the path.
     */
    private String nom;

    /**
     * Description of the path.
     */
    private String description;

    /**
     * List of points making the path.
     */
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private List<Coordinates> points;

    /**
     * List of points of interest.
     */
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private List<InterestPoint> pointsInterets;

    /**
     * Statistics on the path.
     */
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Statistics statistiques;

    /**
     * Boolean to know if the path is archived.
     */
    private boolean archive;

    /**
     * Creation date of the path.
     */
    private long date;

    /**
     * Default constructor.
     */
    public PathEntity() { }

    /**
     * Constructor of the path.
     * @param idUser id of the user who created the path
     * @param name name of the path
     * @param desc description of the path
     * @param creationDate creation date of the path
     * @param point list of points making the path
     * @param stats statistics on the path
     */
    public PathEntity(final Integer idUser, final String name,
                      final String desc,
                      final Long creationDate,
                      final List<Coordinates> point,
                      final Statistics stats) {
        this.idUtilisateur = idUser;
        this.nom = name;
        this.description = desc;
        this.date = creationDate;
        this.points = new ArrayList<>(point);
        this.archive = false;
        this.statistiques = new Statistics(stats.getDuree(),
                stats.getDistance(), stats.getVitesseMoyenne(),
                stats.getDenivPos(), stats.getDenivNeg());
    }

    /**
     * Constructor of the path.
     * @param idUser id of the user who created the path
     * @param path path obtained when a user submits the creation of a path
     */
    public PathEntity(final int idUser, final JsonFullPath path) {
        this.idUtilisateur = idUser;
        this.nom = path.getName();
        this.description = path.getDescription();
        this.date = path.getDate();
        this.points = path.pointsToCoordinates();
        this.pointsInterets = path.getListPointInterest();
        this.archive = false;
        this.statistiques = new Statistics();

        this.statistiques.setDuree(path.getDuration());
    }

    /**
     * Getter of the path id.
     * @return the id of the path
     */
    public ObjectId getId() {
        return this.id == null ? null : new ObjectId(this.id.toByteArray());
    }

    /**
     * Setter of the path id.
     * @param pathId id of the path
     */
    public void setId(final ObjectId pathId) {
        this.id = new ObjectId(pathId.toByteArray());
    }

    /**
     * Getter of the name.
     * @return the name of the path
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter of the description of the path.
     * @return the description of the path
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of the description of the path.
     * @param desc description of the path
     */
    public void setDescription(final String desc) {
        this.description = desc;
    }

    /**
     * Setter of the list of interest points.
     * @param interestPoints the list of interest points
     */
    public void setPointsInterests(final List<InterestPoint> interestPoints) {
        this.pointsInterets = new ArrayList<>(interestPoints);
    }

    /**
     * Getter of the archived status.
     * @return true if the path is archived, false otherwise
     */
    public boolean isArchive() {
        return archive;
    }

    /**
     * Setter of the archived status.
     * @param newStatus true if the path is archived, false otherwise
     */
    public void setArchive(final boolean newStatus) {
        this.archive = newStatus;
    }

    /**
     * Getter of creation date.
     * @return the date in the UNIX format
     */
    public long getDate() {
        return date;
    }

    /**
     * Setter of the creation date.
     * @param newDate date in the UNIX format
     */
    public void setDate(final long newDate) {
        this.date = newDate;
    }

    /**
     * Getter of the statistics.
     * @return Statistics of the path
     */
    public Statistics getStatistiques() {
        // Deep copy to avoid modifications
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(statistiques),
                                Statistics.class);
    }

    /**
     * Setter of the statistics.
     * @param newStatistics new statistics of the path
     */
    public void setStatistiques(final Statistics newStatistics) {
        // Deep copy to avoid modifications
        Gson gson = new Gson();
        this.statistiques = gson.fromJson(gson.toJson(newStatistics),
                                            Statistics.class);
    }

    /**
     * toString method to display the entity.
     * @return string representing the entity
     */
    @Override
    public String toString() {
        return "PathEntity{"
                + "id=" + id
                + ", idUtilisateur=" + idUtilisateur
                + ", nom='" + nom + '\''
                + ", description='" + description + '\''
                + ", points=" + points
                + ", pointsInterets=" + pointsInterets
                + ", statistiques=" + statistiques
                + ", archive=" + archive
                + ", date=" + date
                + '}';
    }

    /**
     * Start the calculation of the statistics.
     */
    public void computeStatistics() {
        this.statistiques.computeStatistics(this.points);
    }
}
