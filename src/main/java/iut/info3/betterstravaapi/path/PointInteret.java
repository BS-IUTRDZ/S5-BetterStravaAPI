package iut.info3.betterstravaapi.path;

import java.util.List;

public class PointInteret {

    /**
     * nom du point d'interet
     */
    private String nom;

    /**
     * description du point d'interet
     */
    private String description;

    /**
     * coordonees du point d'interet
     */
    private Coordonnees coordonnees;

    /**
     * Constructeur par défaut pour permettre la compilation.
     */
    public PointInteret() { };

    /**
     * Création d'un point d'interet.
     * @param nom nom du point donné par l'utilisateur.
     * @param description description du point d'interet entree par l'utilisateur.
     * @param coordonnees coordonnees du point d'interet recuperer automatiquement.
     */
    public PointInteret(final String nom, final String description, final Coordonnees coordonnees){
        this.nom = nom;
        this.description = description;
        this.coordonnees = coordonnees;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

}
