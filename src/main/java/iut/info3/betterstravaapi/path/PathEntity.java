package iut.info3.betterstravaapi.path;


import jakarta.persistence.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Entité représentant un parcour.
 * Un parcour est caractérisé par :
 * <ul>
 *     <li>un identifiant,</li>
 *     <li>l'identifiant de l'utilisateur ayant créer le parcour</li>
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
    private ObjectId _id;

    /**
     * Id de l'utilisateur.
     */
    private Integer idUtilisateur;

    /**
     * Nom du parcour.
     */
    private String nom;

    /**
     * Description du parcour.
     */
    private String description;

    /**
     * liste des point composant le parcours
     */
    private List<Coordonnees> points;

    /**
     * liste des points d'interets sur le parcours
     */
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
    public PathEntity(Integer idUtilisateur, String nom, String description, List<Coordonnees> points, List<PointInteret> pointsInterets) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.description = description;
        this.points = points;
        this.pointsInterets = pointsInterets;
    }

    public ObjectId getIdParcour() {
        return _id;
    }

    public void setIdParcour(ObjectId idParcour) {
        this._id = idParcour;
    }

    public Integer getIdUserParcour() {
        return idUtilisateur;
    }

    public void setIdUserParcour(Integer idParcour) {
        this.idUtilisateur = idParcour;
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