package iut.info3.betterstravaapi.path;


import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Entité représentant un parcour.
 * Un parcour est caractérisé par :
 * <ul>
 *     <li>un identifiant,</li>
 *     <li>une description,</li>
 *     <li>une liste de liste de coordonees geographiques,</li>
 *     <li>une liste de point d'interet caracterisé par leur nom et leur description et leur coordonées</li>
 *     <li>l'id de l'utilisateur a qui appartiens le parcour</li>
 * </ul>
 */

@Document(collection = "parcours")
public class PathEntity {

    /**
     * Id du parcour.
     */
    @Id
    private String _id;

    /**
     * Nom du parcour.
     */
    @Field("nom")
    private String nom;

    /**
     * Description du parcour.
     */
    @Field("description")
    private String description;

    /**
     * liste des point composant le parcours
     */
    @ElementCollection
    private List<Coordonnees> points;

    /**
     * liste des points d'interets sur le parcours
     */
    @DBRef
    private List<PointInteret> pointsInterets;


    /**
     * constructeur par default pour permettre la compilation
     */
    public PathEntity() { }


    /**
     * Création d'un parcours
     * @param nom nom du parcours choisit par l'utilisateur
     * @param description description du parcours donnee par l'utilisateur
     * @param points liste des points de coordonnees composant le parcour
     */
    public PathEntity(String nom, String description, List<Coordonnees> points, List<PointInteret> pointsInterets) {
        this.nom = nom;
        this.description = description;
        this.points = points;
        this.pointsInterets = pointsInterets;
    }

    public String getIdParcour() {
        return _id;
    }

    public void setIdParcour(String idParcour) {
        this._id = idParcour;
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

    public List<Coordonnees> getPoints() {
        return points;
    }

    public void setPoints(List<Coordonnees> points) {
        this.points = points;
    }

    public List<PointInteret> getPointsInterets() {
        return pointsInterets;
    }

    public void setPointsInterets(List<PointInteret> pointsInterets) {
        this.pointsInterets = pointsInterets;
    }

    @Override
    public String toString() {
        return "PathEntity{" +
                "_id='" + _id + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", points=" + points +
                ", pointsInterets=" + pointsInterets +
                '}';
    }
}
