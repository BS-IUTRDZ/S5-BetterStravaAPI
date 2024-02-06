package iut.info3.betterstravaapi.path;

import org.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * Service des parcours.
 */
@Service
public class PathService {

    /**
     * repository connecter a la base nosql.
     */
    @Autowired
    private PathRepository pathRepository;

    /**
     * nombre d'heure en une journée.
     */
    private static final int NB_HEURE_JOURNEE = 24;

    /**
     * nombre de jours en arriere pour recuperer les parcours.
     */
    private static final int NB_JOURS_MOIS = -30;

    /**
     * recuperation des parcours de l'utilisateur.
     * des 30 derniers jours.
     * @param idUser id de l'utilisateur
     * @return la liste des parcours des 30 derniers jours.
     */
    public List<PathEntity> recupParcours30Jours(final int idUser) {
        Calendar calendrier = Calendar.getInstance();
        calendrier.add(Calendar.HOUR, NB_JOURS_MOIS * NB_HEURE_JOURNEE);
        return  pathRepository.findPathByIdUtilisateurAndArchiveAndDateAfter(
                idUser, false, calendrier.getTime().getTime());
    }

    /**
     *recuperation des parcours d'un utilisateur.
     * @param idUser id de l'utilisateur
     * @return la liste des parcours de l'utilisateur
     */
    public List<PathEntity> recupParcoursAll(final int idUser) {
        return  pathRepository.findPathByIdUtilisateurAndArchive(idUser, false);
    }

    /**
     * recuperation du dernier parcours de l'utilisateur.
     * @param idUser id de l'utilisateur
     * @return le PathEntity du parcours du dernier utilisateurs.
     */
    public PathEntity recupDernierParcour(final int idUser) {
        return pathRepository.findTopByIdUtilisateurAndArchiveOrderByDateAsc(
                idUser, false);
    }

    /**
     * @param nom chaine permettant de faire le filtre sur le champ nom
     * @param dateInf date permettant de ne récupérer que les
     *                parcours avec une date inférieure
     * @param dateSup date permettant de ne récupérer que les
     *                parcours avec une date supérieure
     * @param id id unique de l'utilisateur en base de données
     * @return la liste des parcours de l'utilisateur avec l'id 'id'
     *         respectant tout les filtres et n'étant pas archiver
     */
    public List<PathEntity> findParcourByDateAndName(final String nom,
                                                     final String dateInf,
                                                     final String dateSup,
                                                     final int id) {
        long dateMin = Long.parseLong(dateInf);
        long dateMax = Long.parseLong(dateSup);
        return pathRepository
                .findEntitiesByDateAndName(
                        dateMin, dateMax, nom, id, false);
    }

    /**
     * Ecrit au format Json les informations d'un parcours.
     * @param path parcours à analyser
     * @return les informations du parcours au format Json
     */
    public JSONObject getPathInfos(final PathEntity path) {
        JSONObject pathJson = new JSONObject();

        // Récupération des points
        JSONObject points = new JSONObject();
        for (int i = 0; i < path.getPoints().size(); i++) {
            Coordonnees c = path.getPoints().get(i);
            JSONObject coords = new JSONObject();
            coords.put("latitude", c.getLatitude());
            coords.put("longitude", c.getLongitude());

            points.put("point" + i, coords);
        }

        pathJson.put("nom", path.getNom());
        pathJson.put("description", path.getDescription());
        pathJson.put("points", points);

        //TODO points intérets
        return pathJson;
    }

    public PathEntity recupParcoursParId(final ObjectId id) {
        return pathRepository.findByIdAndArchiveFalse(id);
    }

}
