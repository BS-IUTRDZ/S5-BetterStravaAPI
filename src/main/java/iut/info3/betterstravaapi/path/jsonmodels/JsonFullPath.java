package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import iut.info3.betterstravaapi.path.Coordonnees;
import iut.info3.betterstravaapi.path.PointInteret;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un parcours obtenu par une requête à l'API.
 */
public class JsonFullPath {

    /**
     * Nom du parcours.
     */
    @NotNull
    @JsonProperty("nom")
    private String nom;

    /**
     * Description du parcours.
     */
    @NotNull
    @JsonProperty("description")
    private String description;

    /**
     * Date du parcours.
     */
    @Positive
    @JsonProperty("date")
    private long date;

    /**
     * Durée du parcours.
     */
    @Positive
    @JsonProperty("duree")
    private long duree;

    /**
     * Liste des points composant le parcours.
     */
    @NotEmpty
    @JsonProperty("points")
    private List<JsonPoint> jsonPoints;

    /**
     * Liste des points d'interets sur le parcours.
     */
    @NotNull
    @JsonProperty("pointsInterets")
    private List<JsonPointInteret> pointsInterets;

    /**
     * Getter du nom du parcours.
     * @return le nom du parcours
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de la description du parcours.
     * @return la description du parcours
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter de la date du parcours.
     * @return la date du parcours sous le format UNIX
     */
    public long getDate() {
        return date;
    }

    /**
     * Getter de la durée du parcours.
     * @return la durée du parcours en secondes
     */
    public long getDuree() {
        return duree;
    }

    /**
     * Getter de la liste des points composant le parcours.
     * @return la liste des points
     */
    public List<JsonPoint> getPoints() {
        // Copie profonde pour éviter les modifications
        Type type = new TypeToken<List<JsonPoint>>() { }.getType();
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(jsonPoints), type);
    }

    /**
     * Convertit les points en coordonnées.
     * @return la liste des coordonnées
     */
    public List<Coordonnees> pointsToCoordonnees() {
        List<Coordonnees> coordonnees = new ArrayList<>();
        for (JsonPoint jsonPoint : jsonPoints) {
            coordonnees.add(jsonPoint.toCoordonnees());
        }
        return coordonnees;
    }

    /**
     * Getter de la liste des points d'interets.
     * @return la liste des points d'interets
     */
    public List<JsonPointInteret> getJsonPointsInteret() {
        Type type = new TypeToken<List<JsonPointInteret>>() { }.getType();
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(pointsInterets), type);
    }

    /**
     * Convertit les points d'interets json en liste de points d'interets.
     * @return la liste des points d'interets
     */
    public List<PointInteret> getListPointInteret() {
        List<PointInteret> pointInterets = new ArrayList<>();
        for (JsonPointInteret jsonPointInteret : pointsInterets) {
            pointInterets.add(jsonPointInteret.toPointInteret());
        }
        return pointInterets;
    }
}
