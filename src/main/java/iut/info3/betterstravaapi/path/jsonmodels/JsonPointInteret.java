package iut.info3.betterstravaapi.path.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * Classe représentant un point d'interet obtenus par une requête à l'API.
 */
public class JsonPointInteret {

    /**
     * Coordonnées du point d'interet.
     */
    @NotNull
    @JsonProperty("pos")
    private JsonPoint pos;

    /**
     * Nom du point d'interet.
     */
    @NotNull
    @JsonProperty("nom")
    private String nom;

    /**
     * Description du point d'interet.
     */
    @NotNull
    @JsonProperty("description")
    private String description;

    /**
     * Méthode toString pour afficher les informations du point d'intéret.
     * @return le point d'intéret sous le format d'une chaine de caractères
     */
    @Override
    public String toString() {
        return "PointInteret{"
                + "pos=" + pos
                + ", nom=" + nom
                + ", description=" + description
                + '}';
    }
}
