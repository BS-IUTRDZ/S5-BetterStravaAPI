package iut.info3.betterstravaapi.path;


import jakarta.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Entité représentant un parcour.
 * Un parcour est caractérisé par :
 * <ul>
 *     <li>un identifiant,</li>
 *     <li>l'identifiant de l'utilisateur ayant créer le parcour</li>
 *     <li>une description,</li>
 *     <li>une liste de liste de coordonees geographiques,</li>
 *     <li>une liste de point d'interet caracterisé par leur nom
 *     et leur description et leur coordonées</li>
 *     <li>l'id de l'utilisateur a qui appartiens le parcour</li>
 * </ul>
 */

@Document(collection = "parcours")
public class PathEntity {

    /**
     * Id du parcour.
     */
    @Id
    private ObjectId id;

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
     * liste des point composant le parcours.
     */
    private List<Coordonnees> points;

    /**
     * liste des points d'interets sur le parcours.
     */
    private List<PointInteret> pointsInterets;

    /**
     * bollean de parcours archivé ou non.
     */
    private boolean archive;

    /**
     * temps du parcours.
     */
    private long temps;

    /**
     * distance parcourus pendant le parcours.
     */
    private double distance;

    /**
     * vistesse moyenne de la course.
     */
    private double vitesse;

    /**
     * Date de creation du parcours.
     */
    private long date;

    /**
     * constructeur par default pour permettre la compilation.
     */
    public PathEntity() { }

    /**
     * Création d'un parcours.
     * @param idUser id de l'utilisateur a qui apartiens le parcours
     * @param name name du parcours choisit par l'utilisateur
     * @param descri descri du parcours donnee par l'utilisateur
     * @param point liste des point de coordonnees composant le parcour
     * @param interets listes des point d'interet du parcours
     */
    public PathEntity(final Integer idUser, final String name,
                      final String descri,
                      final List<Coordonnees> point,
                      final List<PointInteret> interets) {
        this.idUtilisateur = idUser;
        this.nom = name;
        this.description = descri;
        this.points = point;
        this.pointsInterets = interets;
        this.archive = false;
    }

    /**
     * getter de l'id du parcours.
     * @return l'id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * setter de l'id du parcours.
     * @param idParcour id du parcours
     */
    public void setId(final ObjectId idParcour) {
        this.id = idParcour;
    }

    /**
     * getter de l'id de l'utilisateur du Parcour.
     * @return l'id de l'utilisateur du parcours
     */
    public Integer getIdUserParcour() {
        return idUtilisateur;
    }

    /**
     * setter de l'id de l'utilisateur.
     * @param idUserParcour id de l'utilisateur
     */
    public void setIdUserParcour(final Integer idUserParcour) {
        this.idUtilisateur = idUserParcour;
    }

    /**
     * getter du nom.
     * @return le nom du parcour
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter du nom.
     * @param name nom du parcours
     */
    public void setNom(final String name) {
        this.nom = name;
    }

    /**
     * getter de la description.
     * @return la description du parcour
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter de la description.
     * @param descri descriptif parcours
     */
    public void setDescription(final String descri) {
        this.description = descri;
    }

    /**
     * getter de la listes des points composant le trajet.
     * @return la liste des point
     */
    public List<Coordonnees> getPoints() {
        return points;
    }

    /**
     * setter de la liste de points.
     * @param point point composant le parcours
     */
    public void setPoints(final List<Coordonnees> point) {
        this.points = point;
    }

    /**
     * getter de la liste des points d'interets.
     * @return la listes des points d'interets
     */
    public List<PointInteret> getPointsInterets() {
        return pointsInterets;
    }

    /**
     * setter de la dlistes des points d'interets.
     * @param interets liste des points d'interets
     */
    public void setPointsInterets(final List<PointInteret> interets) {
        this.pointsInterets = interets;
    }

    /**
     * getter du boolean archive.
     * @return true pour archive, flase sinon
     */
    public boolean isArchive() {
        return archive;
    }

    /**
     * setter de l'archive.
     * @param archi boolean pour parcours a archive ou non
     */
    public void setArchive(final boolean archi) {
        this.archive = archi;
    }

    /**
     * getter du temps.
     * @return le temps
     */
    public long getTemps() {
        return temps;
    }

    /**
     * setter du temps.
     * @param temp temps du parcours
     */
    public void setTemps(final long temp) {
        this.temps = temp;
    }

    /**
     * getter de la distance.
     * @return la distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * setter de la distance.
     * @param distances parcourus
     */
    public void setDistance(final double distances) {
        this.distance = distances;
    }

    /**
     * getter de la vitesse.
     * @return la vitesse
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * setter de la vitesse.
     * @param vit vitessedu parcours
     */
    public void setVitesse(final double vit) {
        this.vitesse = vit;
    }

    /**
     * getter de la date.
     * @return la date en long
     */
    public long getDate() {
        return date;
    }

    /**
     * setter de la date.
     * @param dateLong date en long
     */
    public void setDate(final long dateLong) {
        this.date = dateLong;
    }

    /**
     * toString de l'entity.
     * @return un string de l'entity
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
                + ", archive=" + archive
                + ", temps=" + temps
                + ", distance=" + distance
                + ", vitesse=" + vitesse
                + ", date=" + date
                + '}';
    }
}