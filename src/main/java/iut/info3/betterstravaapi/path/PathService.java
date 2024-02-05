package iut.info3.betterstravaapi.path;

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
    public List<PathEntity> recupPerformances30Jours(final int idUser) {
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
    public List<PathEntity> recupPerformancesGlobal(final int idUser) {
        return  pathRepository.findPathByIdUtilisateurAndArchive(idUser, false);
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

}
