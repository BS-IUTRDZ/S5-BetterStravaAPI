package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Service des parcours.
 */
@Service
public class PathService {

    public static final int DEFAULT_PAGE_SIZE = 10;

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
        return  pathRepository.findPathByIdUtilisateurAndArchive(
                idUser, false);
    }

    /**
     * recuperation du dernier parcours de l'utilisateur.
     * @param idUser id de l'utilisateur
     * @return le PathEntity du parcours du dernier utilisateurs.
     */
    public PathEntity recupDernierParcour(final int idUser) {
        return pathRepository.findTopByIdUtilisateurAndArchiveOrderByDateDesc(
                idUser, false);
    }

    /**
     * @param nom chaine permettant de faire le filtre sur le champ nom
     * @param dateInf date permettant de ne récupérer que les
     *                parcours avec une date inférieure
     * @param dateSup date permettant de ne récupérer que les
     *                parcours avec une date supérieure
     * @param id id unique de l'utilisateur en base de données
     * @throws ParseException erreur de parsing de date
     * @return la liste des parcours de l'utilisateur avec l'id 'id'
     *         respectant tout les filtres et n'étant pas archiver
     */
    public List<PathEntity> findParcourByDateAndName(
            final String nom,
            final String dateInf,
            final String dateSup,
            final int id,
            final int nbPathAlreadyLoaded) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
                Locale.FRANCE);

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        long dateMin = sdf.parse(dateInf).getTime();
        long dateMax = sdf.parse(dateSup).getTime();
        return pathRepository
                .findEntitiesByDateAndName(
                        dateMin, dateMax, nom, id, false,
                        getNextPage(nbPathAlreadyLoaded));
    }

    /**
     * @param nom chaine permettant de faire le filtre sur le champ nom
     * @param dateInf date permettant de ne récupérer que les
     *                parcours avec une date inférieure
     * @param dateSup date permettant de ne récupérer que les
     *                parcours avec une date supérieure
     * @param id id unique de l'utilisateur en base de données
     * @param distanceMin distance minimale du parcours recherché
     * @param distanceMax distance maximale du parcours recherché
     * @throws ParseException erreur de parsing de date
     * @return la liste des parcours de l'utilisateur avec l'id 'id'
     *         respectant tout les filtres et n'étant pas archiver
     */
    public List<PathEntity> findParcourByDateAndNameAndDistance(
            final String nom,
            final String dateInf,
            final String dateSup,
            final int distanceMin,
            final int distanceMax,
            final int id,
            final int nbPathAlreadyLoaded) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
                Locale.FRANCE);

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        long dateMin = sdf.parse(dateInf).toInstant().toEpochMilli();
        long dateMax = sdf.parse(dateSup).toInstant().toEpochMilli();
        return pathRepository
                .findEntitiesByDateAndNameAndDistance(dateMin, dateMax, nom,
                        distanceMin, distanceMax, id, false,
                        getNextPage(nbPathAlreadyLoaded));
    }

    /**
     * Recupère un parcours par son id.
     * @param idUtilisateur id unique de l'utilisateur en base de données
     * @param id id d'un parcours
     * @return le parcours correspondant à l'id
     */
    public PathEntity recupParcoursParId(final ObjectId id,
                                         final int idUtilisateur) {
        return pathRepository.findByIdAndArchiveFalseAndAndIdUtilisateur(id,
                idUtilisateur);
    }

    public Pageable getNextPage(int pageIndexStart) {
        return PageRequest.of(0,
                pageIndexStart + DEFAULT_PAGE_SIZE);
    }

}
