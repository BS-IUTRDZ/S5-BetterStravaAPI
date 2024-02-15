package iut.info3.betterstravaapi.path;

/**
 * Classe des points d'intérêts.
 */
public class PointInteret {

    /**
     * nom du point d'interet.
     */
    private String nom;

    /**
     * description du point d'interet.
     */
    private String description;

    /**
     * coordonees du point d'interet.
     */
    private Coordonnees coordonnees;

    /**
     * Constructeur par défaut pour permettre la compilation.
     */
    public PointInteret() { };

    /**
     * Création d'un point d'interet.
     * @param name nom du point donné par l'utilisateur.
     * @param descri description du point d'interet
     *                    entree par l'utilisateur.
     * @param coord coordonnees du point d'interet
     *                    recuperer automatiquement.
     */
    public PointInteret(final String name, final String descri,
                        final Coordonnees coord) {
        this.nom = name;
        this.description = descri;
        this.coordonnees = new Coordonnees(coord.getLatitude(),
                coord.getLongitude(), 0);
    }

    /**
     * getter du nom.
     * @return le nom du point d'interet
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter du nom.
     * @param name nom a set
     */
    public void setNom(final String name) {
        this.nom = name;
    }

    /**
     * getter de la description.
     * @return la description du point d'interet
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter de la description.
     * @param descri le string correspondant a la description
     */
    public void setDescription(final String descri) {
        this.description = descri;
    }

    /**
     * getter des coordonnees.
     * @return les coordonnees du point d'interet
     */
    public Coordonnees getCoordonnees() {
        return new Coordonnees(coordonnees.getLatitude(),
                coordonnees.getLongitude(), 0);
    }

    /**
     * setter des coordonnees.
     * @param coord coordonnees a set
     */
    public void setCoordonnees(final Coordonnees coord) {
        this.coordonnees = new Coordonnees(coord.getLatitude(),
                coord.getLongitude(), 0);
    }

}
