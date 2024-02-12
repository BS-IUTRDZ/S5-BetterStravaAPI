package iut.info3.betterstravaapi.path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
    private final int nbHeureJournee = 24;

    /**
     * nombre de jours en arriere pour recuperer les parcours.
     */
    private final int nbJourMois = -30;

    /**
     * recuperation des parcours de l'utilisateur.
     * des 30 derniers jours.
     * @param idUser id de l'utilisateur
     * @return la liste des parcours des 30 derniers jours.
     */
    public List<PathEntity> recupPerformances30Jours(final int idUser) {
        Calendar calendrier = Calendar.getInstance();
        calendrier.add(Calendar.HOUR, nbJourMois * nbHeureJournee);
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
                                                     final int id) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        long dateMin = sdf.parse(dateInf).getTime();
        long dateMax = sdf.parse(dateSup).getTime();
        return pathRepository
                .findEntitiesByDateAndName(
                        dateMin, dateMax, nom, id,false);
    }



}
