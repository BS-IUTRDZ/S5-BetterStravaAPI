package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Service related to Path functionality.
 */
@Service
public class PathService {
    /**
     * Number of elements to display per page.
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Repository associated with the noSQL database.
     */
    @Autowired
    private PathRepository pathRepository;

    /**
     * Number of hours in a day.
     */
    private static final int HOURS_IN_DAY = 24;

    /**
     * Number of day to get paths from the last month.
     */
    private static final int DAY_IN_MONTH = -30;

    /**
     * Getting all paths from a user since the last 30 days.
     * @param userId id of the user
     * @return list of paths from the last 30 days
     */
    public List<PathEntity> getPathsLastMonth(final int userId) {
        Calendar calendrier = Calendar.getInstance();
        calendrier.add(Calendar.HOUR, DAY_IN_MONTH * HOURS_IN_DAY);
        return  pathRepository.findPathByIdUtilisateurAndArchiveAndDateAfter(
                userId, false, calendrier.getTime().getTime());
    }

    /**
     * Getting all paths from a user.
     * @param userId id of the user
     * @return list of paths from the user
     */
    public List<PathEntity> getAllPaths(final int userId) {
        return  pathRepository.findPathByIdUtilisateurAndArchive(
                userId, false);
    }

    /**
     * Getting the last path from a user.
     * @param userId id of the user
     * @return the last path from the user
     */
    public PathEntity getLastPath(final int userId) {
        return pathRepository.findTopByIdUtilisateurAndArchiveOrderByDateDesc(
                userId, false);
    }

    /**
     * Getting paths from a user with a name and date filters.
     * @param name filter on the name of the path
     * @param infDate inferior date to filter the paths
     * @param supDate superior date to filter the paths
     * @param id filter path with the user id
     * @throws ParseException if the date format is incorrect
     * @param nbPathAlreadyLoaded number of paths already loaded
     * @return list of paths from the user respecting all the filters
     *         ordered by the date of creation in descending order
     */
    public List<PathEntity> findPathsByDateAndName(
            final String name,
            final String infDate,
            final String supDate,
            final int id,
            final int nbPathAlreadyLoaded) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
                Locale.FRANCE);

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        long dateMin = sdf.parse(infDate).getTime();
        long dateMax = sdf.parse(supDate).getTime();

        return pathRepository
                .findEntitiesByDateAndName(
                        dateMin, dateMax, name, id, false,
                        getNextPage(nbPathAlreadyLoaded));
    }

    /**
     * Getting paths from a user with a name, date and distance filters.
     * @param name filter on the name of the path
     * @param infDate inferior date to filter the paths
     * @param supDate superior date to filter the paths
     * @param id filter path with the user id
     * @param minLength minimum length of the path
     * @param maxLength maximum length of the path
     * @throws ParseException if the date format is incorrect
     * @param nbPathAlreadyLoaded number of paths already loaded
     * @return list of paths from the user respecting all the filters
     *         ordered by the date of creation in descending order
     *         and respecting the pagination
     */
    public List<PathEntity> findParcourByDateAndNameAndDistance(
            final String name,
            final String infDate,
            final String supDate,
            final int minLength,
            final int maxLength,
            final int id,
            final int nbPathAlreadyLoaded) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
                Locale.FRANCE);

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        long minDate = sdf.parse(infDate).toInstant().toEpochMilli();
        long maxDate = sdf.parse(supDate).toInstant().toEpochMilli();

        return pathRepository
                .findEntitiesByDateAndNameAndDistance(minDate, maxDate, name,
                        minLength, maxLength, id, false,
                        getNextPage(nbPathAlreadyLoaded));
    }

    /**
     * Get a path by its id.
     * @param userId id of the user
     * @param id id of the path
     * @return the path found
     */
    public PathEntity recupParcoursParId(final ObjectId id,
                                         final int userId) {
        return pathRepository.findByIdAndArchiveFalseAndAndIdUtilisateur(id,
                userId);
    }

    /**
     * Get the filter for the pagination and the sorting.
     * @param nbPathAlreadyLoaded number of paths already loaded
     * @return the filter for the pagination and the sorting
     */
    public Pageable getNextPage(final int nbPathAlreadyLoaded) {
        return PageRequest.of(0,
                nbPathAlreadyLoaded + DEFAULT_PAGE_SIZE)
                .withSort(Sort.by(Sort.Direction.DESC, "date"));
    }

}
