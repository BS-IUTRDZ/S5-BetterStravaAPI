package iut.info3.betterstravaapi.path;

/**
 * Entity representing a point of interest.
 */
public class InterestPoint {

    /**
     * Name of the interest point.
     */
    private String nom;

    /**
     * Description of the interest point.
     */
    private String description;

    /**
     * Coordinates of the interest point.
     */
    private Coordinates coordonnees;

    /**
     * Default constructor.
     */
    public InterestPoint() { };

    /**
     * Constructor of the interest point.
     * @param pointName name of the interest point
     * @param desc description of the interest point
     * @param coords coordinates of the interest point
     */
    public InterestPoint(final String pointName, final String desc,
                         final Coordinates coords) {
        this.nom = pointName;
        this.description = desc;
        this.coordonnees = new Coordinates(
                coords.getLatitude(),
                coords.getLongitude(),
                coords.getAltitude());
    }

    /**
     * Getter of the name.
     * @return the name of the interest point
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter of the name.
     * @param name the name of the interest point
     */
    public void setName(final String name) {
        this.nom = name;
    }

    /**
     * Getter of the description.
     * @return the description of the interest point
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of the description.
     * @param desc the description of the interest point
     */
    public void setDescription(final String desc) {
        this.description = desc;
    }

    /**
     * Getter of the coordinates.
     * @return the coordinates of the interest point
     */
    public Coordinates getCoordonnees() {
        return new Coordinates(
                coordonnees.getLatitude(),
                coordonnees.getLongitude(),
                coordonnees.getAltitude());
    }

    /**
     * Setter of the coordinates.
     * @param coords the coordinates of the interest point
     */
    public void setCoordonnees(final Coordinates coords) {
        this.coordonnees = new Coordinates(
                coords.getLatitude(),
                coords.getLongitude(),
                coords.getAltitude());
    }

}
