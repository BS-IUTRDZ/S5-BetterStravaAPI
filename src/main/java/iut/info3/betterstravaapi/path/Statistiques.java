package iut.info3.betterstravaapi.path;

import java.util.List;

/**
 * Classe de statisques associées à un parcours.
 */
public class Statistiques {

    /**
     * Durée du parcours.
     */
    private long duree;

    /**
     * Distance du parcours.
     */
    private double distance;

    /**
     * Vitesse moyenne au cours du parcours.
     */
    private double vitesseMoyenne;

    /**
     * Dénivelé positif du parcours.
     */
    private double denivPos;

    /**
     * Dénivelé négatif du parcours.
     */
    private double denivNeg;

    /**
     * Constante pour les conversions.
     */
    public static final double METERS_TO_KM = 1000.0;

    /**
     * Constante pour les conversions.
     */
    public static final double SECONDS_TO_HOURS = 3600.0;

    /**
     * Constructeur par défaut pour permettre la compilation.
     */
    public Statistiques() {
    }

    /**
     * Constructeur de statistique.
     * @param dureeStat la durée du parcours
     * @param distStat la distance du parcours
     * @param vitMoyStat la vitesse moyenne du parcours
     * @param denivPosStat le cumul des dénivelés positifs du parcours
     * @param denivNegStat le cumul des dénivelés positifs du parcours
     */
    public Statistiques(final long dureeStat, final double distStat,
                        final double vitMoyStat, final double denivPosStat,
                        final double denivNegStat) {
        this.duree = dureeStat;
        this.distance = distStat;
        this.vitesseMoyenne = vitMoyStat;
        this.denivPos = denivPosStat;
        this.denivNeg = denivNegStat;
    }

    /** @return le temps */
    public long getDuree() {
        return duree;
    }

    /**
     * setter du temps.
     * @param newDuree temps du parcours
     */
    public void setDuree(final long newDuree) {
        this.duree = newDuree;
    }

    /** @return la distance */
    public double getDistance() {
        return distance;
    }

    /**
     * setter de la distance.
     * @param newDistance parcourus
     */
    public void setDistance(final double newDistance) {
        this.distance = newDistance;
    }

    /** @return la vitesse */
    public double getVitesseMoyenne() {
        return vitesseMoyenne;
    }

    /**
     * setter de la vitesse.
     * @param vitMoy vitesse du parcours
     */
    public void setVitesseMoyenne(final double vitMoy) {
        this.vitesseMoyenne = vitMoy;
    }

    /** @return le denivele positif */
    public double getDenivPos() {
        return denivPos;
    }

    /**
     * Associe un denivele positif à un parcours.
     * @param newDenivPos le nouvel email
     */
    public void setDenivPos(final double newDenivPos) {
        this.denivPos = newDenivPos;
    }

    /** @return le denivele negatif */
    public double getDenivNeg() {
        return denivNeg;
    }

    /**
     * Associe un denivele negatif à un parcours.
     * @param newDenivNeg le nouvel email
     */
    public void setDenivNeg(final double newDenivNeg) {
        this.denivNeg = newDenivNeg;
    }

    /**
     * Méthode toString pour afficher les statistiques.
     * @return les statistiques sous le format d'une chaine de caractères
     */
    @Override
    public String toString() {
        return "{"
                + "duree=" + duree
                + ", distance=" + distance
                + ", vitesseMoyenne=" + vitesseMoyenne
                + ", denivPos=" + denivPos
                + ", denivNeg=" + denivNeg
                + '}';
    }

    /**
     * Méthode pour effectuer le calcul des statistiques.
     * @param points liste des points du parcours
     */
    public void calculStatistiques(final List<Coordonnees> points) {
        // Calcul de la distance en km
        this.distance = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            this.distance += points.get(i).distanceTo(points.get(i + 1));
        }
        this.distance = this.distance / METERS_TO_KM;

        // Calcul de la vitesse moyenne en km/h
        this.vitesseMoyenne = this.distance / (this.duree / SECONDS_TO_HOURS);

        // Calcul du dénivelé positif et négatif
        denivPos = 0;
        denivNeg = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            double diff = points.get(i + 1).getAltitude()
                    - points.get(i).getAltitude();
            if (diff > 0) {
                denivPos += diff;
            } else {
                denivNeg -= diff;
            }
        }
    }
}
